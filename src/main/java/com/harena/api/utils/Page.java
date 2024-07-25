package com.harena.api.utils;

import java.util.List;

public record Page<E>(
    int pageNumber,
    boolean isFirst,
    boolean isLast,
    List<E> data,
    boolean hasNext,
    boolean hasPrevious,
    int totalPage,
    int pageSize,
    int totalElements) {}
