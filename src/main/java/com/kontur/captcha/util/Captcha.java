package com.kontur.captcha.util;

import java.time.LocalDateTime;
import lombok.Getter;

public class Captcha {

  @Getter
  private LocalDateTime birthTime;
  @Getter
  private String captchaAnswer;

  public Captcha(String captchaAnswer) {
    super();
    this.birthTime = LocalDateTime.now();
    this.captchaAnswer = captchaAnswer;
  }

}
