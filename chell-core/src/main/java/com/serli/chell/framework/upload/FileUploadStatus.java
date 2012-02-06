
package com.serli.chell.framework.upload;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.Scope;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.form.FormStructure;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.util.WebUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.portlet.ActionRequest;
import javax.portlet.filter.ActionRequestWrapper;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.portlet.PortletFileUpload;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FileUploadStatus {

    private Map<String, List<String>> fieldFormParameters = new HashMap<String, List<String>>();
    private Map<String, FileUploadParameter> uploadParameters = new HashMap<String, FileUploadParameter>();
    private Set<String> parameterNames = new HashSet<String>();

    public static boolean isUploadForm(ActionRequest request) {
        return PortletFileUpload.isMultipartContent(request);
    }

    public static FileUploadStatus fromRequest(ActionRequest request, FormStructure structure) {
        request = new EncodedActionRequest(request);
        FileUploadStatus fus = new FileUploadStatus();
        try {
            PortletFileUpload upload = new PortletFileUpload();
            FileItemIterator fiit = upload.getItemIterator(request);
            String encoding = request.getCharacterEncoding();
            FormField formField;
            FileItemStream fis;
            String fieldName;
            while (fiit.hasNext()) {
                fis = fiit.next();
                fieldName = fis.getFieldName();
                formField = structure.getField(fieldName);
                if (formField != null) {
                    fus.parameterNames.add(fieldName);
                    if (fis.isFormField()) {
                        fus.addFormFieldParameter(fis, fieldName, encoding);
                    } else {
                        fus.addFileUploadParameter(fis, fieldName, encoding, formField);
                    }
                }
            }
            return fus;
        } catch (FileUploadException ex) {
            throw new ChellException("Can not parse upload request.", ex);
        } catch (IOException ex) {
            throw new ChellException("Network error.", ex);
        }
    }

    public boolean hasParameter(FormField field) {
        return parameterNames.contains(field.getName());
    }

    public boolean isUnchangedUploadParameter(FormField field) {
        String fieldName = field.getName();
        FileUploadParameter parameter = uploadParameters.get(fieldName);
        return (field.isUploadField() && parameter != null &&
               (!parameterNames.contains(fieldName) || parameter.hasError()));
    }

    public FileUploadParameter getFileUploadParameter(String fieldName) {
        return uploadParameters.get(fieldName);
    }

    private void addFormFieldParameter(FileItemStream fis, String fieldName, String encoding) {
        List<String> parameters = fieldFormParameters.get(fieldName);
        if (parameters == null) {
            parameters = new ArrayList<String>();
            fieldFormParameters.put(fieldName, parameters);
        }
        try {
            String value = WebUtils.readInputAsString(fis.openStream(), encoding);
            parameters.add(value);
        } catch (IOException ex) {
            throw new ChellException(ex);
        }
    }

    private void addFileUploadParameter(FileItemStream fis, String fieldName, String encoding, FormField formField) {
        FileUploadInfo fileInfo = FileUploadInfo.create(fis, encoding);
        FileUploadParameter parameter = uploadParameters.get(fieldName);
        if (parameter == null) {
            parameter = new FileUploadParameter();
            uploadParameters.put(fieldName, parameter);
        }
        parameter.addFile(fileInfo);
        try {
            if (!fileInfo.isEmpty()) {
                UploadHandler uploadHandler = formField.getUploadHandler();
                if (!uploadHandler.acceptExtension(fileInfo.getExtension())) {
                    parameter.setError(MessageKey.VALIDATION_UPLOAD_BAD_EXTENSION, formField.getLabel(), uploadHandler.formatAcceptedExtensions());
                } else if (!uploadHandler.acceptMimeType(fileInfo.getContentType())) {
                    parameter.setError(MessageKey.VALIDATION_UPLOAD_BAD_MIMETYPE, formField.getLabel(), uploadHandler.formatAcceptedMimeTypes());
                } else {
                    Object item = uploadHandler.onCreate(fileInfo);
                    fileInfo.setItem(item);
                    if (!uploadHandler.acceptFileSize(fileInfo.getSize())) {
                        parameter.setError(MessageKey.VALIDATION_UPLOAD_FILE_TOO_LARGE, formField.getLabel(), uploadHandler.formatMaxFileSize());
                        uploadHandler.onDelete(item);
                    }
                }
            }
        } catch (UploadException ex) {
            parameter.setError(ex.getMessage(), ex.getMessageArguments());
        }
    }

    public String getParameter(FormField field, String preferenceValue) {
        String result = Constant.EMPTY;
        String fieldName = field.getName();
        if (field.isUploadField()) {
            FileUploadParameter parameter = uploadParameters.get(fieldName);
            if (parameter != null) {
                if (!parameter.hasError()) {
                    FileUploadInfo fileInfo = parameter.getFirstAvailableFileInfo();
                    if (fileInfo != null) {
                        FieldConverter converter = field.getUploadHandler().getConverter();
                        Object item = fileInfo.getItem();
                        if (item != null) {
                            result = (String) converter.fromObject(item);
                        }
                    }
                }
                parameter.checkDiscardElement(preferenceValue, result);
            }
        } else {
            List<String> values = fieldFormParameters.get(fieldName);
            if (values != null && values.size() > 0) {
                result = values.get(0);
            }
        }
        return result;
    }

    public String[] getParameters(FormField field, String[] preferenceValues) {
        String[] result = Constant.EMPTY_TAB;
        String fieldName = field.getName();
        if (field.isUploadField()) {
            FileUploadParameter parameter = uploadParameters.get(fieldName);
            if (parameter != null) {
                if (!parameter.hasError()) {
                    List<FileUploadInfo> values = parameter.getAvailableFileInfos();
                    if (values.size() > 0) {
                        FileUploadInfo fileInfo;
                        FieldConverter converter = field.getUploadHandler().getConverter();
                        result = new String[values.size()];
                        for (int i = 0; i < result.length; i++) {
                            fileInfo = values.get(i);
                            result[i] = (String) converter.fromObject(fileInfo.getItem());
                        }
                    }
                }
                parameter.checkDiscardElements(preferenceValues, result);
            }
        } else {
            List<String> values = fieldFormParameters.get(fieldName);
            if (values != null) {
                result = values.toArray(new String[values.size()]);
            }
        }
        return result;
    }

    public static void discardCanceledFormUploadedItems(Form form) {
        FormStructure structure = form.getStructure();
        if (structure.isUploadForm()) {
            Object item;
            UploadHandler uploadHandler;
            try {
                for (FormField formField : structure.getFields()) {
                    uploadHandler = formField.getUploadHandler();
                    if (formField.isUploadField()) {
                        for (String toDiscard : formField.getValueSet(form)) {
                            if (toDiscard != null && toDiscard.length() > 0) {
                                item = uploadHandler.loadItem(toDiscard);
                                uploadHandler.onDiscard(item);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                throw new ChellException(ex);
            }
        }
    }

    public void discardFormUploadedItems(Form form) {
        Object item;
        UploadHandler uploadHandler;
        try {
            FormStructure structure = form.getStructure();
            for (Entry<String, FileUploadParameter> entry : uploadParameters.entrySet()) {
                uploadHandler = structure.getField(entry.getKey()).getUploadHandler();
                for (String toDiscard : entry.getValue().getDiscardElements()) {
                    item = uploadHandler.loadItem(toDiscard);
                    uploadHandler.onDiscard(item);
                }
            }
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public String getUploadError(String fieldName) {
        FileUploadParameter parameter = uploadParameters.get(fieldName);
        if (parameter != null) {
            return parameter.getError();
        }
        return null;
    }

    public void store() {
        Scope.request().set(this);
    }

    public static FileUploadStatus current() {
        FileUploadStatus fus = Scope.request().get(FileUploadStatus.class);
        if (fus == null) {
            fus = new FileUploadStatus();
            // TODO : PRIORITAIRE : A completer
        }
        return fus;
    }

    private static class EncodedActionRequest extends ActionRequestWrapper {

        public EncodedActionRequest(ActionRequest request) {
            super(request);
        }

        @Override
        public String getCharacterEncoding() {
            String encoding = super.getCharacterEncoding();
            if (encoding == null) {
                encoding = Constant.DEFAULT_ENCODING;
            }
            return encoding;
        }
    }
}
