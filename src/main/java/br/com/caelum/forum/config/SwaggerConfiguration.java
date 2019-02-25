package br.com.caelum.forum.config;

import br.com.caelum.forum.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.caelum.forum"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(apiInfo())
                .ignoredParameterTypes(User.class)
                .globalOperationParameters(
                        Arrays.asList(new ParameterBuilder().name("Authorization")
                        .description("Header para facilitar o envio do Authorization Bearer Token")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build())
                );
    }

    private ApiInfo apiInfo() {
        Contact contato = new Contact("Caelum","http://www.ufjf.br","contato@ufjf.edu.br");
        return new ApiInfoBuilder()
                .title("Forum API")
                .description("Descrição")
                .version("1.0")
                .contact(contato)
                .build();
    }
}
