package com.kontur.captcha.controllers;

import com.kontur.captcha.util.CaptchaMaker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class Main {

    @RequestMapping("/")
    public ModelAndView index() throws IOException {

        String file = new CaptchaMaker().make();
        HashMap<String, Object> model = new HashMap<>();
        model.put("captcha", file);
        return new ModelAndView("index", model);
    }
}
