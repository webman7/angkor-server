package com.adplatform.restApi.infra.file.helper;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ImageSizeHelper {
    public int getWidth(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream()).getWidth();
    }

    public int getHeight(MultipartFile file) throws IOException {
        return ImageIO.read(file.getInputStream()).getHeight();
    }
}
