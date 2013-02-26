package com.elapsetech.adt;

public class Bootstrap {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        new ServerAdt(PORT).demarrer().join();
    }

}
