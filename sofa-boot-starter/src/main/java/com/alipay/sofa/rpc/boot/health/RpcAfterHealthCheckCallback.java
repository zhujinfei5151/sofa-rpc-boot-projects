/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.rpc.boot.health;

import com.alipay.sofa.healthcheck.startup.SofaBootMiddlewareAfterReadinessCheckCallback;
import com.alipay.sofa.rpc.boot.context.event.SofaBootRpcStartAfterEvent;
import com.alipay.sofa.rpc.boot.context.event.SofaBootRpcStartEvent;
import com.alipay.sofa.rpc.log.Logger;
import com.alipay.sofa.rpc.log.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * SOFABoot RPC 健康检查回调.会启动服务器并发布服务
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
@Component
public class RpcAfterHealthCheckCallback implements SofaBootMiddlewareAfterReadinessCheckCallback {

    private static final Logger LOGGER = LoggerFactory
                                           .getLogger(RpcAfterHealthCheckCallback.class);

    /**
     * 健康检查
     * @param applicationContext Spring 上下文
     * @return 健康检查结果
     */
    @Override
    public Health onHealthy(ApplicationContext applicationContext) {

        Health.Builder builder = new Health.Builder();
        try {
            //rpc 开始启动事件监听器
            applicationContext.publishEvent(new SofaBootRpcStartEvent(applicationContext));

            //rpc 启动完毕事件监听器
            applicationContext.publishEvent(new SofaBootRpcStartAfterEvent(applicationContext));

            builder.status(Status.UP);

            return builder.build();

        } catch (Exception e) {

            LOGGER.error("Health check callback error", e);
            builder.status(Status.DOWN).withDetail("Exception", e.getMessage());

            return builder.build();
        }
    }
}