package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import com.harena.api.file.BucketConf;
import com.harena.api.file.LocalBucketComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BucketProvider {
  private final EnvProvider envProvider;
  private final BucketConf bucketConf;
  private final BucketComponent bucketComponent;

  public BucketComponent getBucket() {
    if (envProvider.getEnv("STORAGE_METHOD") != null
        && envProvider.getEnv("STORAGE_METHOD").equals("LOCAL")) {
      return new LocalBucketComponent(bucketConf);
    } else {
      return bucketComponent;
    }
  }
}
