package com.fonyou;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * WAR Config (Servlet API 3.0).
 * @author Johan Ballesteros.
 * @version 1.0.0.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CoreApplication.class);
	}

}
