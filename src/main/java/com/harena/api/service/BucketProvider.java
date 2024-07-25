package com.harena.api.service;

import com.harena.api.file.BucketComponent;
import com.harena.api.file.BucketConf;
import com.harena.api.file.LocalBucketComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BucketProvider {
  private static final String method = System.getenv("STORAGE_METHOD");
  private final BucketConf bucketConf;

  public BucketComponent getBucket() {
    if (method.equals("LOCAL")) {
      return new LocalBucketComponent(bucketConf);
    } else {
      return new BucketComponent(bucketConf);
    }
  }
}
