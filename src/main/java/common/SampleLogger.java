package common;

import org.vertx.java.core.logging.Logger;
import org.vertx.java.platform.Container;

public class SampleLogger {
	Logger logger;

	public enum Level {
		INFO,
		DEBUG,
		WARN,
		ERROR
	}
	
	public SampleLogger(Container container){
		logger = container.logger();
	}
	
	public void info(String fmt, Object ... oo) { log(Level.INFO, fmt, oo); }
	public void debug(String fmt, Object ... oo) { log(Level.DEBUG, fmt, oo); }
	public void warn(String fmt, Object ... oo) { log(Level.WARN, fmt, oo); }
	public void error(String fmt, Object ... oo) { log(Level.ERROR, fmt, oo); }

	public void log(Level lvl, String fmt, Object ... oo) {
		String msg = String.format(fmt, oo);
		
		switch(lvl) {
		case INFO: logger.info(msg); break;
		case DEBUG: logger.debug(msg); break;
		case WARN: logger.warn(msg); break;
		case ERROR: logger.error(msg); break;
		}
		
	}
}
