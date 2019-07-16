package com.acme;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
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

    public void start() throws Exception {
        Server server = createServer();

        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private WebAppContext createWebContext() {
        WebAppContext webapp = new WebAppContext();
        webapp.setDescriptor("../webapps/web.xml");
        webapp.setContextPath("/");
        webapp.setBaseResource(getResourceCollection());
        webapp.setParentLoaderPriority(true);

        return webapp;
    }

    private ResourceCollection getResourceCollection() {
        return new ResourceCollection(
                new String[]{
                        "./shared"
                }
        );
    }

}
