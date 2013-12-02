/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.dspl.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Utilities for working with {@link DatasetItem}s.
 *
 * @author James Grimm
 */
public final class DatasetItems {

  private static <T extends DatasetItem<T>> Function<T, T> createCloneFunction() {
    return new Function<T, T>() {
      @Override
      public T apply(T original) {
        return original.clone();
      }
    };
  }

  /**
   * Clone a single {@link DatasetItem}, checking for null.
   */
  public static <T extends DatasetItem<T>> T cloneItem(T item) {
    return item != null ? item.clone() : null;
  }

  /**
   * Clone a list of {@link DatasetItem}s, checking for a null list, but not for null items in the
   * list.
   */
  public static <T extends DatasetItem<T>> List<T> cloneList(List<T> list) {
    return list != null
        ? Lists.newArrayList(Lists.transform(list, DatasetItems.<T>createCloneFunction()))
        : null;
  }

  public static <K extends DatasetItem<K>, V extends DatasetItem<V>>
      Map<K, V> cloneItemMap(Map<K, V> map) {
    if (map == null) {
      return null;
    }
    Map<K, V> result = Maps.newHashMap();
    for (Map.Entry<K, V> entry : map.entrySet()) {
      result.put(entry.getKey().clone(), entry.getValue().clone());
    }
    return result;
  }

  public static <T> Set<T> cloneOrderedSet(Set<T> set) {
    return set != null
        ? Sets.newLinkedHashSet(set)
        : null;
  }

  static <T> ImmutableList<T> immutableCopyOf(List<? extends T> original) {
    return ImmutableList.copyOf(original);
  }

  static <K, V> Map<K, V> immutableCopyOf(Map<? extends K, ? extends V> original) {
    return original != null
        ? ImmutableMap.copyOf(original)
        : ImmutableMap.<K, V>of();
  }

  private DatasetItems() {
    throw new AssertionError("Do not instantiate");
  }
}
