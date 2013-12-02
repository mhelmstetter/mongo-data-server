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

import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.Objects;

/**
 * A global identifier used in the dataset (for concepts, slices and topics). The same Identifier
 * value can not appear twice as a global identifier in a dataset.
 *
 * @author Shardul Deo
 */
public final class Identifier implements DatasetItem<Identifier> {

  private static final long serialVersionUID = -3717327571695460252L;

  private String datasetId;
  private String objectId;

  public Identifier() {
  }

  public Identifier(String datasetId, String objectId) {
    this.datasetId = datasetId;
    this.objectId = objectId;
  }
  
  /**
   * @return Identifier of the dataset this object is defined in
   */
  public String getDatasetId() {
    return datasetId;
  }

  /**
   * Sets identifier of the dataset this object is defined in
   */
  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  /**
   * @return Unique identifier of the object within the dataset
   */
  public String getObjectId() {
    return objectId;
  }

  /**
   * Sets unique identifier of the object within the dataset
   */
  public void setObjectId(String objectId) {
    this.objectId = objectId;
  }

  @Override
  public Identifier clone() {
    Identifier identifier = new Identifier();
    identifier.datasetId = datasetId;
    identifier.objectId = objectId;

    checkState(equals(identifier));
    return identifier;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(datasetId, objectId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Identifier)) {
      return false;
    }

    Identifier other = (Identifier) obj;
    return Objects.equal(datasetId, other.datasetId)
        && Objects.equal(objectId, other.objectId);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("datasetId", datasetId)
        .add("objectId", objectId)
        .toString();
  }
}
