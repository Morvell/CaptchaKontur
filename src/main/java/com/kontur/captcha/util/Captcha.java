package com.kontur.captcha.util;

public class Captcha {

    private String captchaBase64;
    private String captchaAnswer;

    public Captcha(String captchaBase64, String captchaAnswer) {
        this.captchaBase64 = captchaBase64;
        this.captchaAnswer = captchaAnswer;
    }

    public Captcha() {
    }

    public String getCaptchaBase64() {
        return captchaBase64;
    }

    public void setCaptchaBase64(String captchaBase64) {
        this.captchaBase64 = captchaBase64;
    }

    public String getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaAnswer(String captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }
}
