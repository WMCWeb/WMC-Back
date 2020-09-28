package com.wmc.WMCWeb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
// 2020.09.20 이경훈: Prperties 파일을 통해 DB연결 정보 관리하기 위해 PropertySource 등록
@PropertySource("classpath:/application.properties")
public class WmcWebApplication {

	// 2020.09.28 이경훈: Log4j2 logger 사용법
	/*
	private static final Logger logger = LogManager.getLogger(WmcWebApplication.class);

	*/
	public static void main(String[] args) {
		SpringApplication.run(WmcWebApplication.class, args);
		System.out.println("혜린, 경훈, 수빈");
	}

}
