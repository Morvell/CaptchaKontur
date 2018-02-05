package com.kontur.captcha.controllers;

import com.kontur.captcha.util.Captcha;
import com.kontur.captcha.util.CaptchaMaker;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class CaptchaController {

    @GetMapping("/")
    public String greetingForm(Model model) {
        try {
            model.addAttribute("captcha", new CaptchaMaker().make());
            return "index";
        } catch (IOException e) {
            e.printStackTrace();
            return "exception";
        }

    }

    @PostMapping("/")
    public String greetingSubmit(@ModelAttribute Captcha captcha) {
        return "result";
    }
}
