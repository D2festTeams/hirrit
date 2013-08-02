import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

public class Server extends Verticle {
		public void start() {
				vertx.createHttpServer().requestHandler(new Handler() {
						public void handle(Object obj) {
							HttpServerRequest req = (HttpServerRequest) obj;
							String file = req.path().equals("/") ? "index.html" : req.path();
							req.response().sendFile("webroot/" + file);
						}
				}).listen(8080);
		}
}
