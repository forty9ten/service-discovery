package com.netflix.explorers.helloworld;

import java.io.Closeable;

import org.apache.jasper.servlet.JspServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.karyon.server.KaryonServer;

/**
 * Base Jetty Server
 *
 * @author Chris Fregly (chris@fregly.com)
 */
public class BaseJettyServer implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(BaseJettyServer.class);

    private final Server jettyServer;
    private final KaryonServer karyonServer;

    protected final Injector injector;

    public BaseJettyServer() {
        System.setProperty(DynamicPropertyFactory.ENABLE_JMX, "true");

        this.karyonServer = new KaryonServer();
        this.injector     = karyonServer.initialize();
        this.jettyServer  = new Server();
    }

    public void start() {

        final int port = 8080;

        final Context context = new Context(jettyServer, "/", Context.SESSIONS);
        context.setResourceBase("src/main/webapp");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());
        context.addServlet(JspServlet.class, "*.jsp");

        final Server server = new Server(port);
        server.setHandler(context);

        try {
            karyonServer.start();
            server.start();
        } catch (Exception exc) {
            throw new RuntimeException("Cannot start karyon server ...", exc);
        }
    }

    public void close() {
        try {
            jettyServer.stop();
            karyonServer.close();
        } catch (Exception exc) {
            logger.error("Error stopping jetty ...", exc);
        }
    }
}
