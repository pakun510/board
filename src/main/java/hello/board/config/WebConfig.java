package hello.board.config;

import hello.board.config.interceptor.LoggedMemberCheckInterceptor;
import hello.board.config.interceptor.LoginCheckInterceptor;
import hello.board.config.interceptor.ValidatorBoardAuthorityInterceptor;
import hello.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final BoardRepository boardRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/login", "/boards", "/members/join",
                        "/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoggedMemberCheckInterceptor())
                .order(2)
                .addPathPatterns("/login", "/members/join");

        registry.addInterceptor(new ValidatorBoardAuthorityInterceptor(boardRepository))
                .order(3)
                .addPathPatterns("/boards/*/edit", "/boards/*/delete");
    }
}
