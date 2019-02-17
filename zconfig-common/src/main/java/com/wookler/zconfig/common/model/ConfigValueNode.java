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
 * Date: 31/12/18 9:04 PM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.wookler.zconfig.common.ConfigurationException;
import com.wookler.zconfig.common.GlobalConstants;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.annotation.Nonnull;

/**
 * Class represents a configuration value element. All configuration values are treated as String values
 * and converters provided to get a specific value type.
 * <p>
 * JSON:
 * <pre>
 *     name : value,
 *     ...
 * </pre>
 * <p>
 * XML:
 * <pre>
 *     <name value="value"/>
 * </pre>
 */
public class ConfigValueNode extends AbstractConfigNode
        implements IConfigValue<String> {

    /**
     * Configuration value element.
     */
    private String value;

    /**
     * Get the configuration value element.
     *
     * @return - Value.
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Set this config node value to the specified value string.
     *
     * @param value - Value String.
     */
    public void setValue(String value) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(value));
        this.value = value;
    }

    /**
     * Get the value parsed as Boolean.
     * <p>
     * Note: If value string is NULL/Empty will return false.
     *
     * @return - Value parsed as boolean.
     */
    @JsonIgnore
    public boolean getBooleanValue() {
        if (!Strings.isNullOrEmpty(value)) {
            return Boolean.parseBoolean(value);
        }
        return false;
    }

    /**
     * Get the value parsed as Short.
     * <p>
     * Note: If value string is NULL/Empty will return Short.MIN_VALUE.
     *
     * @return - Value parsed as short.
     */
    @JsonIgnore
    public short getShortValue() {
        if (!Strings.isNullOrEmpty(value)) {
            return Short.parseShort(value);
        }
        return Short.MIN_VALUE;
    }

    /**
     * Get the value parsed as Integer.
     * <p>
     * Note: If value string is NULL/Empty will return Integer.MIN_VALUE.
     *
     * @return - Value parsed as integer.
     */
    @JsonIgnore
    public int getIntValue() {
        if (!Strings.isNullOrEmpty(value)) {
            return Integer.parseInt(value);
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Get the value parsed as Long.
     * <p>
     * Note: If value string is NULL/Empty will return Long.MIN_VALUE.
     *
     * @return - Value parsed as long.
     */
    @JsonIgnore
    public long getLongValue() {
        if (!Strings.isNullOrEmpty(value)) {
            return Long.parseLong(value);
        }
        return Long.MIN_VALUE;
    }

    /**
     * Get the value parsed as Float.
     * <p>
     * Note: If value string is NULL/Empty will return Float.MIN_VALUE.
     *
     * @return - Value parsed as float.
     */
    @JsonIgnore
    public float getFloatValue() {
        if (!Strings.isNullOrEmpty(value)) {
            return Float.parseFloat(value);
        }
        return Float.MIN_VALUE;
    }

    /**
     * Get the value parsed as Double.
     * <p>
     * Note: If value string is NULL/Empty will return Double.MIN_VALUE.
     *
     * @return - Value parsed as double.
     */
    @JsonIgnore
    public double getDoubleValue() {
        if (!Strings.isNullOrEmpty(value)) {
            return Double.parseDouble(value);
        }
        return Double.MIN_VALUE;
    }

    /**
     * Get the value parsed as DateTime (Joda). This will use the
     * com.wookler.zconfig.common.GlobalConstants#DEFAULT_DATE_FORMAT for parsing the date.
     *
     * <p>
     * Note: If value string is NULL/Empty will return null.
     *
     * @return - Value parsed as double.
     */
    @JsonIgnore
    public DateTime getDateValue() {
        if (!Strings.isNullOrEmpty(value)) {
            DateTimeFormatter formatter =
                    DateTimeFormat.forPattern(GlobalConstants.DEFAULT_DATE_FORMAT);
            return DateTime.parse(value, formatter);
        }
        return null;
    }

    /**
     * Get the value parsed as DateTime (Joda). This will use the
     * specified date format for parsing the date.
     *
     * <p>
     * Note: If value string is NULL/Empty will return null.
     *
     * @return - Value parsed as double.
     */
    @JsonIgnore
    public DateTime getDateValue(String format) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(format));
        if (!Strings.isNullOrEmpty(value)) {
            DateTimeFormatter formatter =
                    DateTimeFormat.forPattern(format);
            return DateTime.parse(value, formatter);
        }
        return null;
    }

    /**
     * Get the value parsed as DateTime (Joda). This will use the
     * com.wookler.zconfig.common.GlobalConstants#DEFAULT_DATETIME_FORMAT for parsing the date.
     *
     * <p>
     * Note: If value string is NULL/Empty will return null.
     *
     * @return - Value parsed as double.
     */
    @JsonIgnore
    public DateTime getDateTimeValue() {
        if (!Strings.isNullOrEmpty(value)) {
            DateTimeFormatter formatter =
                    DateTimeFormat
                            .forPattern(GlobalConstants.DEFAULT_DATETIME_FORMAT);
            return DateTime.parse(value, formatter);
        }
        return null;
    }

    /**
     * Check if the current path element matches this node name, if so return this instance.
     *
     * @param path  - Tokenized Path array.
     * @param index - Current index in the path array to search for.
     * @return - This instance or NULL.
     */
    @Override
    public AbstractConfigNode find(String[] path, int index) {
        String key = path[index];
        if (getName().compareTo(key) == 0 && (index == path.length - 1)) {
            return this;
        }
        return null;
    }

    /**
     * Mark the configuration instance has been completely loaded.
     *
     * @throws ConfigurationException
     */
    @Override
    public void loaded() throws ConfigurationException {
        if (getState().hasError()) {
            throw new ConfigurationException(String.format(
                    "Cannot mark as loaded : Object state is in error. [state=%s]",
                    getState().getState().name()));
        }
        updateState(ENodeState.Synced);
    }

    /**
     * Update the node states recursively to the new state.
     *
     * @param state - New state.
     */
    @Override
    public void updateState(ENodeState state) {
        getState().setState(state);
    }
}
