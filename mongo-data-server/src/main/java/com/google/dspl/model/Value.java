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
 * An atomic data value.
 *
 * @author Shardul Deo
 */
public final class Value implements DatasetItem<Value> {

  private static final long serialVersionUID = -3990245535813817434L;

  private String stringValue;
  private Double floatValue;
  private Long integerValue;
  private Boolean booleanValue;
  private Long dateValue;
  private Identifier conceptValue;

  /**
   * The type of object returned by this method will be
   * {@code type.getValueType()}.
   *
   * @return Get the value for the given type
   */
  public Object getValue(DataType type) {
    switch (type) {
      case STRING:
        return stringValue;
      case FLOAT:
        return floatValue;
      case INTEGER:
        return integerValue;
      case BOOLEAN:
        return booleanValue;
      case DATE:
        return dateValue;
      case CONCEPT:
        return conceptValue;
    }
    return null;
  }

  /**
   * @return The value if it is a string
   */
  public String getStringValue() {
    return stringValue;
  }

  /**
   * Sets the string value
   */
  public void setStringValue(String stringValue) {
    this.stringValue = stringValue;
  }

  /**
   * @return The value if it is a float
   */
  public Double getFloatValue() {
    return floatValue;
  }

  /**
   * Sets the float value
   */
  public void setFloatValue(Double floatValue) {
    this.floatValue = floatValue;
  }

  /**
   * @return The value if it is an integer
   */
  public Long getIntegerValue() {
    return integerValue;
  }

  /**
   * Sets the integer value
   */
  public void setIntegerValue(Long integerValue) {
    this.integerValue = integerValue;
  }

  /**
   * @return The value if it is a boolean
   */
  public Boolean getBooleanValue() {
    return booleanValue;
  }

  /**
   * Sets the boolean value
   */
  public void setBooleanValue(Boolean booleanValue) {
    this.booleanValue = booleanValue;
  }

  /**
   * @return The value if it is a date
   */
  public Long getDateValue() {
    return dateValue;
  }

  /**
   * Sets the date value
   */
  public void setDateValue(Long dateValue) {
    this.dateValue = dateValue;
  }

  /**
   * @return The value if it is a concept
   */
  public Identifier getConceptValue() {
    return conceptValue;
  }

  /**
   * Sets the concept value
   */
  public void setConceptValue(Identifier conceptValue) {
    this.conceptValue = conceptValue;
  }

  @Override
  public Value clone() {
    Value value = new Value();
    value.booleanValue = booleanValue;
    value.conceptValue = DatasetItems.cloneItem(conceptValue);
    value.dateValue = dateValue;
    value.floatValue = floatValue;
    value.integerValue = integerValue;
    value.stringValue = stringValue;

    checkState(equals(value));
    return value;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(stringValue, floatValue, integerValue, booleanValue, dateValue,
        conceptValue);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Value)) {
      return false;
    }
    Value other = (Value) obj;
    return Objects.equal(stringValue, other.stringValue)
        && Objects.equal(floatValue, other.floatValue)
        && Objects.equal(integerValue, other.integerValue)
        && Objects.equal(booleanValue, other.booleanValue)
        && Objects.equal(dateValue, other.dateValue)
        && Objects.equal(conceptValue, other.conceptValue);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("stringValue", stringValue)
        .add("floatValue", floatValue)
        .add("integerValue", integerValue)
        .add("booleanValue", booleanValue)
        .add("dateValue", dateValue)
        .add("conceptValue", conceptValue)
        .toString();
  }
}
