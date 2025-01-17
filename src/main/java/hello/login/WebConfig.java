package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.intercepter.LogInterceptor;
import hello.login.web.intercepter.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**") // 이 경로에 있는 요청에 인터셉터를 적용해
                .excludePathPatterns("/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"); // 그렇지만 이 경로에 있는 요청 하지마 라는 뜻
            // exclude 에 포함되어 있는 경로는 인터셉터 호출을 아예 하지 않는다
    }

    // 로그 요청 필터
//    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 내가 만든 필터 로직
        filterRegistrationBean.setOrder(1); // 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 필터 적용 url

        return filterRegistrationBean;
    }

    // 로그인 체크 필터
//    @Bean
    public FilterRegistrationBean<LoginCheckFilter> loginCheckFilter() {
        FilterRegistrationBean<LoginCheckFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 내가 만든 필터 로직
        filterRegistrationBean.setOrder(2); // 필터 순서
        filterRegistrationBean.addUrlPatterns("/*"); // 필터 적용 url  // 화이트 리스트를 안에서 설정해주었기때문에 괜찮음

        return filterRegistrationBean;
    }
}
