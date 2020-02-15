package com.ruscassie.litige.proxy;

import com.ruscassie.litige.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-server")
//@RibbonClient(name = "auth-server")
public interface UserProxy {

    @GetMapping(value = "/auth/users/findByEmail")
    User findByEmail(@RequestParam String email);
}
