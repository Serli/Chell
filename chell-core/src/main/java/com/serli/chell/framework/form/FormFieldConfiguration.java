
package com.serli.chell.framework.form;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.converter.DateFieldConverter;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.reflect.AnnotationMetaDataInfos;
import com.serli.chell.framework.validation.Constraint;
import com.serli.chell.framework.validation.annotation.CheckWith;
import com.serli.chell.framework.validation.annotation.Length;
import com.serli.chell.framework.validation.annotation.Size;
import com.serli.chell.framework.validation.annotation.Temporal;
import com.serli.chell.framework.validation.check.LengthCheck;
import com.serli.chell.framework.validation.check.SizeCheck;
import com.serli.chell.framework.validation.check.TemporalCheck;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class FormFieldConfiguration<T extends Annotation> {

    public abstract void configure(FormField formField, T annotation);

    public final static class NoConfiguration extends FormFieldConfiguration<Annotation> {
        public static final NoConfiguration INSTANCE = new NoConfiguration();
        public void configure(FormField formField, Annotation annotation) {
        }
    }

    public static class InputConfiguration extends FormFieldConfiguration<Annotation> {

        protected FormType inputType;
        protected AnnotationMetaDataInfos annotationInfos;

        public final InputConfiguration initialize(FormInputMetadata annotation, Class<? extends Annotation> annotationType) {
            inputType = annotation.type();
            annotationInfos = AnnotationMetaDataInfos.get(annotationType);
            return this;
        }

        public void configure(FormField formField, Annotation annotation) {
            formField.associateInputAnnotation(inputType, annotation, annotationInfos);
        }
    }

    public static class ValidationConfiguration extends FormFieldConfiguration<Annotation> {

        private Class<? extends FieldConverter> converterClass;
        private Class<? extends Constraint> constraintClass;

        public ValidationConfiguration(Class<? extends FieldConverter> converterClass, Class<? extends Constraint> constraintClass) {
            this.converterClass = converterClass;
            this.constraintClass = constraintClass;
        }

        public void configure(FormField formField, Annotation annotation) {
            if (converterClass != null) {
                formField.setSmartConverter(converterClass);
            }
            if (constraintClass != null) {
                formField.addSmartConstraint(instantiateConstraint(constraintClass, annotation));
            }
        }
    };

    public static class LengthConfiguration extends FormFieldConfiguration<Length> {
        public void configure(FormField formField, Length annotation) {
            if (formField.isMultiValued()) {
                throw new ChellException("Field '" + formField.getName() + "' is multi-valued and can not be validated with @Length rule. Please use @Size rule.");
            } else {
                formField.addConstraint(new LengthCheck(annotation));
            }
        }
    };

    public static class SizeConfiguration extends FormFieldConfiguration<Size> {
        public void configure(FormField formField, Size annotation) {
            if (formField.isMultiValued()) {
                formField.addConstraint(new SizeCheck(annotation));
            } else {
                throw new ChellException("Field '" + formField.getName() + "' is not multi-valued and can not be validated with @Size rule. Please use @Length rule.");
            }
        }
    };

    public static class TemporalConfiguration extends  FormFieldConfiguration<Temporal> {
        public void configure(FormField formField, Temporal annotation) {
            String datePattern = annotation.pattern();
            SimpleDateFormat format = new SimpleDateFormat(datePattern);
            format.setLenient(false);
            formField.setSmartConverter(new DateFieldConverter(format));
            formField.addSmartConstraint(new TemporalCheck(annotation, format));
        }
    };

    public static class CheckWithConfiguration extends FormFieldConfiguration<CheckWith> {
        public void configure(FormField formField, CheckWith annotation) {
            formField.addSmartConstraint(instantiateConstraint(annotation.value(), annotation));
        }
    };

    private static Constraint<?> instantiateConstraint(Class<? extends Constraint> constraintClass, Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        try {
            Constructor<? extends Constraint> constructor = constraintClass.getConstructor(annotationType);
            constructor.setAccessible(true);
            return constructor.newInstance(annotation);
        } catch (NoSuchMethodException ex) {
            throw new ChellException("No constructor " + constraintClass.getSimpleName() + "(" + annotationType.getName() +
                                          ") found for constraint class " + constraintClass.getName());
        } catch (SecurityException ex) {
            throw new ChellException(ex);
        } catch (InstantiationException ex) {
            throw new ChellException("Can not instantiate constraint class " + constraintClass.getName());
        } catch (IllegalAccessException ex) {
            throw new ChellException("Can not instantiate constraint class " + constraintClass.getName());
        } catch (InvocationTargetException ex) {
            throw new ChellException("Can not instantiate constraint class " + constraintClass.getName());
        }
    }
}
