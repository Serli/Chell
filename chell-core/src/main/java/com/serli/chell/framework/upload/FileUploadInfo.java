
package com.serli.chell.framework.upload;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.util.FileUtils;
import com.serli.chell.framework.util.MimeType;
import com.serli.chell.framework.util.WebUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.fileupload.FileItemStream;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FileUploadInfo {

    private FileItemStream fis;
    private String extension;
    private String fieldName;
    private String fileName;
    private String contentType;
    private String charsetEncoding;
    private Object item;
    private long size;

    private FileUploadInfo(FileItemStream fis, String fileName, String charsetEncoding) {
        this.fis = fis;
        this.fileName = fileName;
        this.fieldName = fis.getFieldName();
        this.charsetEncoding = charsetEncoding;
        this.item = null;
        if (fileName == null) {
            this.extension = null;
            this.contentType = null;
            this.size = 0;
        } else {
            this.extension = FileUtils.extension(fileName);
            this.contentType = getContentType(fis.getContentType(), extension);
            this.size = Constant.UNSPECIFIED;
        }
    }

    protected static FileUploadInfo create(FileItemStream fis, String charsetEncoding) {
        return new FileUploadInfo(fis, getFilename(fis), charsetEncoding);
    }

    public boolean isEmpty() {
        return (fileName == null);
    }

    public void copyFileContent(OutputStream os) throws IOException {
        size = WebUtils.copyStream(fis.openStream(), os);
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getSize() {
        return size;
    }

    public Object getItem() {
        return item;
    }

    protected void setItem(Object item) {
        this.item = item;
    }

    public String getCharsetEncoding() {
        return charsetEncoding;
    }

    private static String getFilename(FileItemStream fis) {
        String filePath = fis.getName();
        if (filePath != null && filePath.length() > 0) {
            return new File(filePath).getName();
        }
        return null;
    }

    private static String getContentType(String contentType, String extension) {
        if (contentType == null) {
            return MimeType.APPLICATION_OCTET_STREAM;
        } else {
            if (MimeType.APPLICATION_DOWNLOAD.equals(contentType) || MimeType.APPLICATION_OCTET_STREAM.equals(contentType)) {
                contentType = MimeType.getMimeTypeByExtension(extension);
            }
            return contentType;
        }
    }
}
