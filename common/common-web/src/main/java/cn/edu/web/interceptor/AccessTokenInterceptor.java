package cn.edu.web.interceptor;

import cn.edu.core.base.GlobalException;
import cn.edu.core.base.Status;
import cn.edu.core.utils.Constants;
import cn.edu.redis.component.RedisService;
import cn.edu.web.context.AppContext;
import lombok.AllArgsConstructor;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class AccessTokenInterceptor implements HandlerInterceptor {

    private final RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(accessToken)) {
            System.out.println(request.getRequestURI());
            throw new GlobalException(Status.TOKEN_NOT_FOUND);
        }
        String appId = (String) redisService.get(Constants.TOKEN_NAME + accessToken);
        if (StringUtils.isEmpty(appId)) {
            throw new GlobalException(Status.TOKEN_INVALID);
        }
        AppContext.set(appId); // AppId保存到上下文
        return true;
    }
}
