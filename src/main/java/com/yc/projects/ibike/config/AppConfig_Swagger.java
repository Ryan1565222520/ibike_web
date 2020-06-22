package com.yc.projects.ibike.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@Configuration
@EnableSwagger2
public class AppConfig_Swagger {

	@Value(value="${swagger.enabled}")
	Boolean swaggerEnabled;
	
	@Bean
	public Docket createRestApi(){
		//System.out.println("swaggerEnabled"+swaggerEnabled);
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				//是否开启
				.enable(swaggerEnabled).select()
				//扫描的路径包，只要这些包中的类配有swagger注解，则启用这些注解
				.apis(RequestHandlerSelectors.basePackage("com.yc"))
				//指定路径处理PathSelectors.any()代表所有的路径
				.paths(PathSelectors.any()).build().pathMapping("/");
	}

	//swagger首页信息
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("YY出行操作接口")
				.description("springboot | swagger")
				// 作者信息
				.contact(  new Contact("Ryan", "http://www.hyycinfo.com", "1565222520@qq.com")  )
				.version("1.0.0")
				.build();
	}
}
