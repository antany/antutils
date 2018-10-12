package ca.antany.common.property;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import ca.antany.common.property.enums.PropertyType;

/**
 * 
 * Simplify the way to add property file from classpath or filesystem
 * and maintains one PropertyHandler object across project
 * <p>
 * <code>
 * 
 * PropertyHandler ph = PropertyHandler.getInstance(); <br>
 * ph.addPropertyFile("app.properties", PropertyType.CLASSPATH); //loads app.properties from classpath<br>
 * ph.get("key");<br>
 * 
 * </code>
 * </p>
 * @author antany
 * @since 0.0.1
 */
public class PropertyHandler extends Properties {

	
	private static final long serialVersionUID = 1L;
	
	
	private static Object objectLock = new Object();

	Logger logger = Logger.getLogger(PropertyHandler.class.getName());

	private static PropertyHandler pl = null;

	private PropertyHandler() {
		super();
	}

	/**
	 * 
	 * Add the property file to PropertyHandler from CLASSPATH or from local filesystem.
	 * if ANY specified then loads from CLASSPATH and fileSystem (if exists)
	 * if ANY specified and same property exists in both CLASSPATH file and filesystem, 
	 * then value from fileSystem will be considered
	 * 
	 * @param resourcePath
	 * @param propertyType
	 */
	public void addPropertyFile(String resourcePath, PropertyType propertyType) {
		switch (propertyType) {
		case CLASSPATH:
			genenratePropertyFromClassPath(resourcePath);
			break;
		case FILE:
			genenratePropertyFromLocalFile(resourcePath);
			break;
		default:
			genenratePropertyFromClassPath(resourcePath);
			genenratePropertyFromLocalFile(resourcePath);
		}
	}

	
	/**
	 * 
	 * Returns single instance of PropertyHandler, within in an application
	 * 
	 * @return PropertyLoader
	 */
	public static PropertyHandler getInstance() {
		synchronized (objectLock) {
			if (pl == null) {
				pl = new PropertyHandler();
			}
			return pl;
		}
	}

	private void genenratePropertyFromClassPath(String resourcePath) {
		logger.info("Loading property from classpath " + resourcePath);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try (InputStream resourceStream = loader.getResourceAsStream(resourcePath)) {
			this.load(resourceStream);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error Processing classpath Property", e);
		}
	}

	private void genenratePropertyFromLocalFile(String resourcePath) {
		logger.info("Loading property from file " + resourcePath);
		try {
			this.load(new FileInputStream(resourcePath));
		}catch (FileNotFoundException foe) {
			logger.log(Level.INFO, "Local file not found {0}", resourcePath);
		}catch (Exception e) {
			logger.log(Level.SEVERE, "Error Processing Local File Property", e);
		}
	}
}