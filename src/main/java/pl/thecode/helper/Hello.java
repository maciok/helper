package pl.thecode.helper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class Hello {

  @GetMapping("/api/hello")
  String hello() {
    return "Hello World!";
  }




}
