package com.harena.api.service.mappers;

public interface Mapper<T, R> {
  public R toRestModel(T objectModel);

  public T toObjectModel(R restModel);
}
