package com.barath.app.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	
	

	@Bean
    public Docket api() {
		Parameter headerParam = new ParameterBuilder().name("tenant-id").defaultValue("microservices").parameterType("header")
				.modelRef(new ModelRef("string")).description("Tenant Identity").required(false).build();

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.globalOperationParameters(Arrays.asList(headerParam))
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("com.barath.app.order"))              
          .paths(PathSelectors.any())                          
          .build();

    }



	 private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Order API")
				.description("Order API reference for developers")
				.license("OPENSOURCE  API License")
				.version("1.0").build();
	}

}
