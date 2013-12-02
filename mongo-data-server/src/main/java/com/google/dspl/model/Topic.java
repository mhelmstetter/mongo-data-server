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

import java.util.List;

/**
 * A topic is a label that can be attached to concepts. Topics are organized
 * hierarchically.
 *
 * @author Shardul Deo
 */
public final class Topic implements DatasetItem<Topic> {

  private static final long serialVersionUID = 1970354484318205529L;

  private String id;
  private Info info;
  private Identifier parentTopic;
  private List<Topic> childTopics = Lists.newArrayList();

  /**
   * @return The unique identifier of the topic in the dataset
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the unique identifier of the topic in the dataset
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return Textual information about the topic
   */
  public Info getInfo() {
    return info;
  }

  /**
   * Sets textual information about the topic
   */
  public void setInfo(Info info) {
    this.info = info;
  }

  /**
   * @return Parent topic that this topic is a child of
   */
  public Identifier getParentTopic() {
    return parentTopic;
  }

  /**
   * Sets parent topic that this topic is a child of
   */
  public void setParentTopic(Identifier parent) {
    this.parentTopic = parent;
  }

  /**
   * @return Topics that are children of this topic
   */
  public List<Topic> getChildTopics() {
    return immutableCopyOf(childTopics);
  }

  /**
   * Sets topics that are children of this topic
   */
  public void setChildTopics(List<Topic> children) {
    this.childTopics.clear();
    this.childTopics.addAll(checkNotNull(children));
  }

  /**
   * Adds a child topic to this topic
   */
  public void addChildTopic(Topic child) {
    childTopics.add(child);
  }

  public void removeChildTopic(Topic child) {
    childTopics.remove(child);
  }

  @Override
  public Topic clone() {
    Topic topic = new Topic();
    topic.id = id;
    topic.info = cloneItem(info);
    topic.parentTopic = cloneItem(parentTopic);
    topic.setChildTopics(cloneList(childTopics));

    checkState(equals(topic));
    return topic;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, info, parentTopic, childTopics);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Topic)) {
      return false;
    }
    Topic other = (Topic) obj;
    return Objects.equal(id, other.id)
        && Objects.equal(info, other.info)
        && Objects.equal(parentTopic, other.parentTopic)
        && Objects.equal(childTopics, other.childTopics);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this)
        .add("id", id)
        .toString();
  }
}
