package com.linkedin.linkedinclone.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@Configuration
public class OpenApiConfig {

    public static final String SECURITY_SCHEME_KEY = "apikey";

    @Bean
    @Primary
    public OpenAPI documentation(){
        return new OpenAPI()
                .info(new Info()
                        .title("Skill Bridge Social Media Platform")
                        .version("1.0")
                        .license(new License().name("SkillBridge"))
                        .contact(new Contact().name("SkillBridge Team").email("skillbridge@skillbridge.tech")))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_KEY, new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .name("Authorization")
                                .in(SecurityScheme.In.HEADER)))
                .security(Collections.singletonList(new SecurityRequirement().addList(SECURITY_SCHEME_KEY)));
    }
}
