package com.ls.core.error;

import com.alibaba.fastjson.JSONObject;
import com.ls.base.BaseApiService;
import com.ls.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局捕获异常工具类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseApiService<JSONObject> {
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public BaseResponse<JSONObject> exception(Exception e){
        log.error("###全局捕获异常###,error:{}", e);
        return setResultError("系统错误");

    }
}
