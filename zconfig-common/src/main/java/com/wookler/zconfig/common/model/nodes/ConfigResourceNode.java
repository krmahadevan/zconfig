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
 * Date: 24/2/19 12:36 PM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.model.nodes;

import com.google.common.base.Preconditions;
import com.wookler.zconfig.common.ConfigurationException;
import com.wookler.zconfig.common.model.Configuration;
import com.wookler.zconfig.common.model.ENodeState;
import com.wookler.zconfig.common.model.EResourceType;
import com.wookler.zconfig.common.model.nodes.AbstractConfigNode;
import com.wookler.zconfig.common.model.nodes.ConfigElementNode;

import java.net.URI;

/**
 * Node denotes a resource URI. Resources specified will be downloaded (if required) and made accessible
 * via the configuration handle.
 * <p>
 * XML:
 * <resource name="name" URI="uri" type="type" />
 * <p>
 * JSON:
 * resource : {
 * name : "name",
 * URI : "uri",
 * type : "type",
 * ...
 * }
 */
public abstract class ConfigResourceNode extends ConfigElementNode {
    /**
     * Resource type of this node.
     */
    private EResourceType type;
    /**
     * Resource location of the resource pointed to by this node.
     */
    private URI location;

    /**
     * Default constructor - Initialize the state object.
     */
    public ConfigResourceNode() {
    }

    /**
     * Constructor with Configuration and Parent node.
     *
     * @param configuration - Configuration this node belong to.
     * @param parent        - Parent node.
     */
    public ConfigResourceNode(
            Configuration configuration,
            AbstractConfigNode parent) {
        super(configuration, parent);
    }

    /**
     * Get the resource type of this node.
     *
     * @return - Resource type.
     */
    public EResourceType getType() {
        return type;
    }

    /**
     * Set the resource type for this node.
     *
     * @param type - Resource type.
     */
    public void setType(EResourceType type) {
        Preconditions.checkArgument(type != null);
        this.type = type;
    }

    /**
     * Get the URI to the location of the resource.
     *
     * @return - Location URI.
     */
    public URI getLocation() {
        return location;
    }

    /**
     * Set the URI to the location of the resource.
     *
     * @param location - Location URI.
     */
    public void setLocation(URI location) {
        Preconditions.checkArgument(location != null);
        this.location = location;
    }

    /**
     * Check if this node if the terminal value specified in the path.
     *
     * @param path  - Tokenized Path array.
     * @param index - Current index in the path array to search for.
     * @return - Node found or NULL.
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
     * Update the state of this node.
     *
     * @param state - New state.
     */
    @Override
    public void updateState(ENodeState state) {
        getState().setState(state);
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
}