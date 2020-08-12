
package br.dev.kurtis.clientapp;

import br.dev.kurtis.clientapp.match.Away;
import br.dev.kurtis.clientapp.match.Home;
import lombok.Data;

@Data
public class Match {

    private Home home;
    private Away away;
    private String date;
    private String championship;

    @Override
    public String toString() {
        final String homeTeam = home.getTeam();
        final Integer homeScore = home.getScore();
        final String awayTeam = away.getTeam();
        final Integer awayScore = away.getScore();
        return championship + " - " + homeTeam + " " + homeScore + " x " + awayScore + " " + awayTeam;
    }
}
