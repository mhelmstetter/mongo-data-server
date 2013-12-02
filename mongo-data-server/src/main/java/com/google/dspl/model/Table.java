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

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * A table provides data for the dataset. A table may provide data for a concept
 * or for a slice.
 * 
 * @author Shardul Deo
 */
public final class Table implements DatasetItem<Table> {

    private static final long serialVersionUID = -2823227608626177894L;

    private String id;
    private Info info;
    private Data data;
    private List<Column> columns = Lists.newArrayList();
    private List<String> sources = new ArrayList<String>();

    /**
     * @return The unique identifier of the table in the dataset
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the table in the dataset
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Textual information about the table
     */
    public Info getInfo() {
        return info;
    }

    /**
     * Sets textual information about the table
     */
    public void setInfo(Info info) {
        this.info = info;
    }

    /**
     * @return Specifications of the columns in the table
     */
    public List<Column> getColumns() {
        return immutableCopyOf(columns);
    }

    /**
     * Sets specifications of the columns in the table
     */
    public void setColumns(List<Column> columns) {
        this.columns.clear();
        this.columns.addAll(checkNotNull(columns));
    }

    /**
     * Adds specification of a column to the table
     */
    public void addColumn(Column column) {
        columns.add(column);
    }

    public void removeColumn(Column column) {
        columns.remove(column);
    }

    /**
     * @return Source of the data for this table
     */
    public Data getData() {
        return data;
    }

    /**
     * Sets source of the data for this table
     */
    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public Table clone() {
        Table table = new Table();
        table.setColumns(cloneList(columns));
        table.id = id;
        table.info = cloneItem(info);
        table.data = cloneItem(data);

        checkState(equals(table));
        return table;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, info, columns, data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Table)) {
            return false;
        }

        Table other = (Table) obj;
        return Objects.equal(id, other.id) && Objects.equal(info, other.info) && Objects.equal(columns, other.columns)
                && Objects.equal(data, other.data);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("id", id).toString();
    }

    /**
     * Specification of a column in a table
     */
    public static final class Column implements DatasetItem<Column> {

        private static final long serialVersionUID = -1285578386460814507L;

        private String id;
        private String name;
        private DataType type;
        private String format;
        private Value constantValue;
        private ColumnMapping columnMapping;

        /**
         * @return The identifier of the table column
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the identifier of the table column
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The data type of the table column
         */
        public DataType getType() {
            return type;
        }

        /**
         * Sets the data type of the table column
         */
        public void setType(DataType type) {
            this.type = type;
        }

        /**
         * @return The format for the column, used to parse a textual
         *         representation of the values
         */
        public String getFormat() {
            return format;
        }

        /**
         * Sets the format for the column, used to parse a textual
         * representation of the values
         */
        public void setFormat(String format) {
            this.format = format;
        }

        /**
         * @return The constant value for the column
         */
        public Value getConstantValue() {
            return constantValue;
        }

        /**
         * Sets the constant value for the column
         */
        public void setConstantValue(Value value) {
            this.constantValue = value;
        }

        /**
         * @return Mapping from the column to a concept or a property of a
         *         concept
         */
        public ColumnMapping getColumnMapping() {
            return columnMapping;
        }

        /**
         * Sets mapping from the column to a concept or a property of a concept
         */
        public void setColumnMapping(ColumnMapping columnMapping) {
            this.columnMapping = columnMapping;
        }

        @Override
        public Column clone() {
            Column column = new Column();
            column.format = format;
            column.id = id;
            column.type = type;
            column.constantValue = cloneItem(constantValue);
            column.columnMapping = cloneItem(columnMapping);

            checkState(equals(column));
            return column;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id, type, format, constantValue, columnMapping);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof Column)) {
                return false;
            }

            Column other = (Column) obj;
            return Objects.equal(id, other.id) && Objects.equal(type, other.type)
                    && Objects.equal(format, other.format) && Objects.equal(constantValue, other.constantValue)
                    && Objects.equal(columnMapping, other.columnMapping);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("id", id).toString();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * A mapping from a table column to a concept or concept property. The
     * mapping may also specify if the column plays the role of a dimension or a
     * metric.
     */
    public static final class ColumnMapping implements DatasetItem<ColumnMapping> {

        private static final long serialVersionUID = 5864557258259437688L;

        private Identifier concept;
        private String propertyId;
        private String language;
        private boolean isDimension = false;
        private boolean isMetric = false;

        /**
         * @return The id of the concept this column maps to
         */
        public Identifier getConcept() {
            return concept;
        }

        /**
         * Sets the id of the concept this column maps to
         */
        public void setConcept(Identifier concept) {
            this.concept = concept;
        }

        /**
         * @return The id of the concept property this column maps to
         */
        public String getPropertyId() {
            return propertyId;
        }

        /**
         * Sets the id of the concept property this column maps to
         */
        public void setPropertyId(String propertyId) {
            this.propertyId = propertyId;
        }

        /**
         * @return The language/locale of the values in the mapped column
         */
        public String getLanguage() {
            return language;
        }

        /**
         * Sets the language/locale of the values in the mapped column
         */
        public void setLanguage(String language) {
            this.language = language;
        }

        /**
         * @return If this column plays the role of a dimension
         */
        public boolean isDimension() {
            return isDimension;
        }

        /**
         * Sets if this column plays the role of a dimension
         */
        public void setDimension(boolean isDimension) {
            this.isDimension = isDimension;
        }

        /**
         * @return If this column plays the role of a metric
         */
        public boolean isMetric() {
            return isMetric;
        }

        /**
         * Sets if this column plays the role of a metric
         */
        public void setMetric(boolean isMetric) {
            this.isMetric = isMetric;
        }

        @Override
        public ColumnMapping clone() {
            ColumnMapping columnMapping = new ColumnMapping();
            columnMapping.concept = cloneItem(concept);
            columnMapping.propertyId = propertyId;
            columnMapping.language = language;
            columnMapping.isDimension = isDimension;
            columnMapping.isMetric = isMetric;

            checkState(equals(columnMapping));
            return columnMapping;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(concept, propertyId, language, isDimension, isMetric);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof ColumnMapping)) {
                return false;
            }

            ColumnMapping other = (ColumnMapping) obj;
            return Objects.equal(concept, other.concept) && Objects.equal(propertyId, other.propertyId)
                    && Objects.equal(language, other.language) && Objects.equal(isDimension, other.isDimension)
                    && Objects.equal(isMetric, other.isMetric);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("concept", concept).toString();
        }
    }

    /**
     * Specification of the data source for a table
     */
    public static final class Data implements DatasetItem<Data> {

        private static final long serialVersionUID = -3237689169016975281L;

        private String file;
        private String format = "csv";
        private String encoding = "utf-8";

        /**
         * @return File which contains the data for the table
         */
        public String getFile() {
            return file;
        }

        /**
         * Sets file which contains the data for the table
         */
        public void setFile(String file) {
            this.file = file;
        }

        /**
         * @return Format of the data file
         */
        public String getFormat() {
            return format;
        }

        /**
         * Sets format of the data file
         */
        public void setFormat(String format) {
            this.format = format;
        }

        /**
         * @return Encoding of the file
         */
        public String getEncoding() {
            return encoding;
        }

        /**
         * Sets encoding of the file
         */
        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }

        @Override
        public Data clone() {
            Data data = new Data();
            data.file = file;
            data.format = format;
            data.encoding = encoding;

            checkState(equals(data));
            return data;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(file, format, encoding);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof Data)) {
                return false;
            }

            Data other = (Data) obj;
            return Objects.equal(file, other.file) && Objects.equal(format, other.format)
                    && Objects.equal(encoding, other.encoding);
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this).add("file", file).toString();
        }
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }
    
    public void addSource(String source) {
        sources.add(source);
    }
}
