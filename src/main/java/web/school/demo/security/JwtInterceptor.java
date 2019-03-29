package web.school.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import web.school.demo.comment.dto.BaseResultFactory;
import web.school.demo.comment.dto.ErrorResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }else{
            String token = request.getHeader("Authorization");
            if (token == null){
                response.setContentType("text/json;charset=utf-8");
                String result_json=new ObjectMapper().writeValueAsString(BaseResultFactory.build(0,"用户未登录"));
                response.getWriter().append(result_json);
                return false;
            }
            Claims claims = JwtUtil.checkToken(token);
            request.setAttribute("username",claims.getSubject());
            return true;
        }
    }
}
