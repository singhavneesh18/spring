package com.example.spring.controller;

import com.example.spring.dto.Greeting;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MyAppRestController {

  @GetMapping("/api/getName")
  public Greeting getName() {
    //        log.info("Request received....");
    return new Greeting("Hello new World number: " + new Random().nextInt(1000));
  }
}
