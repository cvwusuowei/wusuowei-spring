package com.wusuowei.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration

public class Knife4jConfig {

 

    private OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API文档")
                        .description("wusuowei")
                        .contact(new Contact()
                                .name("wusuowei")
                                .email("1047365005@qq.com"))
                        .termsOfService("http://111.231.13.130:8080/api")
                        .version("1.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("项目API文档")
                        .url("/"));
    }

}
