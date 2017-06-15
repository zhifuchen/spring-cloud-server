package com.fufang.cloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhifu.chen on 2017/6/15.
 */
@RestController
@RequestMapping("/test")
public class TestController {
    
    @ResponseBody
    @RequestMapping(value = "/response", method = RequestMethod.GET)
    public String response() {
        return "hello world";
    }
}
