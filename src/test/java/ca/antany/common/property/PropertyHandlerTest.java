package ca.antany.common.property;

import org.junit.Test;

import ca.antany.common.property.enums.PropertyType;

public class PropertyHandlerTest {

	@Test
	public void propertyHanderTest() {
		PropertyHandler ph = PropertyHandler.getInstance();
		ph.addPropertyFile("PropertyHandlerTest.properties", PropertyType.CLASSPATH);
		assert("success".equals(ph.get("test1")));
	}
}
