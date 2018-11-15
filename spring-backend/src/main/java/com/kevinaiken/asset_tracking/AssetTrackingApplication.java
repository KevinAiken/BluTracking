package com.kevinaiken.asset_tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ImportResource(locations="spring-config.xml")
public class AssetTrackingApplication {
	public static void main(String[] args) {
		SpringApplication.run(AssetTrackingApplication.class, args);

		while(true){

		}
	}
}
