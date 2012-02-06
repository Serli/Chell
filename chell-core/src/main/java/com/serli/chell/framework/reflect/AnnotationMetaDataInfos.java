package com.serli.chell.framework.reflect;

import com.serli.chell.framework.reflect.AnnotationDefaultValues.DefaultValue;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.util.ClassUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class AnnotationMetaDataInfos {

    private static final Map<Class<? extends Annotation>, AnnotationMetaDataInfos> ALL_ANNOTATION_INFOS = new HashMap<Class<? extends Annotation>, AnnotationMetaDataInfos>();
    private Map<Type, MetaDataInfo> metaDataInfos = new EnumMap<Type, MetaDataInfo>(Type.class);

    public AnnotationMetaDataInfos(Class<? extends Annotation> annotationType) {
        AnnotationMetaData metaData;
        MetaDataInfo metaDataInfo;
        Type metaDataType;
        for (Method method : annotationType.getDeclaredMethods()) {
            metaData = method.getAnnotation(AnnotationMetaData.class);
            if (metaData != null) {
                metaDataType = metaData.value();
                metaDataType.validateMethod(method);
                metaDataInfo = new MetaDataMethod(method);
                metaDataInfos.put(metaDataType, metaDataInfo);
            }
        }
        AnnotationConstants constants = annotationType.getAnnotation(AnnotationConstants.class);
        if (constants != null) {
            Class<? extends AnnotationDefaultValues> annotationDefaultValuesClass = constants.value();
            AnnotationDefaultValues annotationDefaultValues = ClassUtils.newInstance(annotationDefaultValuesClass);
            DefaultValue[] defaultValues = annotationDefaultValues.getDefaultValues();
            if (defaultValues != null) {
                for (DefaultValue defaultValue : defaultValues) {
                    metaDataType = defaultValue.getType();
                    metaDataType.validateDefaultValue(defaultValue);
                    metaDataInfos.put(metaDataType, defaultValue);
                }
            }
        }
    }

    public MetaDataInfo<?> getMethod(Type type) {
        return metaDataInfos.get(type);
    }

    public <T> MetaDataInfo<T> getMethod(Type type, Class<T> returnType) {
        return metaDataInfos.get(type);
    }

    public Object getValue(Type type, Annotation annotation) {
        MetaDataInfo mdm = metaDataInfos.get(type);
        if (mdm != null) {
            return mdm.getValue(annotation);
        }
        return null;
    }

    public Set<Type> getTypes() {
        return metaDataInfos.keySet();
    }

    public Object getValue(Type type, Annotation annotation, Object defaultValue) {
        MetaDataInfo mdm = metaDataInfos.get(type);
        if (mdm != null) {
            return mdm.getValue(annotation);
        }
        return defaultValue;
    }

    public Object getDefaultValue(Type type) {
        MetaDataInfo mdm = metaDataInfos.get(type);
        if (mdm != null) {
            return mdm.getDefaultValue();
        }
        return null;
    }

    public static AnnotationMetaDataInfos get(Annotation annotation) {
        return get(annotation.annotationType());
    }

    public static synchronized AnnotationMetaDataInfos get(Class<? extends Annotation> annotationClass) {
        AnnotationMetaDataInfos infos = ALL_ANNOTATION_INFOS.get(annotationClass);
        if (infos == null) {
            infos = new AnnotationMetaDataInfos(annotationClass);
            ALL_ANNOTATION_INFOS.put(annotationClass, infos);
        }
        return infos;
    }
}
