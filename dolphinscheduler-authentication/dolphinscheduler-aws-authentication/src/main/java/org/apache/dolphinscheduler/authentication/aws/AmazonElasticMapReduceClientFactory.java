/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.authentication.aws;

import java.util.Map;

import lombok.experimental.UtilityClass;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClientBuilder;

@UtilityClass
public class AmazonElasticMapReduceClientFactory {

    public AmazonElasticMapReduce createAmazonElasticMapReduceClient(Map<String, String> awsProperties) {
        AWSCredentialsProvider awsCredentialsProvider = AWSCredentialsProviderFactor.credentialsProvider(awsProperties);
        Regions regions = Regions.fromName(awsProperties.get(AwsConfigurationKeys.AWS_REGION));
        String endpoint = awsProperties.get(AwsConfigurationKeys.AWS_ENDPOINT);

        if (endpoint != null && !endpoint.isEmpty()) {
            return AmazonElasticMapReduceClientBuilder
                    .standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, regions.getName()))
                    .withCredentials(awsCredentialsProvider)
                    .build();
        } else {
            return AmazonElasticMapReduceClientBuilder
                    .standard()
                    .withCredentials(awsCredentialsProvider)
                    .withRegion(regions)
                    .build();
        }
    }

}
