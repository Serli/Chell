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
