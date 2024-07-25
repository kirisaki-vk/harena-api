package com.harena.api.file;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Component;

@Component
public class LocalBucketComponent extends BucketComponent {
  private static final String LOCAL_PATH_PREFIX = "harena_api_local_bucket";
  private static final Path localPath;

  public LocalBucketComponent(BucketConf bucketConf) {
    super(bucketConf);
  }

  static {
    try {
      localPath = Files.createTempDirectory(Path.of("/tmp"), LOCAL_PATH_PREFIX);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public File download(String bucketKey) {
    return new File(String.valueOf(Path.of(localPath.toString(), bucketKey)));
  }

  @Override
  public FileHash upload(File file, String bucketKey) {
    try {
      Files.copy(file.toPath(), Path.of(localPath.toString(), bucketKey), REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new FileHash(FileHashAlgorithm.NONE, bucketKey);
  }

  @Override
  public String getBucketName() {
    return LOCAL_PATH_PREFIX;
  }
}
