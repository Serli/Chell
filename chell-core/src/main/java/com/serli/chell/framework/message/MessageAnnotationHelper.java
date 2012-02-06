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

package com.serli.chell.framework.message;

import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.AnnotationMetaDataInfos;
import com.serli.chell.framework.reflect.MetaDataInfo;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class MessageAnnotationHelper {

    private static final Type[] MESSAGE_METADATA_TYPES = { Type.MESSAGE_TEXT, Type.MESSAGE_KEY, Type.MESSAGE_BUNDLE };
    private static final Map<Class<? extends Annotation>, MessageMetaData> META_DATA_MESSAGES = new HashMap<Class<? extends Annotation>, MessageMetaData>();

    public static Message getMessage(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        MessageMetaData mmd = getMessageMetaData(annotationType);
        AnnotationMetaDataInfos infos = mmd.getMetaData();
        Message message = mmd.getDefaultMessage();
        MetaDataInfo<String> method;
        for (Type metaDataType : MESSAGE_METADATA_TYPES) {
            method = infos.getMethod(metaDataType, String.class);
            if (method != null) {
                message.set(metaDataType, method.getValue(annotation), method.getDefaultValue());
            }
        }
        return message;
    }

    public static Message getMessage(Annotation annotation, String defaultText) {
        Message message = getMessage(annotation);
        if (message.isEmpty()) {
            message.set(Type.MESSAGE_TEXT, defaultText);
        }
        return message;
    }

    public static Message getMessage(Annotation annotation, String defaultKey, String defaultBundle) {
        Message message = getMessage(annotation);
        if (message.isEmpty()) {
            message.set(Type.MESSAGE_KEY, defaultKey);
            message.set(Type.MESSAGE_BUNDLE, defaultBundle);
        }
        return message;
    }

    public static Message getDefaultMessage(Class<? extends Annotation> annotationType) {
        return getMessageMetaData(annotationType).getDefaultMessage();
    }
    
    private static synchronized MessageMetaData getMessageMetaData(Class<? extends Annotation> annotationType) {
        MessageMetaData mmd = META_DATA_MESSAGES.get(annotationType);
        if (mmd == null) {
            mmd = new MessageMetaData(annotationType);
            META_DATA_MESSAGES.put(annotationType, mmd);
        }
        return mmd;
    }

    private static class MessageMetaData {

        private Message defaultMessage;
        private AnnotationMetaDataInfos metaData;

        public MessageMetaData(Class<? extends Annotation> annotationType) {
            metaData = new AnnotationMetaDataInfos(annotationType);
            defaultMessage = Message.empty();
            MetaDataInfo<String> method;
            for (Type metaDataType : MESSAGE_METADATA_TYPES) {
                method = metaData.getMethod(metaDataType, String.class);
                if (method != null) {
                    defaultMessage.set(metaDataType, method.getDefaultValue());
                }
            }
        }

        public Message getDefaultMessage() {
            return Message.copy(defaultMessage);
        }

        public AnnotationMetaDataInfos getMetaData() {
            return metaData;
        }
    }
}
