package com.ice.usercenter.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;


/**
 * Swagger2.0 用法
 *
 * @EnableWebMvc
 */

@Configuration
//@EnableSwagger2

public class SwaggerConfig {
//
//    @Autowired
//    Environment environment;
//
//
//    @Bean
//    public Docket docketUser() {
///**
// * 配置swagger全局参数
// *
// */
//        Parameter token = new ParameterBuilder().name("token")
//                .description("用户登录令牌")
//                .parameterType("header")  //query参数放在请求体    header请求头参数
//                .modelRef(new ModelRef("String"))
//                .required(true)//每个接口都带上token
//                .build();
//        ArrayList<Parameter> parameters = new ArrayList<Parameter>();
//        parameters.add(token);
//        return new Docket(DocumentationType.SWAGGER_2)
//                .globalOperationParameters(parameters);
//    }
//}


}




/**
 * Swagger信息一般保存在Docket类里
 * 配置docket,扫描具体参数
 */
//    @Bean
//    public Docket docketUser() {
//        //控制swagger在哪个环境可以展示
////        Profiles profiles = Profiles.of("dev", "test");  //配合配置文件使用
////        boolean isEnable = environment.acceptsProfiles(profiles); //检查是dev还是test这个环境下 如果是返回true  配合.enable()使用
//
//
//        return new Docket(DocumentationType.SWAGGER_2)
//
//
//                .groupName("用户")//组名  多个组需要多个bean
//                .select().paths(PathSelectors.ant("/user")).build();
//
////                .ignoredParameterTypes(String.class) //配置要忽略的请求参数 根据参数类型进行忽略
////                .enable(isEnable)  //是否能够访问swagger-ui    dev test环境下能够访问swagger-ui
////                .select()
////
////                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))  //可扫描所有方法，类，包
////                .paths(PathSelectors.ant("/user/**")) //扫描指定映射类
////                .build();
//
//
//    }

//    @Bean
//    public Docket docketHello() {
//
//
//        return new Docket(DocumentationType.SWAGGER_2)
//
//
//                .groupName("你好")//组名  多个组需要多个bean
//                .select().paths(PathSelectors.ant("/hello")).build();
//
//
//    }
//
////    private ApiInfo apiInfo(){
////        Contact con = new Contact("张三", "aaa.com", "123@qq.com");
////        return new ApiInfo("Swagger学习接口文档",
////                "文档信息",
////                "v1.0",
////                "http::localhost:8080",
////                con,
////                "",
////                "",
////                new ArrayList<>()
////        );
////    }
//
//}
