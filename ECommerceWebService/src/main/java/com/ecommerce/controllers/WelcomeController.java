/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author chungnt
 */
@Controller
public class WelcomeController {

    private List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");

    @GetMapping("/welcome")
    public String main(Model model) {
        model.addAttribute("message", "chungnt");
        model.addAttribute("tasks", tasks);
        return "welcome";
    }
}
