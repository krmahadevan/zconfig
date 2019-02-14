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
 * Date: 13/2/19 5:50 PM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.utils;

import com.wookler.zconfig.common.Context;

/**
 * Interface to be implemented for defining ID Generators.
 */
public interface IUniqueIDGenerator {
    /**
     * Generate a Unique String ID.
     *
     * @param context - Additional parameter context, if required.
     * @return - Generated String ID.
     */
    String generateStringId(Context context);

    /**
     * Generate a Unique Integer ID.
     *
     * @param context - Additional parameter context, if required.
     * @return - Generated Integer ID.
     */
    int generateIntId(Context context);

    /**
     * Generate a Unique Long ID.
     *
     * @param context - Additional parameter context, if required.
     * @return - Generated Long ID.
     */
    long generateLongId(Context context);
}
