package org.hong.pay.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hong
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    @GetMapping
    public String home() {
        return "home";
    }
}
