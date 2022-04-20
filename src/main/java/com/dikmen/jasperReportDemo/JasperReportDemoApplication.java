package com.dikmen.jasperReportDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dikmen.*")
public class JasperReportDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JasperReportDemoApplication.class, args);
	}

}
 