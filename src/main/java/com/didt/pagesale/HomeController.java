package com.didt.pagesale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {
    @GetMapping("/test")
    public String test() {
        log.info("This is working message");
        return "Testing message";
    }
}
