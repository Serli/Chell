
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