package com.zuhee.gw.api.controller.test;

import com.zuhee.gw.api.common.aop.UserActivityLog;
import com.zuhee.gw.api.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    @UserActivityLog
    public ApiResponse<Map<String, String>> hello() {
        return ApiResponse.ok(Map.of("message", "Hello, Zuhee-GW!"));
    }

    @GetMapping("/slow")
    @UserActivityLog
    public ApiResponse<String> slow() throws InterruptedException {
        Thread.sleep(1100);
        return ApiResponse.ok("This was a slow API");
    }
}
