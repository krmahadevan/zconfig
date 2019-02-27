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
 * Date: 26/2/19 10:41 AM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.common.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be used to define configuration mapping for auto-wired
 * configuration elements.
 * Created by subho on 16/11/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD })
public @interface ConfigAttribute {
    /**
     * Get the configuration parameter name. By default it maps to defined
     * parameters. For mapping to attributes prefix the name with @.
     *
     * @return - Mapping element name.
     */
    String name() default "";

    /**
     * Is this parameter required, will raise an exception if element is not found
     * in the configuration. By default all elements are assumed not to be required
     * unless specified.
     *
     * @return - Is mandatory?
     */
    boolean required() default false;
}
