package com.jeanboy.web.demo.controller;


import com.alibaba.fastjson.JSON;
import com.jeanboy.web.demo.base.BaseController;
import com.jeanboy.web.demo.constants.HttpStatus;
import com.jeanboy.web.demo.exceptions.ServerException;
import com.jeanboy.web.demo.exceptions.info.ExceptionInfo;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ServerExceptionController extends BaseController {

    /**
     * 格式化响应异常
     *
     * @param response
     * @param exception
     * @return
     */
    private String format(HttpServletResponse response, ExceptionInfo exception) {
        response.setStatus(exception.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        return JSON.toJSONString(exception);
    }

    private ExceptionInfo getException(int status, String message) {
        if (message == null || message.equals("")) {
            message = HttpStatus.getMessage(status);
        }
        return new ExceptionInfo(status, message);
    }

    private ExceptionInfo getException(String message) {
        if (message == null || message.equals("")) {
            message = HttpStatus.getMessage(HttpStatus.STATUS_500);
        }
        return new ExceptionInfo(HttpStatus.STATUS_500, message);
    }

    /**
     * 运行时异常
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public String handleRuntimeException(HttpServletResponse response, RuntimeException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        ExceptionInfo exception = getException(e.getMessage());
        return format(response, exception);
    }

    /**
     * 空指针异常
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, NullPointerException e) {
        logger.error(e.getMessage());
        e.printStackTrace();
        ExceptionInfo exception = getException(e.getMessage());
        return format(response, exception);
    }

    /**
     * 类型转换异常
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, ClassCastException e) {
        logger.error(e.getMessage());

        ExceptionInfo exception = getException(e.getMessage());
        return format(response, exception);
    }

    /**
     * IO 异常
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, IOException e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(e.getMessage());
        return format(response, exception);
    }

    /**
     * 未知方法异常
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, NoSuchMethodException e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(e.getMessage());
        return format(response, exception);
    }

    /**
     * 数据越界异常
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, IndexOutOfBoundsException e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(e.getMessage());
        return format(response, exception);
    }

    /**
     * 400 错误
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, HttpMessageNotReadableException e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(HttpStatus.STATUS_400, e.getMessage());
        return format(response, exception);
    }

    /**
     * 405 错误
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, HttpRequestMethodNotSupportedException e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(HttpStatus.STATUS_405, e.getMessage());
        return format(response, exception);
    }

    /**
     * 406 错误
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseBody
    public String handleException(HttpServletResponse response, HttpMediaTypeNotAcceptableException e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(HttpStatus.STATUS_406, e.getMessage());
        return format(response, exception);
    }

    /**
     * 500 错误
     *
     * @param response
     * @param e
     * @return
     */
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public String handleException(HttpServletResponse response, RuntimeException e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(HttpStatus.STATUS_500, e.getMessage());
        return format(response, exception);
    }

    /**
     * 未捕获到的异常
     *
     * @param response
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletResponse response, Exception e) {
        logger.error(e.getMessage());
        ExceptionInfo exception = getException(e.getMessage());
        return format(response, exception);
    }

    /**
     * 自定义异常
     *
     * @param response
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ServerException.class)
    public String handleException(HttpServletResponse response, ServerException e) {
        logger.error(e.getMessage());
        return format(response, e.getException());
    }
}
