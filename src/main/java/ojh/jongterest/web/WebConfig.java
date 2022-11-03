package ojh.jongterest.web;

import ojh.jongterest.web.argumentResolver.LoginUserArgumentResolver;
import ojh.jongterest.web.interceptor.LogInterceptor;
import ojh.jongterest.web.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableJpaAuditing
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.profiles.active}")
    private String key;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginUserArgumentResolver());
    }

    // 동적 이미지 로딩
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if (key.equals("prod")) {
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:///home/images/");
        } else {

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/Users/ojh/jongterest/src/main/resources/static/images/");
        }
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
            .excludePathPatterns("/", "/user/create", "/login", "/logout", "/articles/list",
                    "/css/**","/fonts/**","/images/**", "/js/**", "/template/**", "/*.ico", "/error", "/error-page");
    }
}
