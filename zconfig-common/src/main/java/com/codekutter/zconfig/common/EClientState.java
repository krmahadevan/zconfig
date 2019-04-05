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
 * Date: 10/2/19 8:59 PM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.codekutter.zconfig.common;

/**
 * State enum to be used for exposing client handle states.
 */
public enum EClientState implements IStateEnum<EClientState> {
    /**
     * Client state is Unknown
     */
    Unknown,
    /**
     * Client has been initialized.
     */
    Initialized,
    /**
     * Client is available for operations.
     */
    Available,
    /**
     * Client has been disposed.
     */
    Disposed,
    /**
     * Client is in Error state.
     */
    Error;


    /**
     * Get the state that represents an error state.
     *
     * @return - Error state.
     */
    @Override
    public EClientState getErrorState() {
        return Error;
    }}
