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

package com.serli.chell.framework.resource;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.resource.Resource.Cacheability;
import com.serli.chell.framework.util.FileUtils;
import com.serli.chell.framework.util.MimeType;
import com.serli.chell.framework.util.WebUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ResourceElement implements Serializable {

    public static final Pattern WEBAPP_FORBIDDEN_RESOURCE_PATH = Pattern.compile("(/[.]{2})|([.]{2}/)");

    public static enum Type {
        JAVASCRIPT {
            String toHtml(String resourceUrl) {
                StringBuilder b = new StringBuilder();
                b.append("<script type=\"text/javascript\" src=\"");
                b.append(resourceUrl).append("\"></script>\n");
                return b.toString();
            }
        },

        CSS {
            String toHtml(String resourceUrl) {
                StringBuilder b = new StringBuilder();
                b.append("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
                b.append(resourceUrl).append("\" />\n");
                return b.toString();
            }
        };

        abstract String toHtml(String resourceUrl);
    }

    private Type type;
    private String path;
    private byte[] content;
    private int size;
    private String cacheability;

    private ResourceElement(Type type, String path) {
        this(type, (path.startsWith("/") ? path : "/" +  path), null);
    }

    private ResourceElement(Type type, String path, byte[] content) {
        this.type = type;
        this.path = path;
        this.content = content;
        this.cacheability = null;
        this.size = (content == null ? 0 : content.length);
    }

    public byte[] getContent() {
        return content;
    }

    public int getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    public String getMimeType() {
        switch (type) {
            case JAVASCRIPT: return MimeType.APPLICATION_JAVASCRIPT;
            case CSS: return MimeType.TEXT_CSS;
        }
        return null;
    }

    public void setCacheability(Cacheability cacheability) {
        this.cacheability = cacheability.getCacheability();
    }

    public String getCacheability() {
        return cacheability;
    }

    public String toHtml(RenderRequest request, RenderResponse response) {
        if (content == null) {
            return type.toHtml(request.getContextPath() + path);
        } else {
            ResourceURL resourceUrl = response.createResourceURL();
            if (cacheability != null) {
                resourceUrl.setCacheability(cacheability);
            }
            resourceUrl.setParameter(Constant.PARAMETER_RESOURCE, path);
            return type.toHtml(resourceUrl.toString());
        }
    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResourceElement other = (ResourceElement) obj;
        if ((this.path == null) ? (other.path != null) : !this.path.equals(other.path)) {
            return false;
        }
        return true;
    }

    public static ResourceElement load(String path) {
        try {
            Type type = getResourceType(path);
            if (path.charAt(0) == '@') {
                return new ResourceElement(type, path.substring(1));
            } else {
                if (WEBAPP_FORBIDDEN_RESOURCE_PATH.matcher(path).find()) {
                    throw new ChellException("This resource is forbidden.");
                }
                InputStream is = ResourceElement.class.getResourceAsStream(path);
                if (is != null) {
                    byte[] content = WebUtils.readInputAsByte(is);
                    return new ResourceElement(type, path, content);
                } else {
                    throw new ChellException("Can not read or find resource : " + path);
                }
            }
        } catch (IOException ex) {
            throw new ChellException(ex);
        }
    }

    private static Type getResourceType(String path) {
        String extension = FileUtils.extension(path);
        if (extension.equalsIgnoreCase("js")) {
            return Type.JAVASCRIPT;
        } else if (extension.equalsIgnoreCase("css")) {
            return Type.CSS;
        } else {
            throw new ChellException("This kind of resource is not managed : " + path);
        }
    }
}
