/*
 * Copyright 2012 Google Inc. All Rights Reserved.
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

import java.util.List;

/**
 * Represent data corresponding to a table.
 * TODO: Should this be Iterable<List<Value>> instead?
 *
 * @author Shardul Deo
 */
public interface TableData {

  /**
   * @return Column ids of this table in the order the data is available.
   */
  List<String> getColumnIds();

  /**
   * @return Rows of data containing cells in the same order as the columns specified by
   *         {@link #getColumnIds()}.
   */
  Iterable<List<String>> getData();
}
