package com.example.CrowdFunding.CrowdFundingBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// import io.swagger.v3.oas.annotations.OpenAPIDefinition;
// import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
// @OpenAPIDefinition(info = @Info(title = "CrowdFunding API", version = "1.0.1", description = "CrowdFunding API Documentation with Spring"))
public class CrowdFundingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrowdFundingBackendApplication.class, args);
	}

}
