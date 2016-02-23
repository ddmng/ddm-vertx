package it.danielediminica;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class Runner extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    vertx.createHttpServer().requestHandler(req -> req.response().end("Hello World!"))
    .listen(
        Integer.getInteger("http.port"), System.getProperty("http.address"));
  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Runner());
  }

}
