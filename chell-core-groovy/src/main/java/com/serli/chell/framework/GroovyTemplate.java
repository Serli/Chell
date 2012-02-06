/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.serli.chell.framework;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.resolver.ViewResolver;
import com.serli.chell.framework.util.FileUtils;
import groovy.text.SimpleTemplateEngine;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;

/**
 * @author Mathieu Ancelin (mathieu.ancelin@serli.com)
 */
public class GroovyTemplate {

    private static final Pattern listPattern = Pattern.compile("\\#\\{list items:[^/]+, as:'[^']+'\\}");
    private static final Pattern extendsPatter = Pattern.compile("\\#\\{extends '[^']+' /\\}");
    private static final Pattern setPattern = Pattern.compile("\\#\\{set [^/]+:'[^']+' /\\}");
    private static final Pattern getPattern = Pattern.compile("\\#\\{get [^/]+ /\\}");
    private static final Pattern linkPattern = Pattern.compile("\\@\\{'[^']+'\\}");
    private static final Pattern linkRender = Pattern.compile("\\@render\\{'[^']+'\\}");
    private static final Pattern linkAction = Pattern.compile("\\@action\\{'[^']+'\\}");

    private final ConcurrentHashMap<String, groovy.text.Template> templates = new ConcurrentHashMap<String, groovy.text.Template>();
    private final SimpleTemplateEngine engine;
    private final ViewResolver viewResolver;

    public GroovyTemplate(ViewResolver viewResolver) {
        this.engine = new SimpleTemplateEngine();
        this.viewResolver = viewResolver;
    }
    
    public Writer render(File file, Map<String, Object> context, OutputStream os) throws Exception {
        // TODO : if file not exists, return 404
        if (file != null) {
            OutputStreamWriter osw = new OutputStreamWriter(os);
            if (!templates.containsKey(file.getAbsolutePath())) {
                String code = FileUtils.readFileAsString(file, Constant.DEFAULT_ENCODING);
                templates.putIfAbsent(file.getAbsolutePath(), engine.createTemplate(enhanceCode(code)));
            }

            return templates.get(file.getAbsolutePath()).make(new MapProxy(context)).writeTo(osw);
        }
        return null;
    }

    private String enhanceCode(String code) {
        // TODO : custom tags, links, optimize :)
        List<String> before = new ArrayList<String>();
        String custom = code.replace("%{", "<%").replace("}%", "%>").replace("$.", "\\$.").replace("$(", "\\$(").replace("#{/list}", "<% } %>").replace("#{/list }", "<% } %>");
        Matcher matcher = listPattern.matcher(custom);
        while (matcher.find()) {
            String group = matcher.group();
            String list = group;
            list = list.replace("#{list items:", "<% ").replace(", as:'", ".each { ").replace(",as:'", ".each { ").replace("'}", " -> %>").replace("' }", " -> %>");
            custom = custom.replace(group, list);
        }
        Matcher setMatcher = setPattern.matcher(custom);
        while (setMatcher.find()) {
            String group = setMatcher.group();
            String name = group;
            name = name.replace("#{set ", "").replaceAll(":'[^']+' /\\}", "");
            String value = group;
            value = group.replaceAll("\\#\\{set [^/]+:'", "").replace("' /}", "");
            custom = custom.replace(group, "");
            before.add("<% " + name + " = '" + value + "' %>\n");
        }
        Matcher getMatcher = getPattern.matcher(custom);
        while (getMatcher.find()) {
            String group = getMatcher.group();
            String name = group;
            name = name.replace("#{get ", "").replace(" /}", "");
            custom = custom.replace(group, "${" + name + "}");
        }
        Matcher linkMatcher = linkPattern.matcher(custom);
        String rootPath = viewResolver.getRootPath();
        while (linkMatcher.find()) {
            String group = linkMatcher.group();
            String link = group;
            link = link.replace("@{'", "").replace("'}", "");
            if (!"/".equals(rootPath)) {
                link = rootPath + link;
            }
            custom = custom.replace(group, link);
        }
        //////
        Matcher renderMatcher = linkRender.matcher(custom);
        while (renderMatcher.find()) {
            String group = renderMatcher.group();
            String link = group;
            link = link.replace("@render{'", "").replace("'}", "");
            PortletURL url = ((MimeResponse) ChellPortlet.currentResponses.get()).createRenderURL();
            try {
                if (link.toLowerCase().equals("view")) {
                    url.setWindowState(ChellPortlet.currentRequests.get().getWindowState());
                    url.setPortletMode(PortletMode.VIEW);
                    link = url.toString();
                } else if (link.toLowerCase().equals("edit")) {
                    url.setWindowState(ChellPortlet.currentRequests.get().getWindowState());
                    url.setPortletMode(PortletMode.EDIT);
                    link = url.toString();
                } else if (link.toLowerCase().equals("help")) {
                    url.setWindowState(ChellPortlet.currentRequests.get().getWindowState());
                    url.setPortletMode(PortletMode.HELP);
                    link = url.toString();
                } else {
                    link = "#";
                }
            } catch (Exception e) { 
                e.printStackTrace(); 
            }
            custom = custom.replace(group, link);
        }
        Matcher actionMatcher = linkAction.matcher(custom);
        while (actionMatcher.find()) {
            String group = actionMatcher.group();
            String link = group;
            link = link.replace("@action{'", "").replace("'}", "");
            PortletURL url = ((MimeResponse) ChellPortlet.currentResponses.get()).createActionURL();
            url.setParameter("javax.portlet.action", link);
            link = url.toString();
            custom = custom.replace(group, link);
        }
        /////
        Matcher extendsMatcher = extendsPatter.matcher(custom);
        custom = custom.replaceAll("\\#\\{extends '[^']+' /\\}", "");
        while (extendsMatcher.find()) {
            String group = extendsMatcher.group();
            String fileName = group;
            fileName = fileName.replace("#{extends '", "").replace("' /}", "");
            File file = viewResolver.getViewFile(fileName);
            String parentCode = FileUtils.readFileAsString(file, Constant.DEFAULT_ENCODING);
            String parentCustomCode = enhanceCode(parentCode);
            String[] parts = parentCustomCode.split("\\#\\{doLayout /\\}");
            if (parts.length > 2) {
                throw new RuntimeException("Can't have #{doLayout /} more than one time in a template.");
            }
            String finalCode = parts[0] + custom + parts[1];
            for (String bef : before) {
                finalCode = bef + finalCode;
            }
            return finalCode;
        }
        for (String bef : before) {
            custom = bef + custom;
        }
        return custom;
    }

    private class MapProxy extends HashMap<String, Object> {

        public MapProxy(Map<String, Object> map) {
            super(map);
        }

        @Override
        public boolean containsKey(Object key) {
            return true;
        }

        @Override
        public Object get(Object key) {
            Object result = super.get((String) key);
            if (result == null) {
                result = Constant.EMPTY;
                put((String) key, result);
            }
            return result;
        }
    }
}
