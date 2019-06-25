package ca.antany.common.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IOUtils {

	private static Logger logger = Logger.getLogger(IOUtils.class.getName());
	
	public static void close(Closeable object) {
		try {
			if(object!=null) {
				object.close();
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE,e,null);
		}
	}
}
