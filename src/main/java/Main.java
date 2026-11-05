import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);
    public static final String BASE_URI = "http://localhost:8080/dsaApp/";

    public static void main(String[] args) throws IOException {
        ResourceConfig config = new ResourceConfig(ServicioDrones.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
        logger.info("Servidor arrancado en " + BASE_URI + ". Pulsa Enter para pararlo.");
        System.in.read();
        server.stop();
    }
}
