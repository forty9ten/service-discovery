package com.netflix.explorers.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.ConfigurationManager;
import com.netflix.karyon.spi.PropertyNames;

/**
 * Edge Server
 *
 * @author Chris Fregly (chris@fregly.com)
 */
public class Server extends BaseJettyServer {
    private static final Logger logger = LoggerFactory
            .getLogger(Server.class);

    public static void main(final String[] args) throws Exception {
        System.setProperty("archaius.deployment.applicationId", "frontend");
//        System.setProperty("archaius.deployment.environment", "dev");

        System.setProperty(PropertyNames.SERVER_BOOTSTRAP_BASE_PACKAGES_OVERRIDE, "com.netflix");

        String appId = ConfigurationManager.getDeploymentContext().getApplicationId();
        String env = ConfigurationManager.getDeploymentContext().getDeploymentEnvironment();

        // populate the eureka-specific properties
        System.setProperty("eureka.client.props", appId);
        if (env != null) {
            System.setProperty("eureka.environment", env);
        }

        Server edgeServer = new Server();
        edgeServer.start();
    }
}
