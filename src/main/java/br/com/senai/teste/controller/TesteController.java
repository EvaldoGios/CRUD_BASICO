package br.com.senai.teste.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class TesteController {
    @GetMapping("/teste")
    public String teste() {
        return "I just wanna feel alive.\n Lil Peep, Nuts.";
    }

}
