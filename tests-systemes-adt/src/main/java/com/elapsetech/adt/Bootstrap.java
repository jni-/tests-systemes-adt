package com.elapsetech.adt;

import com.elapsetech.adt.contextes.ContexteDemo;

public class Bootstrap {

    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        new ServerAdt(PORT, new ContexteDemo()).demarrer().join();
    }

}
