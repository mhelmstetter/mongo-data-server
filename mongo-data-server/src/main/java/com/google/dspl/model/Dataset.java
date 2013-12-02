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

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * The top level object that describes a dataset. This is based on the DSPL
 * schema defined at http://code.google.com/apis/publicdata/docs/schema/dspl9.html
 *
 * @author Shardul Deo
 */
@Document(collection="datasets")
public final class Dataset implements DatasetItem<Dataset> {
  private static final long serialVersionUID = 7234410480596199516L;

  @Id
  private String datasetId;
  private String name;
  private Info info;
  private Info provider;
  private List<Attribute> attributes = Lists.newArrayList();
  private List<Topic> topics = Lists.newArrayList();
  private List<Concept> concepts = Lists.newArrayList();
  private List<Slice> slices = Lists.newArrayList();
  private List<Table> tables = Lists.newArrayList();

  /**
   * @return The identifier of this dataset
   */
  public String getDatasetId() {
    return datasetId;
  }

  /**
   * Sets the identifier of this dataset
   */
  public void setDatasetId(String datasetId) {
    this.datasetId = datasetId;
  }

  /**
   * @return General information about the dataset
   */
  public Info getInfo() {
    return info;
  }

  /**
   * Sets general information about the dataset
   */
  public void setInfo(Info info) {
    this.info = info;
  }

  /**
   * @return General information about the dataset provider
   */
  public Info getProvider() {
    return provider;
  }

  /**
   * Sets general information about the dataset provider
   */
  public void setProvider(Info provider) {
    this.provider = provider;
  }

  /**
   * @return Attributes associated with this dataset
   */
  public List<Attribute> getAttributes() {
    return immutableCopyOf(attributes);
  }

  /**
   * Sets attributes associated with this dataset
   */
  public void setAttributes(List<Attribute> attributes) {
    this.attributes.clear();
    this.attributes.addAll(checkNotNull(attributes));
  }

  /**
   * Adds an attribute to this dataset
   */
  public void addAttribute(Attribute attribute) {
    attributes.add(attribute);
  }

  public void removeAttribute(Attribute attribute) {
    attributes.remove(attribute);
  }

  /**
   * @return Topics defined in this dataset
   */
  public List<Topic> getTopics() {
    return immutableCopyOf(topics);
  }

  /**
   * Sets topics defined in this dataset
   */
  public void setTopics(List<Topic> topics) {
    this.topics.clear();
    this.topics.addAll(checkNotNull(topics));
  }

  /**
   * Adds a topic to this dataset
   */
  public void addTopic(Topic topic) {
    topics.add(topic);
  }

  public void removeTopic(Topic topic) {
    topics.remove(topic);
  }

  /**
   * @return Concepts defined in this dataset
   */
  public List<Concept> getConcepts() {
    return immutableCopyOf(concepts);
  }

  /**
   * Sets concepts defined in this dataset
   */
  public void setConcepts(List<Concept> concepts) {
    this.concepts.clear();
    this.concepts.addAll(checkNotNull(concepts));
  }

  /**
   * Adds a concept to this dataset
   */
  public void addConcept(Concept concept) {
    concepts.add(concept);
  }

  /**
   * Removes a concept from this dataset
   */
  public void removeConcept(Concept concept) {
    concepts.remove(concept);
  }

  /**
   * @return Slices defined in this dataset
   */
  public List<Slice> getSlices() {
    return immutableCopyOf(slices);
  }

  /**
   * Sets slices defined in this dataset
   */
  public void setSlices(List<Slice> slices) {
    this.slices.clear();
    this.slices.addAll(checkNotNull(slices));
  }

  /**
   * Adds a slice to this dataset
   */
  public void addSlice(Slice slice) {
    slices.add(slice);
  }

  /**
   * Removes a slice from this dataset
   */
  public void removeSlice(Slice slice) {
    slices.remove(slice);
  }

  /**
   * Remove all slices from this dataset
   */
  public void clearSlices() {
    slices.clear();
  }

  /**
   * @return Tables defined in this dataset
   */
  public List<Table> getTables() {
    return immutableCopyOf(tables);
  }

  /**
   * Sets tables defined in this dataset
   */
  public void setTables(List<Table> tables) {
    this.tables.clear();
    this.tables.addAll(checkNotNull(tables));
  }

  /**
   * Adds a table to this dataset
   */
  public void addTable(Table table) {
    tables.add(table);
  }

  /**
   * Remove a table from this dataset
   */
  public void removeTable(Table table) {
    tables.remove(table);
  }

  /**
   * Remove all tables from this dataset
   */
  public void clearTables() {
    tables.clear();
  }

  @Override
  public Dataset clone() {
    Dataset dataset = new Dataset();
    dataset.datasetId = datasetId;
    dataset.info = cloneItem(info);
    dataset.provider = cloneItem(provider);
    dataset.setAttributes(cloneList(attributes));
    dataset.setConcepts(cloneList(concepts));
    dataset.setSlices(cloneList(slices));
    dataset.setTables(cloneList(tables));
    dataset.setTopics(cloneList(topics));

    checkState(equals(dataset));
    return dataset;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(datasetId,
        info,
        provider,
        attributes,
        topics,
        concepts,
        slices,
        tables);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }

    if (!(obj instanceof Dataset)) {
      return false;
    }

    Dataset other = (Dataset) obj;
    return Objects.equal(datasetId, other.datasetId)
        && Objects.equal(info, other.info)
        && Objects.equal(provider, other.provider)
        && Objects.equal(attributes, other.attributes)
        && Objects.equal(topics, other.topics)
        && Objects.equal(concepts, other.concepts)
        && Objects.equal(slices, other.slices)
        && Objects.equal(tables, other.tables);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("datasetId", datasetId)
        .toString();
  }

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}
}
