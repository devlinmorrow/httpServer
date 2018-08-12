package http.util;

import java.io.File;

public class ResourceTypeIdentifier {

    public ContentType getType(File resource) {
        String resourceName = resource.getName();
        if (resource.isDirectory() || resourceName.contains(".html")) {
            return ContentType.HTML;
        } else if (resourceName.contains(".jpeg")) {
            return ContentType.JPEG;
        } else if (resourceName.contains(".gif")) {
            return ContentType.GIF;
        } else if (resourceName.contains(".png")) {
            return ContentType.PNG;
        } else {
            return ContentType.TXT;
        }
    }
}
