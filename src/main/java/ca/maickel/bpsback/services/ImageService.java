package ca.maickel.bpsback.services;

import ca.maickel.bpsback.services.exceptions.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {
  public BufferedImage getJpgImgFromFile(MultipartFile file) {
    String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    if (!"jpg".equals(extension) && !"png".equals(extension) && !"jpeg".equals(extension)) {
      throw new FileException("Only png, jpg and jpeg files are accepted");
    }
    try {
      BufferedImage image = ImageIO.read(file.getInputStream());
      if ("png".equals(extension) || "jpeg".equals(extension)) {
        image = otherToJpg(image);
      }
      return image;
    } catch (IOException exception) {
      throw new FileException("Error on reading image file");
    }
  }

  public BufferedImage otherToJpg(BufferedImage image) {
    BufferedImage jpgImg =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    // Color white replace transparent background with a white one
    jpgImg.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
    return jpgImg;
  }

  public InputStream getInputStream(BufferedImage image, String extension) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(image, extension, outputStream);
      return new ByteArrayInputStream(outputStream.toByteArray());
    } catch (IOException e) {
      throw new FileException("Error on reading photo file");
    }
  }
  /**
   * Crop the image to square/ Gets the minimum size to find out if it is a vertical or horizontal
   * image
   */
  public BufferedImage crop(BufferedImage image) {
    //
    int size = (image.getHeight() <= image.getWidth()) ? image.getHeight() : image.getHeight();
    return Scalr.crop(
        image,
        (image.getWidth() / 2) - (size / 2),
        (image.getHeight() / 2) - (size / 2),
        size,
        size);
  }

  /** Resizes the size of image */
  public BufferedImage resize(BufferedImage image, int size) {
    return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, size);
  }
}
