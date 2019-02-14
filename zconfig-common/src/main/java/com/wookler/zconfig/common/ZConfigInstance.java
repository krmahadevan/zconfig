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
 * Date: 13/2/19 10:55 AM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common;

import com.wookler.zconfig.common.model.annotations.ConfigValue;
import org.joda.time.DateTime;

/**
 * Abstract base class for defining a client/server instance.
 */
public class ZConfigInstance {
    /**
     * Unique client instance ID.
     */
    private String id;
    /**
     * Client name (or service name).
     */
    @ConfigValue(name = "name", required = true)
    private String name;
    /**
     * Client hostname.
     */
    private String hostname;
    /**
     * Client IP Address String.
     */
    private String ip;
    /**
     * Client instance start timestamp.
     */
    private DateTime startTime;

    /**
     * Get the unique client ID.
     *
     * @return - Unique Client ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the unique client ID.
     *
     * @param id - Unique Client ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the client name.
     *
     * @return - Client name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the client name.
     *
     * @param name - Client name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the client hostname.
     *
     * @return - Client hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Set the client hostname.
     *
     * @param hostname - Client hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Get the Client IP Address (String)
     *
     * @return - IP Address String.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Set the Client IP Address (String)
     *
     * @param ip - IP Address String.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Get the client instance start timestamp.
     *
     * @return - Instance Start timestamp.
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * Get the client instance start timestamp.
     *
     * @param startTime - Instance Start timestamp.
     */
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }
}
