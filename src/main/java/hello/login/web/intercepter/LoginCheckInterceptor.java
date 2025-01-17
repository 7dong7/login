package hello.login.web.intercepter;

import hello.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    // 로그인 기능은 preHandle 만 있으면 된다
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉터 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if( session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자의 요청");
            // 로그인 페이지로 redirect & 로그인시 현재 페이지 다시 올수 있게 requestURI 같이 보내기
            response.sendRedirect("/login?redirectURL="+requestURI);
            return false; // false 를 붙여야 인터셉터가 더 진행되지 않는다
                          // true 를 붙이면 인터셉터가 더 진행된다
        }

        return true;
    }
}
