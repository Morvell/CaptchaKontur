package com.kontur.captcha.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.kontur.captcha.util.Captcha;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class CaptchaControllerTest {

  @InjectMocks
  private CaptchaController captchaController;

  private MockMvc mockMvc;


  @Before
  public void setup() {

    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(captchaController).build();
  }


  @Test
  public void get_test() throws Exception {

    Map<String, Captcha> captchaMap = captchaController.getCaptchaList();
    final Captcha[] captcha = {null};
    final String[] id = {null};
    for (String key : captchaMap.keySet()) {
      id[0] = key;
      captcha[0] = captchaMap.get(id[0]);
    }
    mockMvc.perform(
        get("/get"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(mvcResult -> {
          for (String key : captchaMap.keySet()) {
            id[0] = key;
            captcha[0] = captchaMap.get(id[0]);
          }
        })
        .andExpect(MockMvcResultMatchers.header().string("id", id[0]))
        .andExpect(MockMvcResultMatchers.header().string("answer", captcha[0].getCaptchaAnswer()));

  }


  @Test
  public void post_ok() throws Exception {

    Map<String, Captcha> captchaMap = captchaController.getCaptchaList();
    final Captcha[] captcha = {null};
    final String[] id = {null};
    for (String key : captchaMap.keySet()) {
      id[0] = key;
      captcha[0] = captchaMap.get(id[0]);
    }
    mockMvc.perform(
        get("/get"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(mvcResult -> {
          for (String key : captchaMap.keySet()) {
            id[0] = key;
            captcha[0] = captchaMap.get(id[0]);
          }
        });

    mockMvc
        .perform(post("/post").header("id", id[0]).header("answer", captcha[0].getCaptchaAnswer()))
        .andExpect(MockMvcResultMatchers.status().isOk());

  }


  @Test
  public void post_bad_request() throws Exception {

    Map<String, Captcha> captchaMap = captchaController.getCaptchaList();
    final Captcha[] captcha = {null};
    final String[] id = {null};
    for (String key : captchaMap.keySet()) {
      id[0] = key;
      captcha[0] = captchaMap.get(id[0]);
    }
    mockMvc.perform(
        get("/get"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(mvcResult -> {
          for (String key : captchaMap.keySet()) {
            id[0] = key;
            captcha[0] = captchaMap.get(id[0]);
          }
        });

    mockMvc.perform(post("/post").header("id", id[0]))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

  }


  @Test
  public void post_no_acceptable() throws Exception {
    mockMvc.perform(
        get("/get"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc.perform(post("/post").header("id", "123").header("answer", "123"))
        .andExpect(MockMvcResultMatchers.status().isNotAcceptable());

  }

  @Test
  public void post_duble() throws Exception {

    Map<String, Captcha> captchaMap = captchaController.getCaptchaList();
    final Captcha[] captcha = {null};
    final String[] id = {null};
    for (String key : captchaMap.keySet()) {
      id[0] = key;
      captcha[0] = captchaMap.get(id[0]);
    }
    mockMvc.perform(
        get("/get"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(mvcResult -> {
          for (String key : captchaMap.keySet()) {
            id[0] = key;
            captcha[0] = captchaMap.get(id[0]);
          }
        });

    mockMvc
        .perform(post("/post").header("id", id[0]).header("answer", captcha[0].getCaptchaAnswer()))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc
        .perform(post("/post").header("id", id[0]).header("answer", captcha[0].getCaptchaAnswer()))
        .andExpect(MockMvcResultMatchers.status().isNotAcceptable());

  }

  @Test
  public void post_forbidden() throws Exception {

    Map<String, Captcha> captchaMap = captchaController.getCaptchaList();
    final Captcha[] captcha = {null};
    final String[] id = {null};
    for (String key : captchaMap.keySet()) {
      id[0] = key;
      captcha[0] = captchaMap.get(id[0]);
    }
    mockMvc.perform(
        get("/get"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(mvcResult -> {
          for (String key : captchaMap.keySet()) {
            id[0] = key;
            captcha[0] = captchaMap.get(id[0]);
          }
        });

    mockMvc.perform(post("/post").header("id", id[0]).header("answer", "123"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  public void post_time_delay() throws Exception {

    Map<String, Captcha> captchaMap = captchaController.getCaptchaList();
    final Captcha[] captcha = {null};
    final String[] id = {null};
    for (String key : captchaMap.keySet()) {
      id[0] = key;
      captcha[0] = captchaMap.get(id[0]);
    }
    captchaController.setDelay(1);

    mockMvc.perform(
        get("/get"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(mvcResult -> {
          for (String key : captchaMap.keySet()) {
            id[0] = key;
            captcha[0] = captchaMap.get(id[0]);
          }
        });

    Thread.sleep(1200);
    mockMvc.perform(
        post("/post").header("id", id[0]).header("answer", captcha[0].getCaptchaAnswer()))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }


}