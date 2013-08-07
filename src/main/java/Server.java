import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import common.SampleLogger;

public class Server extends Verticle {
	private static final int 	LISTEN_PORT = 8080;
	private SampleLogger 		log;

	public void start() {
		log = new SampleLogger(getContainer());

		Listener listener = new Listener();

		vertx.createHttpServer().requestHandler(listener).listen(LISTEN_PORT);

		log.info("start Server... \r\nlisten : %d", LISTEN_PORT);
	}

	private class Listener implements Handler<HttpServerRequest> {
		@Override
		public void handle(HttpServerRequest request) {
			String path = request.path();
			String file = path.equals("/") ? "index.html" : path;

			log.info("file : %s", file);

			request.response().sendFile("webroot/" + file);
		}
	}
}
