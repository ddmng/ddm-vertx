package it.danielediminica;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.function.Consumer;

public class Runner extends AbstractVerticle {
    static Router router;
    static Vertx vertx;
    private final static Logger logger = io.vertx.core.logging.LoggerFactory.getLogger(Runner.class.getName());

    @Override
    public void start() throws Exception {

        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(Integer.getInteger("http.port"), System.getProperty("http.address"));


    }

    public static void main(String[] args) {

        Consumer<Vertx> runner = vertx -> {
            router = Router.router(vertx);
            vertx.deployVerticle(new Runner());

            // /now special handler
            router.route("/now").handler(h -> {
                h.response().sendFile("webroot/now.html");
            });

            // static handler
            logger.debug("Adding static handler");
            router.route("/*").handler(StaticHandler.create().setCachingEnabled(false));

            // 404 errors
            router.route("/*").failureHandler(h -> {
                h.response().sendFile("webroot/404.html");
            });

        };

        vertx = Vertx.vertx();
        runner.accept(vertx);

        logger.info("Server started");

    }

}
