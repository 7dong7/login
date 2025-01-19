package hello.login.web.argumentresolver;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    
    // 어노테이션 적용 타이밍
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행"); // 이 supportsParameter 의 경우 내부의 캐시가 있어서 여러번 실행되지 않음

        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // parameter 에 어노테이션으로 사용되었나?
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType()); // Member 클래스 앞에 사용되었나?

        return hasLoginAnnotation && hasMemberType; // 둘 다 만족하면 true
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolverArgument 실행");

        HttpServletRequest request  = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        // session 의 값이 없는 경우
        if(session == null) { // 로그인 하지 않음
            return null; // 그럼 Member = null 반환
        }

        // 로그인을 한 경우  session 에 저장된 Member = loginMember 정보 반환
        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
