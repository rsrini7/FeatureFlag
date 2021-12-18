package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    
    @GetMapping("/api/v1/accounts")
    public String accounts() {
        return "";
    }
}
