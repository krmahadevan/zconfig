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
 * Date: 2/1/19 8:36 AM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.model;

import com.google.common.base.Preconditions;
import com.wookler.zconfig.common.ConfigurationException;
import com.wookler.zconfig.common.FileReadResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Configuration resource node for the type BLOB.
 */
public class ConfigResourceBlob extends ConfigResourceFile {

    /**
     * Constructor with Configuration and Parent node.
     *
     * @param configuration - Configuration this node belong to.
     * @param parent        - Parent node.
     */
    public ConfigResourceBlob(
            Configuration configuration,
            AbstractConfigNode parent) {
        super(configuration, parent);
    }

    /**
     * Override set type as this type can only be BLOB.
     *
     * @param type - Resource type.
     */
    @Override
    public void setType(EResourceType type) {
        super.setType(EResourceType.BLOB);
    }

    /**
     * Read a BLOB segment from the resource handle.
     *
     * @param position - Start offset where to read from.
     * @param size     - Size of the BLOB segment to read.
     * @return - File Read response. (response.readBytes will be 0 if nothing was read)
     * @throws ConfigurationException
     */
    public FileReadResponse getBlob(long position, int size)
    throws ConfigurationException {
        Preconditions.checkArgument(position >= 0);
        Preconditions.checkArgument(size > 0);
        Preconditions.checkArgument(getResourceHandle() != null);

        try {
            try (
                    RandomAccessFile fis = new RandomAccessFile(
                            getResourceHandle().getAbsoluteFile(), "r")) {
                FileReadResponse response =
                        new FileReadResponse(getResourceHandle().getAbsolutePath(),
                                             position);
                if (position > 0) {
                    fis.seek(position);
                }
                byte[] array = new byte[size];
                size = fis.read(array);
                if (size > 0) {
                    response.setData(array);
                    response.setReadBytes(size);
                } else {
                    response.setReadBytes(0);
                }
                return response;
            }
        } catch (FileNotFoundException e) {
            throw new ConfigurationException(e);
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }
}
