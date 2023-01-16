package br.com.dea.management.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
    public ResponseEntity<String> getFactory() {
        return ResponseEntity.ok("HELLO WORLD");
    }
}
