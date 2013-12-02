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
 * Textual information about an element of the dataset.
 * 
 * @author Shardul Deo
 */
public final class Info implements DatasetItem<Info> {

    private static final long serialVersionUID = -3188539543404404119L;

    private String name;
    private String description;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public Info clone() {
        Info info = new Info();
        info.setDescription(description);
        info.setName(name);
        info.setUrl(url);

        checkState(equals(info));
        return info;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, description, url);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Info)) {
            return false;
        }

        Info other = (Info) obj;
        return Objects.equal(name, other.name) && Objects.equal(description, other.description)
                && Objects.equal(url, other.url);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("name", name).toString();
    }
}
