
package com.serli.chell.framework.upload;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.util.MimeType;
import com.serli.chell.framework.util.SingletonFactory;
import com.serli.chell.framework.util.Size;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * @param <T> Type of an item (can be a file, database row id, ...)
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class UploadHandler<T> {

    private Set<String> acceptedExtensions;
    private Set<String> acceptedMimeTypes;
    private FieldConverter<T, String> converter;
    private long maxFileSize;

    /**
     * Return the converter instance to convert an item into a string
     * for store / read from preferences.
     */
    protected abstract Class<? extends FieldConverter<T, String>> defineConverter();

    /**
     * Function called to delete an item.
     */
    public abstract void onDelete(T item) throws UploadException;

    /**
     * Function called to store the uploaded file somewhere (file, database, ...)
     */
    public abstract T onCreate(FileUploadInfo fileInfo) throws UploadException;

    /**
     * Fonction called when a stored item is discarded.
     */
    public void onDiscard(T item) throws UploadException {
        onDelete(item);
    }

    /**
     * Return a displayable representation of the item.
     */
    public String onDisplay(T item) {
        return converter.fromObject(item);
    }

    /**
     * Return an item loaded from a string representation of the item.
     */
    public T loadItem(String itemValue) {
        return converter.toObject(itemValue);
    }

    /**
     * Determine if the handler accept the specified extension.
     */
    public final boolean acceptExtension(String extension) {
        return acceptElement(acceptedExtensions, extension);
    }

    /**
     * Determine if the handler accept the specified mime type.
     */
    public final boolean acceptMimeType(String mimeType) {
        return acceptElement(acceptedMimeTypes, mimeType);
    }

    /**
     * Determine if the file size is too large or not.
     */
    public final boolean acceptFileSize(long fileSize) {
        return (maxFileSize == Constant.UNSPECIFIED ? true : fileSize <= maxFileSize);
    }

    /**
     * Return the max allowed file size in bytes.
     */
    public final long getMaxFileSize() {
        return maxFileSize;
    }

    /**
     * Return the converter of the handler.
     */
    public final FieldConverter<T, String> getConverter() {
        return converter;
    }

    /**
     *
     * @return
     */
    protected String[] acceptedExtensions() {
        return null;
    }

    protected String[] acceptedMimeTypes() {
        return null;
    }

    protected Size acceptedMaxFileSize() {
        return null;
    }

    protected String formatMaxFileSize() {
        return Size.fromBytes(maxFileSize).toString();
    }

    protected String formatAcceptedExtensions() {
        StringBuilder b = new StringBuilder();
        if (acceptedExtensions != null && acceptedExtensions.size() > 0) {
            for (String extension : acceptedExtensions) {
                b.append(extension.toUpperCase()).append(", ");
            }
            b.setLength(b.length() - 2);
        }
        return b.toString();
    }

    protected String formatAcceptedMimeTypes() {
        StringBuilder b = new StringBuilder();
        if (acceptedMimeTypes != null && acceptedMimeTypes.size() > 0) {
            String[] extensions;
            for (String mimeType : acceptedMimeTypes) {
                extensions = MimeType.getExtensionsForMimeType(mimeType);
                if (extensions != null) {
                    for (String extension : extensions) {
                        b.append(extension.toUpperCase()).append(", ");
                    }
                } else {
                    b.append('\'').append(mimeType).append("', ");
                }
            }
            b.setLength(b.length() - 2);
        }
        return b.toString();
    }

    public String getDeleteButtonLabel() {
        return MessageBundle.getMessage(MessageKey.ACTION_DELETE);
    }

    public final void configure() {
        Size maxSize = acceptedMaxFileSize();
        if (maxSize == null) {
            maxFileSize = Constant.UNSPECIFIED;
        } else {
            maxFileSize = maxSize.toBytes();
        }
        acceptedExtensions = createAcceptedElement(acceptedExtensions());
        acceptedMimeTypes = createAcceptedElement(acceptedMimeTypes());
        Class<? extends FieldConverter<T, String>> cvtClass = defineConverter();
        if (cvtClass == null) {
            throw new ChellException("Upload handler must define a field converter class.");
        }
        converter = SingletonFactory.get(cvtClass);
    }

    private Set<String> createAcceptedElement(String[] elements) {
        if (elements != null) {
            Set<String> result = new LinkedHashSet<String>();
            for (String element : elements) {
                result.add(element.toLowerCase());
            }
            return result;
        }
        return null;
    }

    private boolean acceptElement(Set<String> acceptedElements, String element) {
        if (acceptedElements != null) {
            return acceptedElements.contains(element.toLowerCase());
        }
        return true;
    }
}
