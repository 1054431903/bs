package com.agrithings.yz;

import org.rapidoid.config.Conf;
import org.rapidoid.net.Server;

/**
 * @author avidmouse
 * @version 1.0, 25/02/2018.
 */
public class Main {

    public static void main(String[] args) {

        Server server = new YzServer().listen(
                Conf.ON.entry("port").or(8101)
        );

        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
    }

}
