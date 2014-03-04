/**
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.explorers.helloworld.resources;

import com.google.inject.Singleton;
import com.netflix.client.ClientException;
import com.netflix.client.ClientFactory;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.niws.client.http.RestClient;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.sun.jersey.api.view.Viewable;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Path("/")
@Singleton
public class HelloWorldAppResource {
    private Logger logger = LoggerFactory.getLogger(HelloWorldAppResource.class);

    private RestClient backendClient;

    public HelloWorldAppResource() throws ClientException {
        String backendClientName = "backend";
        IClientConfig config = DefaultClientConfigImpl.getClientConfigWithDefaultValues(backendClientName);
        config.setProperty(CommonClientConfigKey.NIWSServerListClassName, DiscoveryEnabledNIWSServerList.class.getName());
        config.setProperty(CommonClientConfigKey.DeploymentContextBasedVipAddresses, "backend");
        config.setProperty(CommonClientConfigKey.MaxAutoRetries, 0);
        config.setProperty(CommonClientConfigKey.MaxAutoRetriesNextServer, 2);

        ClientFactory.registerClientFromProperties(backendClientName, config);

        backendClient = (RestClient) ClientFactory.getNamedClient("backend");
    }

    @GET
    @Produces( MediaType.TEXT_HTML )
    public Viewable showIndex()
    {
        logger.info("home page");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("data", dataFromBackend());
        return new Viewable( "/helloworld/home.ftl", model );
    }

    private String dataFromBackend() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI("/")).build();
            HttpResponse response = backendClient.executeWithLoadBalancer(request);
            return IOUtils.toString(response.getInputStream(), "utf-8");
        } catch (URISyntaxException e) {
            String message = "Bad backend URL";
            logger.error(message, e);
            return message;
        } catch (ClientException e) {
            String message = "Error making backend request";
            logger.error(message, e);
            return message;
        } catch (IOException e) {
            String message = "Error parsing response";
            logger.error(message, e);
            return message;
        }
    }
}