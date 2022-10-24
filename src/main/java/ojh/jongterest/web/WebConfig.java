package ojh.jongterest.web;

import ojh.jongterest.web.argumentResolver.LoginUserArgumentResolver;
import ojh.jongterest.web.interceptor.LogInterceptor;
import ojh.jongterest.web.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    // 동적 이미지 로딩
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/Users/ojh/jongterest/src/main/resources/static/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(new LogInterceptor())
//            .order(1)
//            .addPathPatterns("/**")
//            .excludePathPatterns("/css/**", "/*.ico", "/error");

    registry.addInterceptor(new LoginCheckInterceptor())
            .order(1)
            .addPathPatterns("/**")
            .excludePathPatterns("/", "/user/create", "/login", "/logout",
                    "/css/**", "/*.ico", "/error");
    }
}