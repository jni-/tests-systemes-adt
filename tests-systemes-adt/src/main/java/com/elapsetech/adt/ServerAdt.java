package com.elapsetech.adt;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.elapsetech.adt.contextes.ContexteAdt;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class ServerAdt {

    private final int port;

    public ServerAdt(int port, ContexteAdt contexte) {
        this.port = port;
        contexte.creer();
    }

    public Server demarrer() throws Exception {
        Server server = new Server(port);

        configurerJersey(server);

        server.start();
        return server;
    }

    private void configurerJersey(Server server) {
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/adt");

        ServletHolder jerseyServletHolder = new ServletHolder(ServletContainer.class);
        jerseyServletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass",
                "com.sun.jersey.api.core.PackagesResourceConfig");
        jerseyServletHolder.setInitParameter("com.sun.jersey.config.property.packages", "com.elapsetech.adt.rest");
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
    }

}
