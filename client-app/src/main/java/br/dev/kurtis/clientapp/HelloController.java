package br.dev.kurtis.clientapp;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.net.ConnectException;

@Log
@RestController
public class HelloController {

    private final MatchClient client;

    public HelloController(MatchClient client) {
        this.client = client;
    }

    @GetMapping("/")
    public String helloSleuth() {
        log.info("Request received");
        final Call<Match> call = client.requestMatch(1);
        try {
            final Response<Match> response = call.execute();
            final Match match = response.body();
            return match.toString();
        } catch (IOException ex) {
            throw new UnavailabilityException(ex);
        }
    }
}

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "The matches service in temporarily unavailable")
class UnavailabilityException extends RuntimeException {

    public UnavailabilityException(Throwable cause) {
        super(cause);
    }
}
