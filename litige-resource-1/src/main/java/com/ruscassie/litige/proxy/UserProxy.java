package com.ruscassie.litige.proxy;

import com.ruscassie.litige.dto.User;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "auth-server")
public interface UserProxy {

    @PostMapping(value = "/users/findByEmail")
    User findByEmail(@RequestParam String email);
}
