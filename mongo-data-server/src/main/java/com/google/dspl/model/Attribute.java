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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.dspl.model.DatasetItems.cloneItem;
import static com.google.dspl.model.DatasetItems.immutableCopyOf;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * An attribute of a dataset or a concept.
 *
 * @author Shardul Deo
 */
public final class Attribute implements DatasetItem<Attribute> {

  private static final long serialVersionUID = 5637068591381759694L;

  private String id;
  private Identifier concept;
  private Info info;
  private DataType type;
  private Value value;
  private Map<String, String> stringValues = Maps.newHashMap();

  /**
   * @return The identifier of the attribute
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the identifier of the attribute
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return The concept that has an instance that is the value of this attribute
   */
  public Identifier getConcept() {
    return concept;
  }

  /**
   * Sets the concept that has an instance that is the value of this attribute
   */
  public void setConcept(Identifier concept) {
    this.concept = concept;
  }

  /**
   * @return Textual information about the attribute
   */
  public Info getInfo() {
    return info;
  }

  /**
   * Sets textual information about the attribute
   */
  public void setInfo(Info info) {
    this.info = info;
  }

  /**
   * @return The data type of the attribute
   */
  public DataType getType() {
    return type;
  }

  /**
   * Sets the data type of the attribute
   */
  public void setType(DataType type) {
    this.type = type;
  }

  /**
   * @return Value of the attribute if it is not a multi-locale value
   */
  public Value getValue() {
    return value;
  }

  /**
   * Sets value of the attribute
   */
  public void setValue(Value value) {
    this.value = value;
  }

  /**
   * @return Value of the attribute for the given language if it is a
   *         multi-locale string value
   */
  public String getValue(String language) {
    return stringValues.get(language);
  }

  /**
   * Sets the value of the attribute for the given language
   */
  public void setValue(String language, String value) {
    stringValues.put(language, value);
  }

  /**
   * @return Map of language to localized string values of the attribute if it
   *         is a multi-locale string value
   */
  public Map<String, String> getStringValues() {
    return immutableCopyOf(stringValues);
  }

  /**
   * Sets map of language to localized string values of the attribute if it is a
   * multi-locale string value
   */
  public void setStringValues(Map<String, String> stringValues) {
    this.stringValues.clear();
    this.stringValues.putAll(checkNotNull(stringValues));
  }

  @Override
  public Attribute clone() {
    Attribute attribute = new Attribute();
    attribute.id = id;
    attribute.concept = cloneItem(concept);
    attribute.info = cloneItem(info);
    attribute.setStringValues(stringValues);
    attribute.type = type;
    attribute.value = cloneItem(value);

    checkState(equals(attribute));
    return attribute;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, concept, info, type, value, stringValues);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Attribute)) {
      return false;
    }
    Attribute other = (Attribute) obj;
    return Objects.equal(id, other.id)
        && Objects.equal(concept, other.concept)
        && Objects.equal(info, other.info)
        && Objects.equal(type, other.type)
        && Objects.equal(value, other.value)
        && Objects.equal(stringValues, other.stringValues);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("id", id)
        .add("concept", concept)
        .toString();
  }
}
