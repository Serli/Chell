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


package handler;

import com.serli.chell.framework.upload.FileUploadHandler;
import com.serli.chell.framework.upload.FileUploadInfo;
import com.serli.chell.framework.util.Size;
import com.serli.chell.framework.util.Size.Unit;
import java.io.File;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class AllUploadHandler extends FileUploadHandler {

    private File uploadDir = new File(System.getProperty("user.home") + "/chell-upload/images");

    @Override
    protected Size acceptedMaxFileSize() {
        return new Size(2, Unit.MB);
    }

    protected File getStoreDirectory(FileUploadInfo fileInfo) {
        return uploadDir;
    }
}
