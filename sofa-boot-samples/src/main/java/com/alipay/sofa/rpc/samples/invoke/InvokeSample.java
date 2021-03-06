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
package com.alipay.sofa.rpc.samples.invoke;

import com.alipay.sofa.rpc.api.future.SofaResponseFuture;
import org.springframework.context.ApplicationContext;

/**
 *
 * 同步，future，回调调用方式
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class InvokeSample {

    public void start(ApplicationContext applicationContext) throws InterruptedException {

        HelloSyncService helloSyncServiceReference = (HelloSyncService) applicationContext
            .getBean("helloSyncServiceReference");
        HelloFutureService helloFutureServiceReference = (HelloFutureService) applicationContext
            .getBean("helloFutureServiceReference");
        HelloCallbackService helloCallbackServiceReference = (HelloCallbackService) applicationContext
            .getBean("helloCallbackServiceReference");

        System.out.println(helloSyncServiceReference.saySync("sync"));

        helloFutureServiceReference.sayFuture("future");
        System.out.println(SofaResponseFuture.getResponse(1000, true));

        helloCallbackServiceReference.sayCallback("callback");

        Thread.sleep(3000);
    }

}