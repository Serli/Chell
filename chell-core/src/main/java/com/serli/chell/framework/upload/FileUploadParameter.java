
package com.serli.chell.framework.upload;

import com.serli.chell.framework.message.MessageBundle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FileUploadParameter {

    private List<FileUploadInfo> fileInfos = new ArrayList<FileUploadInfo>(1);
    private List<String> toDiscard = new ArrayList<String>(1);
    private String error = null;

    public void addFile(FileUploadInfo fileInfo) {
        fileInfos.add(fileInfo);
    }

    public List<FileUploadInfo> getFileInfos() {
        return fileInfos;
    }

    public FileUploadInfo getFirstAvailableFileInfo() {
        for (FileUploadInfo fileInfo : fileInfos) {
            if (!fileInfo.isEmpty()) {
                return fileInfo;
            }
        }
        return null;
    }

    public List<FileUploadInfo> getAvailableFileInfos() {
        List<FileUploadInfo> result = new ArrayList<FileUploadInfo>(fileInfos.size());
        for (FileUploadInfo fileInfo : fileInfos) {
            if (!fileInfo.isEmpty()) {
                result.add(fileInfo);
            }
        }
        return result;
    }

    public String getError() {
        return error;
    }

    public boolean hasError() {
        return (error != null);
    }

    public void setError(String messageKey, Object... arguments) {
        this.error = MessageBundle.getMessageFormatted(messageKey, arguments);
    }

    public void checkDiscardElement(String prefValue, String newValue) {
        if (prefValue != null && prefValue.length() > 0 && !prefValue.equals(newValue)) {
            toDiscard.add(prefValue);
        }
    }

    public void checkDiscardElements(String[] prefValues, String[] newValues) {
        Set<String> set = new HashSet<String>(newValues.length);
        for (String newValue : newValues) {
            set.add(newValue);
        }
        for (String prefValue : prefValues) {
            if (prefValue != null && prefValue.length() > 0 && !set.contains(prefValue)) {
                toDiscard.add(prefValue);
            }
        }
    }

    public List<String> getDiscardElements() {
        return toDiscard;
    }
}
