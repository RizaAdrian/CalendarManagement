package com.uaic.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Transformer {
  public static <F, T> Set<T> transform(Collection<F> fromCollection, Function<? super F, ? extends T> function) {
    return fromCollection.stream().map(function).collect(Collectors.toSet());
  }

  public static <T> Set<T> listToSet(List<T> list) {
    return new HashSet<>(list);
  }
}
