package com.advance.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;

/**
 * swagger配置需在打包时关闭
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {

    @Value("${component.id:}")
    private String componengId;

    @Value("${component.segment.id:}")
    private String segmengId;

    /**
     * 组件中文全称
     */
    @Value("${component.name:}")
    private String componentName;

    /**
     * 组件版本号
     */
    @Value("${component.version:}")
    private String componentVersion;

    /**
     * 组件SE名字，note名
     */
    @Value("${component.se.name:}")
    private String componentSEName;

    /**
     * 组件内部接口swagger(groupName不要写中文)
     *
     * @return
     */
    @Bean
    public Docket webRestApi() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("Unauthorized").build());
        responseMessageList.add(new ResponseMessageBuilder().code(403).message("Forbidden").build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("Not Found").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("Server Error").build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("1." + segmengId + "_web")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.advance"))

                //不匹配以/api/开头的接口路径
                .paths(PathSelectors.regex("^((?!/api/).)*$"))
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                ;
    }

    /**
     * 组件api接口swagger
     *
     * @return
     */
    @Bean
    public Docket openRestApi() {
        //统一在请求头中增加token
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("Token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header").required(true).allowableValues(new AllowableRangeValues("1", "128")).build();
        pars.add(tokenPar.build());

        List<ResponseMessage> responseMessageList = new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder().code(401).message("Unauthorized").build());
        responseMessageList.add(new ResponseMessageBuilder().code(403).message("Forbidden").build());
        responseMessageList.add(new ResponseMessageBuilder().code(404).message("Not Found").build());
        responseMessageList.add(new ResponseMessageBuilder().code(500).message("Server Error").build());

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("2." + segmengId + "_api")
//                .host("localhost") //依据接口规范host必须为localhost，而设置为localhost将导致swagger没法调试，因此这里需要导出json的时候再放开
                .apiInfo(apiInfo())
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .protocols(Sets.newHashSet("http", "https"))
                .select()
                .paths(Predicates.and(ant("/api/**")))
                .build()
                .globalOperationParameters(pars)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                ;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(componentName + segmengId + "服务接口")
                .description(componentName + "API接口描述")
                .version(componentVersion)
                .contact(new Contact(componentSEName, "", componentSEName + "@example.com.cn"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");

    }
}
