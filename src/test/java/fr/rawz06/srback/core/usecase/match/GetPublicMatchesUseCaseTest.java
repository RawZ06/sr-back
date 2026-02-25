package fr.rawz06.srback.core.usecase.match;

import com.github.javafaker.Faker;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.port.input.match.GetPublicMatches;
import fr.rawz06.srback.core.port.output.match.FindMatchesByVisibility;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class GetPublicMatchesUseCaseTest {
    private final Faker faker = new Faker();

    private Match createMatch(long id) {
        final String player1 = faker.name().username();
        final String player2 =  faker.name().username();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final String round = "Round " + UUID.randomUUID().toString().substring(0, 8);

        return new Match(id, player1, player2, date, round);
    }

    @Test
    void getPublicMatches() {
        // Init data
        final long id = faker.number().randomNumber();
        final Match currentMatch = createMatch(id);
        final List<Match> currentMatches = List.of(currentMatch);

        //Init dependencies
        FindMatchesByVisibility findMatchesByVisibility = visibility -> currentMatches;

        //Init usecase
        GetPublicMatches getPublicMatchesUseCase = new GetPublicMatchesUseCase(findMatchesByVisibility);

        //When
        List<Match> matches = getPublicMatchesUseCase.getPublicMatches();

        //Then
        assertThat(matches).hasSize(matches.size());
        assertThat(matches.getFirst().getDatetime()).isEqualTo(currentMatch.getDatetime());
        assertThat(matches.getFirst().getRound()).isEqualTo(currentMatch.getRound());
        assertThat(matches.getFirst().getPlayer1()).isEqualTo(currentMatch.getPlayer1());
        assertThat(matches.getFirst().getPlayer2()).isEqualTo(currentMatch.getPlayer2());
    }
}
