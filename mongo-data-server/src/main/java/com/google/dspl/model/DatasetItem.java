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

import java.io.Serializable;

/**
 * Item that is part of a DSPL {@link Dataset}.  Essentially, one of the POJOs in this package.
 *
 * <p>To be Serializable, especially for GWT serialization, fields should not be final. GWT contains
 * an annotation to document fields that would normally be final, but are not, due to this
 * constraint. That annotation is @NonFinalForGwtSerialization. We have opted to avoid a dependency
 * on GWT for the non-GWT build, so we cannot use this annotation.
 *
 * @author James Grimm
 */
public interface DatasetItem<T> extends Cloneable, Serializable {
  T clone();
}
