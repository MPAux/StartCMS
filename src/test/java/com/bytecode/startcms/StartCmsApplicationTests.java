package com.bytecode.startcms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StartCmsApplicationTests {
	Log log = LogFactory.getLog(this.getClass());
	
	@Test
	void contextLoads() {
		log.info("Inside contextLoads");
	}

}
