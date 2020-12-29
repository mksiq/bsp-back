package ca.maickel.bpsback.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {
  private Logger LOG = LoggerFactory.getLogger(S3Service.class);

  private final AmazonS3 s3client;

  @Value("${s3.bucket}")
  private String bucketName;

  public S3Service(AmazonS3 s3client) {
    this.s3client = s3client;
  }

  public void uploadFile(String path) {
    try {
      File file = new File(path);
      s3client.putObject(new PutObjectRequest(bucketName, "firstFile.jpg", file));
    } catch (AmazonServiceException exception) {
      LOG.info("Amazon Exception:" + exception.getErrorCode());
      LOG.info(exception.getErrorMessage());
    } catch (AmazonClientException exception) {
        LOG.info("Amazon Client Exception:" + exception.getMessage());
      LOG.info("Cause:" + exception.getCause());
    }
  }
}
