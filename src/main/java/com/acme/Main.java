package com.acme;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;

public class Main {


    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.start();
        } catch (Exception e) {
            System.out.println("Exception while starting...!");
            e.printStackTrace();
        }
    }

    public Server start() throws Exception {
        Server server = createServer();

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return server;
    }

    private Server createServer() {
        Server server = new Server();
        server.setStopAtShutdown(true);
        setupHttp(server);
        setupWebApp(server);
        return server;
    }

    private void setupHttp(Server server) {
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(8080);
        server.setConnectors(new Connector[]{serverConnector});
    }

    private void setupWebApp(Server server) {
        HandlerCollection handlers = new HandlerCollection();

        handlers.setHandlers(new Handler[]{
                createWebContext()
        });

        server.setHandler(handlers);
    }

    private ContextHandler createWebContext() {
        ServletContextHandler context = new ServletContextHandler();
//        context.setDescriptor("../webapps/web.xml");
        context.setContextPath("/");
        context.setBaseResource(getResourceCollection());
//        context.setParentLoaderPriority(true);

        ServletHolder defHolder = new ServletHolder("default", DefaultServlet.class);
        context.addServlet(defHolder, "/");

        return context;
    }

    private ResourceCollection getResourceCollection() {
        return new ResourceCollection(
                new String[]{
                        "./shared"
                }
        );
    }

}
