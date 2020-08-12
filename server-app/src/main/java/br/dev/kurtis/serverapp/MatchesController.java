package br.dev.kurtis.serverapp;

import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class MatchesController {

    @GetMapping(value = "/matches/1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String helloSleuth() {
        log.info("request received to get match 1");
        return "{\"home\":{\"team\":\"Guarani\",\"score\":2},\"away\":{\"team\":\"Cruzeiro\",\"score\":3},\"date\":\"2020-11-08 20:30:00\",\"championship\":\"Brasileirão 2020 - Serie B\"}";
    }
}
