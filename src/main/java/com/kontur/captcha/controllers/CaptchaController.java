package com.kontur.captcha.controllers;

import com.kontur.captcha.util.Captcha;
import com.kontur.captcha.util.CaptchaMaker;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CaptchaController {

  @Getter
  private Map<String, Captcha> captchaList = new ConcurrentSkipListMap<>();

  private CaptchaMaker captchaMaker = new CaptchaMaker();

  @Getter
  @Setter
  private int delay;

  public CaptchaController() {

    captchaList = new ConcurrentSkipListMap<>();
    captchaMaker = new CaptchaMaker();
    delay = 60;
  }

  @GetMapping("/get")
  @ResponseBody
  public ResponseEntity<String> getResponse() {

    try {
      captchaMaker.make();
    } catch (IOException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    String id = UUID.randomUUID().toString();

    captchaList.put(id, captchaMaker.getCaptcha());

    HttpHeaders headers = new HttpHeaders();
    headers.add("id", String.valueOf(id));
    headers.add("answer", captchaMaker.getCaptcha().getCaptchaAnswer());

    return new ResponseEntity<>(captchaMaker.getCaptchaImageString(), headers, HttpStatus.OK);


  }

  @PostMapping("/post")
  public ResponseEntity<String> postRequest(
      @RequestHeader(value = "id", defaultValue = "null") String id,
      @RequestHeader(value = "answer", defaultValue = "null") String answer) {

    Captcha captcha = captchaList.getOrDefault(id, null);

    if (id.equals("null") || answer.equals("null")) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    if (captcha == null) {
      return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    if (captcha.getCaptchaAnswer().equals(answer)) {
      if (captcha.getBirthTime().compareTo(LocalDateTime.now().minusSeconds(delay)) >= 0) {
        removeOldDataWithThis(id);
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        removeOldDataWithThis(id);
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
      }
    } else {
      removeOldDataWithThis(id);
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
  }

  private void removeOldDataWithThis(String id) {
    captchaList.remove(id);
    captchaList.forEach((s, captcha) -> {
      if (captcha.getBirthTime().compareTo(LocalDateTime.now().minusSeconds(delay)) < 0) {
        captchaList.remove(s);
      }
    });
  }

  @Scheduled(cron = "0 0 * * * *")
  public void reportCurrentTime() {
    captchaList.forEach((s, captcha) -> {
      if (captcha.getBirthTime().compareTo(LocalDateTime.now().minusMinutes(1)) < 0) {
        captchaList.remove(s);
      }
    });
  }
}
