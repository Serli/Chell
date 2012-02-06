
package handler;

import com.serli.chell.framework.upload.FileUploadHandler;
import com.serli.chell.framework.upload.FileUploadInfo;
import com.serli.chell.framework.util.MimeType;
import com.serli.chell.framework.util.Size;
import com.serli.chell.framework.util.Size.Unit;
import java.io.File;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ImageUploadHandler extends FileUploadHandler {

    private File uploadDir = new File(System.getProperty("user.home") + "/chell-upload/images");

    @Override
    protected String[] acceptedMimeTypes() {
        return new String[] {
            MimeType.IMAGE_BMP,
            MimeType.IMAGE_GIF,
            MimeType.IMAGE_JPEG,
            MimeType.IMAGE_PNG
        };
    }

    @Override
    protected Size acceptedMaxFileSize() {
        return new Size(500, Unit.KB);
    }

    protected File getStoreDirectory(FileUploadInfo fileInfo) {
        return uploadDir;
    }
}
