package com.ls.elk.aop;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ls.elk.kafka.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈全局捕获异常〉
 *
 * @author liusheng
 * @create 2019/8/2
 * @since 1.0.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private KafkaSender<JSONObject> kafkaSender;
    //捕获运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONObject exceptionHandler(Exception e) {
        log.info("###全局捕获异常###,error:{}", e);

        // 1.封装异常日志信息
        JSONObject errorJson = new JSONObject();
        JSONObject logJson = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        logJson.put("request_time", df.format(new Date()));
        logJson.put("error_info", e);

        errorJson.put("request_error", logJson);
        kafkaSender.send(errorJson);
        // 2. 返回错误信息
        JSONObject result = new JSONObject();
        result.put("code", 500);
        result.put("msg", "系统错误");

        // 参数中记录下，IP和端口号
        return result;
    }

}
