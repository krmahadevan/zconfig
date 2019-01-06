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
 * Date: 2/1/19 8:16 AM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.model;

import com.google.common.base.Preconditions;
import com.wookler.zconfig.common.ConfigurationException;

import java.io.File;

/**
 * Configuration resource node for the type file.
 *
 */
public class ConfigResourceFile extends ConfigResourceNode {
    /**
     * File handle to the file pointed to by the URI.
     */
    private File resourceHandle;

    /**
     * Get the file handle to the resource.
     *
     * @return - File handle.
     */
    public File getResourceHandle() {
        return resourceHandle;
    }

    /**
     * Set the file handle for the resource.
     *
     * @param resourceHandle - File handle.
     */
    public void setResourceHandle(File resourceHandle) {
        Preconditions.checkArgument(resourceHandle != null);
        Preconditions.checkArgument(resourceHandle.exists());
        Preconditions.checkArgument(resourceHandle.canRead());

        this.resourceHandle = resourceHandle;
    }

    /**
     * Override the set type method, as this resource can only be of type FILE.
     *
     * @param type - Resource type.
     */
    @Override
    public void setType(EResourceType type) {
        super.setType(EResourceType.FILE);
    }

}
