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
import static com.google.dspl.model.DatasetItems.cloneList;
import static com.google.dspl.model.DatasetItems.immutableCopyOf;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * TODO: Add pluralName, totalName and synonym.
 *
 * @author Shardul Deo
 */
public final class Concept implements DatasetItem<Concept> {

  private static final long serialVersionUID = -1430724464102265865L;

  private String id;
  private Info info;
  private DataType type;
  private Identifier parent;
  private Value defaultValue;
  private ConceptTableMapping table;

  private List<Identifier> topics = Lists.newArrayList();
  private List<Attribute> attributes = Lists.newArrayList();
  private List<ConceptProperty> properties = Lists.newArrayList();

  /**
   * @return The unique identifier of the concept in the dataset
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the concept in the dataset
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return Textual information about the concept
   */
  public Info getInfo() {
    return info;
  }

  /**
   * Sets textual information about the concept
   */
  public void setInfo(Info info) {
    this.info = info;
  }

  /**
   * @return The data type of the concept
   */
  public DataType getType() {
    return type;
  }

  /**
   * Sets the data type of the concept
   */
  public void setType(DataType type) {
    this.type = type;
  }

  /**
   * @return The unique identifier of a concept that this concept extends
   */
  public Identifier getParent() {
    return parent;
  }

  /**
   * Sets the unique identifier of a concept that this concept extends
   */
  public void setParent(Identifier parent) {
    this.parent = parent;
  }

  /**
   * @return The topics the concept is associated with
   */
  public List<Identifier> getTopics() {
    return immutableCopyOf(topics);
  }

  /**
   * Sets the topics the concept is associated with
   */
  public void setTopics(List<Identifier> topicIds) {
    this.topics.clear();
    this.topics.addAll(checkNotNull(topicIds));
  }

  /**
   * Adds a topic the concept is associated with
   */
  public void addTopic(Identifier topicId) {
    topics.add(topicId);
  }

  public void removeTopic(Identifier topicId) {
    topics.remove(topicId);
  }

  /**
   * @return The attributes of the concept
   */
  public List<Attribute> getAttributes() {
    return immutableCopyOf(attributes);
  }

  /**
   * Sets the attributes of the concept
   */
  public void setAttributes(List<Attribute> attributes) {
    this.attributes.clear();
    this.attributes.addAll(checkNotNull(attributes));
  }

  /**
   * Adds an attribute to the concept
   */
  public void addAttribute(Attribute attribute) {
    attributes.add(attribute);
  }

  public void removeAttribute(Attribute attribute) {
    attributes.remove(attribute);
  }

  /**
   * @return The properties of the concept
   */
  public List<ConceptProperty> getProperties() {
    return immutableCopyOf(properties);
  }

  /**
   * Sets the properties of the concept
   */
  public void setProperties(List<ConceptProperty> properties) {
    this.properties.clear();
    this.properties.addAll(checkNotNull(properties));
  }

  /**
   * Adds a property to the concept
   */
  public void addProperty(ConceptProperty property) {
    properties.add(property);
  }

  public void removeProperty(ConceptProperty property) {
    properties.remove(property);
  }

  /**
   * @return The default value of the concept
   */
  public Value getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value of the concept
   */
  public void setDefaultValue(Value defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * @return The reference to a table that contains all the possible values for
   *         the concept and its non-constant properties
   */
  public ConceptTableMapping getTable() {
    return table;
  }

  /**
   * Sets the reference to a table that contains all the possible values for the
   * concept and its non-constant properties
   */
  public void setTable(ConceptTableMapping table) {
    this.table = table;
  }

  @Override
  public Concept clone() {
    Concept concept = new Concept();
    concept.setAttributes(cloneList(attributes));
    concept.defaultValue = cloneItem(defaultValue);
    concept.id = id;
    concept.info = cloneItem(info);
    concept.parent = cloneItem(parent);
    concept.setProperties(cloneList(properties));
    concept.table = cloneItem(table);
    concept.setTopics(cloneList(topics));
    concept.type = type;

    checkState(equals(concept));
    return concept;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id,
        info,
        type,
        parent,
        topics,
        attributes,
        properties,
        defaultValue,
        table);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Concept)) {
      return false;
    }

    Concept other = (Concept) obj;
    return Objects.equal(id, other.id)
        && Objects.equal(info, other.info)
        && Objects.equal(type, other.type)
        && Objects.equal(parent, other.parent)
        && Objects.equal(topics, other.topics)
        && Objects.equal(attributes, other.attributes)
        && Objects.equal(properties, other.properties)
        && Objects.equal(defaultValue, other.defaultValue)
        && Objects.equal(table, other.table);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("id", id)
        .toString();
  }

  /**
   * A property of the concept. Properties represent additional information
   * about instances of the concept (e.g., a concept "city" may have a property
   * "country").
   */
  public static final class ConceptProperty implements DatasetItem<ConceptProperty> {

    private static final long serialVersionUID = -6378497850857941696L;

    private String id;
    private Info info;
    private DataType type;
    private Identifier concept;
    private boolean isParent;
    private boolean isMapping;
    private boolean isRequired;

    /**
     * @return The identifier of the concept property
     */
    public String getId() {
      return id;
    }

    /**
     * Sets the identifier of the concept property
     */
    public void setId(String id) {
      this.id = id;
    }

    /**
     * @return Textual information about the property
     */
    public Info getInfo() {
      return info;
    }

    /**
     * Sets textual information about the property
     */
    public void setInfo(Info info) {
      this.info = info;
    }

    /**
     * @return The data type of the concept property
     */
    public DataType getType() {
      return type;
    }

    /**
     * Sets the data type of the concept property
     */
    public void setType(DataType type) {
      this.type = type;
    }

    /**
     * @return The reference to a concept that corresponds to the values of the
     *         property
     */
    public Identifier getConcept() {
      return concept;
    }

    /**
     * Sets the reference to a concept that corresponds to the values of the
     * property
     */
    public void setConcept(Identifier concept) {
      this.concept = concept;
    }

    /**
     * @return Whether this property denotes a hierarchical relationship between
     *         this concept and the referenced concept
     */
    public boolean isParent() {
      return isParent;
    }

    /**
     * Sets whether this property denotes a hierarchical relationship between
     * this concept and the referenced concept
     */
    public void setIsParent(boolean isParent) {
      this.isParent = isParent;
    }

    /**
     * @return Whether this property denotes a mapping (1-to-1) relationship
     *         between this concept and the referenced concept
     */
    public boolean isMapping() {
      return isMapping;
    }

    /**
     * Sets whether this property denotes a mapping (1-to-1) relationship
     * between this concept and the referenced concept
     */
    public void setIsMapping(boolean isMapping) {
      this.isMapping = isMapping;
    }

    /**
     * @return Whether any instance of the enclosing concept must provide a
     *         value for this property. In particular, if the concept provides a
     *         table, then that table must have a column for this property, with
     *         all values filled in.
     */
    public boolean isRequired() {
      return isRequired;
    }

    /**
     * Sets whether any instance of the enclosing concept must provide a value
     * for this property
     */
    public void setIsRequired(boolean isRequired) {
      this.isRequired = isRequired;
    }

    @Override
    public ConceptProperty clone() {
      ConceptProperty property = new ConceptProperty();
      property.concept = cloneItem(concept);
      property.id = id;
      property.info = cloneItem(info);
      property.isMapping = isMapping;
      property.isParent = isParent;
      property.isRequired = isRequired;
      property.type = type;

      checkState(equals(property));
      return property;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(id, info, type, concept, isParent, isMapping, isRequired);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }

      if (!(obj instanceof ConceptProperty)) {
        return false;
      }

      ConceptProperty other = (ConceptProperty) obj;
      return Objects.equal(id, other.id)
          && Objects.equal(info, other.info)
          && Objects.equal(type, other.type)
          && Objects.equal(concept, other.concept)
          && Objects.equal(isParent, other.isParent)
          && Objects.equal(isMapping, other.isMapping)
          && Objects.equal(isRequired, other.isRequired);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this)
          .add("id", id)
          .add("concept", concept)
          .toString();
    }
  }

  /**
   * A mapping to the id of the table column that contains the values of a
   * property of the concept. This mapping may be omitted if the table column
   * that contains the concept property values has the property id as its id.
   *
   * A single property can be mapped to multiple table columns (one per
   * language) by specifying different values for the language attribute.
   */
  public static final class ConceptTableMapping implements DatasetItem<ConceptTableMapping> {

    private static final long serialVersionUID = -2951597092290522203L;

    private String tableId;
    private String conceptMappingColumn;
    private List<PropertyMapping> propertyMappings = Lists.newArrayList();

    /**
     * @return The identifier of the table that contains data for the concept
     */
    public String getTableId() {
      return tableId;
    }

    /**
     * Sets the identifier of the table that contains data for the concept
     */
    public void setTableId(String tableId) {
      this.tableId = tableId;
    }

    /**
     * @return The identifier of the table column that contains the values of
     *         the concept
     */
    public String getConceptMappingColumn() {
      return conceptMappingColumn;
    }

    /**
     * Sets the identifier of the table column that contains the values of the
     * concept
     */
    public void setConceptMappingColumn(String conceptMappingColumn) {
      this.conceptMappingColumn = conceptMappingColumn;
    }

    /**
     * @return the mappings to the identifiers of the table columns that contain
     *         the values of the properties of the concept
     */
    public List<PropertyMapping> getPropertyMappings() {
      return immutableCopyOf(propertyMappings);
    }

    /**
     * Sets the mappings to the identifiers of the table columns that contain
     * the values of the properties of the concept
     */
    public void setPropertyMappings(Collection<PropertyMapping> propertyMappings) {
      this.propertyMappings.clear();
      this.propertyMappings.addAll(checkNotNull(propertyMappings));
    }

    /**
     * Adds a mapping to the identifier of the table column that contains the
     * values of a property of the concept
     */
    public void addPropertyMapping(PropertyMapping propertyMapping) {
      propertyMappings.add(propertyMapping);
    }

    public void removePropertyMapping(PropertyMapping propertyMapping) {
      propertyMappings.remove(propertyMapping);
    }

    @Override
    public ConceptTableMapping clone() {
      ConceptTableMapping mapping = new ConceptTableMapping();
      mapping.conceptMappingColumn = conceptMappingColumn;
      mapping.setPropertyMappings(cloneList(propertyMappings));
      mapping.tableId = tableId;

      checkState(equals(mapping));
      return mapping;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(tableId, conceptMappingColumn, propertyMappings);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }

      if (!(obj instanceof ConceptTableMapping)) {
        return false;
      }

      ConceptTableMapping other = (ConceptTableMapping) obj;
      return Objects.equal(tableId, other.tableId)
          && Objects.equal(conceptMappingColumn, other.conceptMappingColumn)
          && Objects.equal(propertyMappings, other.propertyMappings);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this)
          .add("tableId", tableId)
          .toString();
    }
  }

  /**
   * A mapping to the identifier of the table column that contains the values of
   * a property of the concept. This mapping may be omitted if the table column
   * that contains the concept property values has the property identifier as
   * its identifier.
   *
   * A single property can be mapped to multiple table columns (one per
   * language) by specifying different values for the language attribute.
   */
  public static final class PropertyMapping implements DatasetItem<PropertyMapping> {

    private static final long serialVersionUID = 8381661318567808769L;

    private String propertyId;
    private String language;
    private String toColumn;

    /**
     * @return The identifier of the mapped concept property
     */
    public String getPropertyId() {
      return propertyId;
    }

    /**
     * Sets the identifier of the mapped concept property
     */
    public void setPropertyId(String propertyId) {
      this.propertyId = propertyId;
    }

    /**
     * @return The language of the values in the mapped column
     */
    public String getLanguage() {
      return language;
    }

    /**
     * Sets the language of the values in the mapped column
     */
    public void setLanguage(String language) {
      this.language = language;
    }

    /**
     * @return The identifier of the mapped table column
     */
    public String getToColumn() {
      return toColumn;
    }

    /**
     * Sets the identifier of the mapped table column
     */
    public void setToColumn(String toColumn) {
      this.toColumn = toColumn;
    }

    @Override
    public PropertyMapping clone() {
      PropertyMapping mapping = new PropertyMapping();
      mapping.language = language;
      mapping.propertyId = propertyId;
      mapping.toColumn = toColumn;

      checkState(equals(mapping));
      return mapping;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(propertyId, language, toColumn);
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }

      if (!(obj instanceof PropertyMapping)) {
        return false;
      }

      PropertyMapping other = (PropertyMapping) obj;
      return Objects.equal(propertyId, other.propertyId)
          && Objects.equal(language, other.language)
          && Objects.equal(toColumn, other.toColumn);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this)
          .add("propertyId", propertyId)
          .add("language", language)
          .add("toColumn", toColumn)
          .toString();
    }
  }
}
