package com.kontur.captcha.util;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

import static org.junit.Assert.*;

public class CaptchaMakerTest {

  CaptchaMaker captchaMaker = new CaptchaMaker();

  @Before
  public void before() throws IOException {
    captchaMaker.make();
  }

  @Test
  public void notNull() throws Exception {
    assertNotNull(captchaMaker.getCaptcha());
    assertNotNull(captchaMaker.getCaptchaImageString());
  }

  @Test
  public void random() throws IOException {
    CaptchaMaker newCaptcha = new CaptchaMaker();
    newCaptcha.make();

    assertNotEquals(captchaMaker.getCaptcha(), newCaptcha.getCaptcha());
    assertNotEquals(captchaMaker.getCaptchaImageString(), newCaptcha.getCaptchaImageString());
  }

  @Test
  public void isNumberAndLetter() {

    boolean f = true;

    for (char a : captchaMaker.getCaptcha().getCaptchaAnswer().toCharArray()) {
      if (Character.isAlphabetic(a) || Character.isDigit(a)) {
        continue;
      } else {
        f = false;
        break;
      }
    }
    assertTrue(f);
  }

  @Test
  public void isNumberAndLetterTwo() {

    boolean f = true;

    for (char a : captchaMaker.getCaptcha().getCaptchaAnswer().toCharArray()) {
      if (Character.isAlphabetic(a) || Character.isDigit(a)) {
        continue;
      } else {
        f = false;
        break;
      }
    }
    assertTrue(f);
  }


}