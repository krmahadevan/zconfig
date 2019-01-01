/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Copyright (c) $year
 * Date: 31/12/18 7:41 PM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Configuration node represents Key/Value pairs.
 */
public abstract class ConfigKeyValueNode extends AbstractConfigNode {
    /**
     * Map containing the defined parameters within a node definition.
     */
    private Map<String, String> keyValues;

    /**
     * Get the defined parameters for a specific node.
     *
     * @return - Map of parameters.
     */
    public Map<String, String> getKeyValues() {
        return keyValues;
    }

    /**
     * Set the parameters for a specific node.
     *
     * @param keyValues - Map of parameters.
     */
    public void setKeyValues(Map<String, String> keyValues) {
        updated();
        this.keyValues = keyValues;
    }

    /**
     * Get the value for the specified key.
     *
     * @param key - Parameter key.
     * @return - Parameter value or NULL if not found.
     */
    @JsonIgnore
    public String getValue(String key) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
        if (keyValues != null) {
            return keyValues.get(key);
        }
        return null;
    }

    /**
     * Add a new key/value with the specified key and value.
     *
     * @param key - Parameter key.
     * @param value - Parameter value.
     */
    public void addKeyValue(String key, String value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
        if (keyValues == null) {
            keyValues = new HashMap<>();
        }
        keyValues.put(key, value);
        updated();
    }

    /**
     * Remove the key/value with the specified key.
     * @param key - Parameter key.
     * @return - True if removed, else NULL.
     */
    public boolean removeKeyValue(String key) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(key));
        if (keyValues != null && !keyValues.isEmpty()) {
            if (keyValues.containsKey(key)) {
                keyValues.remove(key);
                updated();
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the key/value pairs.
     *
     * @return - Key/Value pairs string.
     */
    @Override
    public String toString() {
        return String.format("%s:[key/values=%s]", keyValues);
    }
}
