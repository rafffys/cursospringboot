package br.com.caelum.forum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController {
    @GetMapping("/")
    public String olaMundo() {
        return "Olá Mundo";
    }
}
