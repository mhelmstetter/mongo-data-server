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

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * A slice describes a combination of concepts for which data exists. Metrics
 * are the concepts that provide values, while dimensions are the concepts used
 * to access these values. More precisely, the dimensions are a primary key for
 * the data of the slice. That is, for any combination of values of the
 * dimensions, there is at most one data row in the slice.
 *
 * @author Shardul Deo
 */
public final class Slice implements DatasetItem<Slice> {

  private static final long serialVersionUID = -2411795515911887474L;

  private String id;
  private String name;
  private String type;
  private String table;
  private Info info;
  private TableMapping tableMapping;
  private List<String> dimensions = new ArrayList<String>();
  private List<String> metrics = new ArrayList<String>();
  
  private SortedMap<String, String> keyToCompressedKeyMap = new TreeMap<String, String>();

  /**
   * @return The unique identifier of the slice in the dataset
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the slice in the dataset
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return Textual information about the slice
   */
  public Info getInfo() {
    return info;
  }

  /**
   * Sets textual information about the slice
   */
  public void setInfo(Info info) {
    this.info = info;
  }

  /**
   * @return Dimensions in the slice
   */
  public List<String> getDimensions() {
    return immutableCopyOf(dimensions);
  }

  /**
   * Sets dimensions for the slice
   */
  public void setDimensions(List<String> dimensions) {
    this.dimensions.clear();
    this.dimensions.addAll(checkNotNull(dimensions));
  }

  /**
   * Adds a dimension to the slice
   */
  public void addDimension(String dimension) {
    dimensions.add(dimension);
  }

  public void removeDimension(Identifier dimension) {
    dimensions.remove(dimension);
  }

  /**
   * @return Metrics in the slice
   */
  public List<String> getMetrics() {
    return immutableCopyOf(metrics);
  }

  /**
   * Sets metrics for the slice
   */
  public void setMetrics(List<String> metrics) {
    this.metrics.clear();
    this.metrics.addAll(checkNotNull(metrics));
  }

  /**
   * Adds a metric to the slice
   */
  public void addMetric(String metric) {
    metrics.add(metric);
  }

  public void removeMetric(Identifier metric) {
    metrics.remove(metric);
  }

  /**
   * @return Mapping to a table where the slice data can be accessed
   */
  public TableMapping getTable() {
    return tableMapping;
  }

  /**
   * Sets mapping to a table where the slice data can be accessed
   */
  public void setTable(TableMapping table) {
    this.tableMapping = table;
  }

  @Override
  public Slice clone() {
    Slice slice = new Slice();
    slice.setDimensions(dimensions);
    slice.id = id;
    slice.info = cloneItem(info);
    slice.setMetrics(metrics);
    slice.tableMapping = cloneItem(tableMapping);

    checkState(equals(slice));
    return slice;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, info, dimensions, metrics, tableMapping);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Slice)) {
      return false;
    }
    Slice other = (Slice) obj;
    return Objects.equal(id, other.id)
        && Objects.equal(info, other.info)
        && Objects.equal(dimensions, other.dimensions)
        && Objects.equal(metrics, other.metrics)
        && Objects.equal(tableMapping, other.tableMapping);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("id", id)
        .toString();
  }

  /**
   * A mapping to a table that provides data for a slice.
   */
  public static final class TableMapping implements DatasetItem<TableMapping> {

    private static final long serialVersionUID = -3379079476739101841L;

    private String tableId;
    private List<ConceptMapping> dimensionMappings = Lists.newArrayList();
    private List<ConceptMapping> metricMappings = Lists.newArrayList();

    /**
     * @return The identifier of the table that contains data for the slice
     */
    public String getTableId() {
      return tableId;
    }

    /**
     * Sets the identifier of the table that contains data for the slice
     */
    public void setTableId(String tableId) {
      this.tableId = tableId;
    }

    /**
     * @return Mappings to the table columns that contain values for the
     *         dimensions of the slice
     */
    public List<ConceptMapping> getDimensionMappings() {
      return immutableCopyOf(dimensionMappings);
    }

    /**
     * Sets mappings to the table columns that contain values for the dimensions
     * of the slice
     */
    public void setDimensionMappings(List<ConceptMapping> dimensionMappings) {
      this.dimensionMappings.clear();
      this.dimensionMappings.addAll(checkNotNull(dimensionMappings));
    }

    /**
     * Adds mapping to a table column that contains values for a dimension of
     * the slice
     */
    public void addDimensionMapping(ConceptMapping dimensionMapping) {
      dimensionMappings.add(dimensionMapping);
    }

    public void removeDimensionMapping(ConceptMapping dimensionMapping) {
      dimensionMappings.remove(dimensionMapping);
    }

    /**
     * @return Mappings to the table columns that contain values for the metrics
     *         of the slice
     */
    public List<ConceptMapping> getMetricMappings() {
      return immutableCopyOf(metricMappings);
    }

    /**
     * Sets mappings to the table columns that contain values for the metrics of
     * the slice
     */
    public void setMetricMappings(List<ConceptMapping> metricMappings) {
      this.metricMappings.clear();
      this.metricMappings.addAll(checkNotNull(metricMappings));
    }

    /**
     * Adds mapping to a table column that contains values for a metric of the
     * slice
     */
    public void addMetricMapping(ConceptMapping metricMapping) {
      metricMappings.add(metricMapping);
    }

    public void removeMetricMappings(ConceptMapping metricMapping) {
      metricMappings.remove(metricMapping);
    }

    @Override
    public TableMapping clone() {
      TableMapping mapping = new TableMapping();
      mapping.setDimensionMappings(cloneList(dimensionMappings));
      mapping.setMetricMappings(cloneList(metricMappings));
      mapping.tableId = tableId;

      checkState(equals(mapping));
      return mapping;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(tableId, dimensionMappings, metricMappings);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof TableMapping)) {
        return false;
      }
      TableMapping other = (TableMapping) obj;
      return Objects.equal(tableId, other.tableId)
          && Objects.equal(dimensionMappings, other.dimensionMappings)
          && Objects.equal(metricMappings, other.metricMappings);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this)
          .add("tableId", tableId)
          .toString();
    }
  }

  /**
   * A mapping to the id of the table column that contains the values of a
   * dimension or metric of the slice. This mapping may be omitted if the table
   * column that contains the slice dimension/metric values has the concept id
   * as its column id. If the referenced concept comes from an external dataset,
   * the mapping may be omitted if the id of the column matches the local id of
   * the concept.
   */
  public static final class ConceptMapping implements DatasetItem<ConceptMapping> {

    private static final long serialVersionUID = -778406985100203351L;

    private Identifier concept;
    private String toColumn;

    /**
     * @return The identifier of the mapped dimension/metric concept
     */
    public Identifier getConcept() {
      return concept;
    }

    /**
     * Sets the identifier of the mapped dimension/metric concept
     */
    public void setConcept(Identifier concept) {
      this.concept = concept;
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
    public ConceptMapping clone() {
      ConceptMapping mapping = new ConceptMapping();
      mapping.concept = cloneItem(concept);
      mapping.toColumn = toColumn;

      checkState(equals(mapping));
      return mapping;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(concept, toColumn);
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof ConceptMapping)) {
        return false;
      }
      ConceptMapping other = (ConceptMapping) obj;
      return Objects.equal(concept, other.concept)
          && Objects.equal(toColumn, other.toColumn);
    }

    @Override
    public String toString() {
      return Objects.toStringHelper(this)
          .add("concept", concept)
          .add("toColumn", toColumn)
          .toString();
    }
  }

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getType() {
    return type;
}

public void setType(String type) {
    this.type = type;
}

public void setTable(String table) {
    this.table = table;
}
}
