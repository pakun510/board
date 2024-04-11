package hello.board.config;

import hello.board.config.interceptor.LoggedMemberCheckInterceptor;
import hello.board.config.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/boards/**", "/members/**",
                        "/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoggedMemberCheckInterceptor())
                .order(2)
                .addPathPatterns("/login", "/members/join");
    }
}
