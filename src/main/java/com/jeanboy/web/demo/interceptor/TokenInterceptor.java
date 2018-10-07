package com.jeanboy.web.demo.interceptor;

import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.constants.ErrorCode;
import com.jeanboy.web.demo.exceptions.info.ErrorInfo;
import com.jeanboy.web.demo.exceptions.info.ExceptionInfo;
import com.jeanboy.web.demo.utils.StringUtil;
import com.jeanboy.web.demo.utils.TokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断 Token 是否有效
        String token = request.getHeader("token");
        if (StringUtil.isEmpty(token) || TokenUtil.isInvalid(token)) {
            response.setStatus(401);
            response.setHeader("Content-Type", "text/json;charset=UTF-8");
            ExceptionInfo exceptionInfo = new ExceptionInfo(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            List<ErrorInfo> errors = new ArrayList<>();
            errors.add(new ErrorInfo(ErrorCode.TOKEN_INVALID.value(), ErrorCode.TOKEN_INVALID.getReasonPhrase()));
            exceptionInfo.setErrors(errors);
            String content = JSON.toJSONString(exceptionInfo);
            response.getWriter().write(content);
            response.getWriter().close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

}
