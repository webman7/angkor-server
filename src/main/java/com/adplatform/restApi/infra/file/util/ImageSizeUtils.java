package com.adplatform.restApi.infra.file.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Image size calculate utility class.
 *
 * @author Seohyun Lee
 * @since 1.0
 */
public class ImageSizeUtils {
    public static int getWidth(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream()).getWidth();
    }

    public static int getHeight(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream()).getHeight();
    }
}
