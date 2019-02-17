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
 * Date: 16/2/19 12:14 PM
 * Subho Ghosh (subho dot ghosh at outlook.com)
 *
 */

package com.wookler.zconfig.core.zookeeper;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.wookler.zconfig.common.LogUtils;
import com.wookler.zconfig.common.model.Version;
import com.wookler.zconfig.core.ZConfigCoreEnv;
import com.wookler.zconfig.core.ZConfigCoreInstance;
import com.wookler.zconfig.core.model.Application;
import com.wookler.zconfig.core.model.ApplicationGroup;
import com.wookler.zconfig.core.model.IZkNode;
import com.wookler.zconfig.core.model.ZkConfigNode;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryOneTime;

import javax.annotation.Nonnull;

/**
 * Helper class for ZooKeeper.
 */
public class ZkUtils {
    /**
     * Default path for creating configuration locks.
     */
    private static final String ZK_LOCK_PATH = "__LOCKS__";
    /**
     * System Root lock path.
     */
    private static final String ZK_ROOT_LOCK = "__ROOT_LOCK__";
    /**
     * Default retry sleep interval.
     */
    private static final int DEFAULT_RETRY_SLEEP = 1000;
    /**
     * ZooKeeper Root path for this server.
     */
    private static final String SERVER_ROOT_PATH = "/ZCONFIG-SERVER";

    /**
     * Get a new instance of the Curator Client. Method will start() the client.
     *
     * @return - Curator Framework Client.
     * @throws ZkException
     */
    public static final CuratorFramework getZkClient() throws ZkException {
        try {
            ZkConnectionConfig config =
                    ZConfigCoreEnv.get().getZkConnectionConfig();
            if (config == null) {
                throw new ZkException(
                        "ZooKeeper Connection configuration not set.");
            }
            RetryPolicy retryPolicy = null;
            if (!Strings.isNullOrEmpty(config.getRetryClass())) {
                LogUtils.debug(ZkUtils.class,
                               String.format("Using Retry implemenation : %s",
                                             config.getRetryClass()));
                Class<?> type = Class.forName(config.getRetryClass());
                if (type.equals(ExponentialBackoffRetry.class)) {
                    if (config.getSleepTime() <= 0 || config.getMaxRetries() < 0) {
                        throw new ZkException(String.format(
                                "Missing Retry Parameter(s) : [type=%s]",
                                config.getRetryClass()));
                    }
                    retryPolicy = new ExponentialBackoffRetry(config.getSleepTime(),
                                                              config.getMaxRetries());
                }
            }
            if (retryPolicy == null) {
                retryPolicy = new RetryOneTime(DEFAULT_RETRY_SLEEP);
            }
            CuratorFramework client = CuratorFrameworkFactory
                    .newClient(config.getConnectionString(), retryPolicy);
            client.start();
            return client;
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    /**
     * Get the ZooKeeper root path for this server.
     *
     * @return - ZooKeeper root path.
     * @throws ZkException
     */
    public static final String getServerRootPath() throws ZkException {
        try {
            ZkConnectionConfig config =
                    ZConfigCoreEnv.get().getZkConnectionConfig();
            if (config == null) {
                throw new ZkException(
                        "ZooKeeper Connection configuration not set.");
            }
            String rp = config.getRootPath();
            if (!Strings.isNullOrEmpty(rp)) {
                if (!rp.startsWith("/")) {
                    rp = String.format("/%s", rp);
                }
                ZConfigCoreInstance instance = ZConfigCoreEnv.get().getInstance();
                return String
                        .format("%s%s/%s", SERVER_ROOT_PATH, rp,
                                instance.getName());
            } else {
                ZConfigCoreInstance instance = ZConfigCoreEnv.get().getInstance();
                return String
                        .format("%s/%s", SERVER_ROOT_PATH, instance.getName());
            }
        } catch (Exception e) {
            throw new ZkException(e);
        }
    }

    /**
     * Create a new instance of a distributed ZooKeeper lock with the specified name.
     *
     * @param client - Curator Framework client handle.
     * @param name   - Lock name.
     * @return - Lock instance.
     * @throws ZkException
     */
    public static final InterProcessMutex getZkLock(
            @Nonnull CuratorFramework client,
            @Nonnull String name)
    throws ZkException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name));
        String path =
                String.format("%s/%s/%s", getServerRootPath(), ZK_LOCK_PATH, name);

        LogUtils.debug(ZkUtils.class,
                       String.format("Getting ZK Lock : [lock=%s]...", path));

        return new InterProcessMutex(client, path);
    }

    /**
     * Get the lock to the ZooKeeper root.
     *
     * @param client - Curator Framework client handle.
     * @return - Lock instance.
     * @throws ZkException
     */
    public static final InterProcessMutex getSystemLock(
            @Nonnull CuratorFramework client)
    throws ZkException {
        return getZkLock(client, ZK_ROOT_LOCK);
    }

    /**
     * Get the distributed lock instance for the specified Application Group.
     *
     * @param client - Curator Framework client handle.
     * @param group  - Application Group to Lock.
     * @return - Lock instance.
     * @throws ZkException
     */
    public static final InterProcessMutex getZkLock(
            @Nonnull CuratorFramework client,
            @Nonnull ApplicationGroup group)
    throws ZkException {
        String path = group.getAbsolutePath();
        return getZkLock(client, path);
    }

    /**
     * Get the distributed lock instance for the specified Application.
     *
     * @param client      - Curator Framework client handle.
     * @param application - Application to Lock.
     * @return - Lock instance.
     * @throws ZkException
     */
    public static final InterProcessMutex getZkLock(
            @Nonnull CuratorFramework client,
            @Nonnull Application application)
    throws ZkException {
        String path = application.getAbsolutePath();
        return getZkLock(client, path);
    }

    /**
     * Get the distributed lock instance for the specified Application.
     *
     * @param client        - Curator Framework client handle.
     * @param configuration - Configuration node to Lock.
     * @param version       - Configuration version to lock for.
     * @return - Lock instance.
     * @throws ZkException
     */
    public static final InterProcessMutex getZkLock(
            @Nonnull CuratorFramework client,
            @Nonnull ZkConfigNode configuration,
            @Nonnull Version version)
    throws ZkException {
        String path = String.format("%s/%d", configuration.getAbsolutePath(),
                                    version.getMajorVersion());
        return getZkLock(client, path);
    }

    /**
     * Get the ZooKeeper path for the specified node.
     *
     * @param node - Node to get path for.
     * @return - ZooKeeper path.
     * @throws ZkException
     */
    public static final String getZkPath(@Nonnull IZkNode node)
    throws ZkException {
        return String.format("%s/%s", getServerRootPath(), node.getAbsolutePath());
    }

    /**
     * Get the ZooKeeper path of the specified path String
     * relative to the configuration.
     *
     * @param configNode - Root configuration node.
     * @param path       - Path to append.
     * @return - Absolute Path
     * @throws ZkException
     */
    public static final String getZkPath(@Nonnull ZkConfigNode configNode,
                                         @Nonnull String path)
    throws ZkException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(path));
        String p = getZkPath(configNode);
        StringBuilder buff = new StringBuilder(p);
        String[] parts = path.split("\\.");
        if (parts.length > 0) {
            for (String part : parts) {
                buff.append("/").append(part);
            }
        }
        return buff.toString();
    }
}
