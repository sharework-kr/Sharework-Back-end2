package com.sharework.domain;
;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class testController {

    @GetMapping("/api/hello1")
    public String hello1(@RequestParam String password) {

        return "123";
    }
}