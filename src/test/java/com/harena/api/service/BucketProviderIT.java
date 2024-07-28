package com.harena.api.service;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;

import com.harena.api.conf.FacadeIT;
import com.harena.api.file.BucketComponent;
import com.harena.api.file.BucketConf;
import com.harena.api.file.LocalBucketComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class BucketProviderIT extends FacadeIT {
  @Autowired private BucketConf conf;
  @MockBean EnvProvider envProvider;

  @Test
  void bucket_provider_local() {
    when(envProvider.getEnv("STORAGE_METHOD")).thenReturn("LOCAL");

    BucketProvider subjectLocal = new BucketProvider(conf, envProvider);
    assertInstanceOf(LocalBucketComponent.class, subjectLocal.getBucket());
  }

  @Test
  void bucket_provider_remote() {
    when(envProvider.getEnv("STORAGE_METHOD")).thenReturn("REMOTE");

    BucketProvider subjectRemote = new BucketProvider(conf, envProvider);
    assertInstanceOf(BucketComponent.class, subjectRemote.getBucket());
  }
}
