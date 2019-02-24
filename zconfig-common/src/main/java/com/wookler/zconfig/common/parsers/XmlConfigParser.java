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
 * Date: 2/2/19 11:15 AM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.parsers;

import com.wookler.zconfig.common.ConfigurationException;
import com.wookler.zconfig.common.model.ConfigurationSettings;
import com.wookler.zconfig.common.model.Version;
import com.wookler.zconfig.common.readers.AbstractConfigReader;

/**
 * Configuration Parser implementation that reads the configuration from a XML file.
 */
public class XmlConfigParser extends AbstractConfigParser {
    /**
     * Parse and load the configuration instance using the specified properties.
     *
     * @param name     - Configuration name being loaded.
     * @param reader   - Configuration reader handle to read input from.
     * @param settings - Configuration Settings to use for parsing.
     * @param version  - Configuration version to load.
     * @throws ConfigurationException
     */
    @Override
    public void parse(String name, AbstractConfigReader reader,
                      ConfigurationSettings settings, Version version)
    throws ConfigurationException {

    }
}
