package com.harena.api.service;

import org.springframework.stereotype.Component;

@Component
public class EnvProvider {
  public String getEnv(String key) {
    return System.getenv(key);
  }
}
