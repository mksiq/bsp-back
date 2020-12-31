package ca.maickel.bpsback.services;

import ca.maickel.bpsback.services.exceptions.FileException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class S3Service {
  private Logger LOG = LoggerFactory.getLogger(S3Service.class);

  private final AmazonS3 s3client;

  @Value("${s3.bucket}")
  private String bucketName;

  public S3Service(AmazonS3 s3client) {
    this.s3client = s3client;
  }

  public URI uploadFile(MultipartFile multipartFile) {
    try {
      String fileName = multipartFile.getOriginalFilename();
      InputStream is = multipartFile.getInputStream();
      String contentType = multipartFile.getContentType();
      return uploadFile(is, fileName, contentType);
    } catch (IOException exception) {
      throw new FileException("Error while uploading photo");
    }
  }

  public URI uploadFile(InputStream is, String fileName, String contentType) {
    try {
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentType(contentType);
      s3client.putObject(bucketName, fileName, is, meta);
      return s3client.getUrl(bucketName, fileName).toURI();
    } catch (URISyntaxException e) {
      throw new FileException("Error while converting URL to URI in photo upload");
    }
  }
}
