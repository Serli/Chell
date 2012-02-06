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

package com.serli.chell.framework.upload;

import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.form.converter.FileFieldConverter;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.util.FileNameGenerator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class FileUploadHandler extends UploadHandler<File> {

    protected final Class<? extends FieldConverter<File, String>> defineConverter() {
        return FileFieldConverter.class;
    }

    /**
     * Delete the stored file.
     */
    public final void onDelete(File file) throws UploadException {
        file.delete();
    }

    /**
     * Store uploaded file on the server.
     */
    public final File onCreate(FileUploadInfo fileInfo) throws UploadException {
        File storageDirectory = getStoreDirectory(fileInfo);
        if (!storageDirectory.exists()) {
            storageDirectory.mkdirs();
        } else if (!storageDirectory.isDirectory()) {
            throw new UploadException(MessageKey.VALIDATION_UPLOAD_BAD_DIRECTORY, fileInfo.getFieldName());
        }
        File item = new File(storageDirectory, getStoreFileName(fileInfo));
        if (!item.exists()) {
            try {
                FileOutputStream fos = new FileOutputStream(item);
                fileInfo.copyFileContent(fos);    
            } catch (IOException ex) {
                throw new UploadException(MessageKey.VALIDATION_UPLOAD_ERROR, ex, fileInfo.getFieldName());
            }
        } else {
            throw new UploadException(MessageKey.VALIDATION_UPLOAD_FILE_EXISTING, fileInfo.getFieldName());
        }
        return item;
    }

    @Override
    public String onDisplay(File file) {
        return FileNameGenerator.extract(file.getName());
    }

    /**
     * Determine the directory where the uploaded file is stored.
     * @param fileInfo The uploaded file informations
     * @return A directory (may not exist).
     */
    protected abstract File getStoreDirectory(FileUploadInfo fileInfo);

    /**
     * Determine the final name of the uploaded file.
     * @param fileInfo The uploaded file informations
     * @return A file name
     */
    protected String getStoreFileName(FileUploadInfo fileInfo) {
        return FileNameGenerator.get(fileInfo.getFileName());
    }
}
