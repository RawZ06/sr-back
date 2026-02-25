package fr.rawz06.srback.core.usecase.match;

import com.github.javafaker.Faker;
import fr.rawz06.srback.core.exception.business.NullAvailabilityException;
import fr.rawz06.srback.core.exception.domain.IllegalMatchStateException;
import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.model.RestreamerAvailability;
import fr.rawz06.srback.core.port.input.match.UpdateRestreamerAvailability;
import fr.rawz06.srback.core.port.output.match.FindMatchById;
import fr.rawz06.srback.core.port.output.match.FindRestreamerByName;
import fr.rawz06.srback.core.port.output.match.PersistMatch;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UpdateRestreamerAvailabilityUseCaseTest {

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
    void updateRestreamerAvailability() throws MatchNotFoundException, RestreamerNotFoundException, NullAvailabilityException {
        // Init data
        final long id = faker.number().randomNumber();
        final String restreamerName = faker.name().username();
        final Match currentMatch = createMatch(id);
        final Restreamer currentRestreamer = new Restreamer(restreamerName);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> Optional.of(currentRestreamer);
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        UpdateRestreamerAvailability updateRestreamerAvailability = new UpdateRestreamerAvailabilityUseCase(findMatchById, persistMatch, findRestreamerByName);

        //When
        Match match = updateRestreamerAvailability.updateRestreamerAvailability(id, currentRestreamer.name(), RestreamerAvailability.AVAILABLE);

        //Then
        assertThat(match.getDatetime()).isEqualTo(currentMatch.getDatetime());
        assertThat(match.getRound()).isEqualTo(currentMatch.getRound());
        assertThat(match.getPlayer1()).isEqualTo(currentMatch.getPlayer1());
        assertThat(match.getPlayer2()).isEqualTo(currentMatch.getPlayer2());

        assertThat(match.getAvailabilities().get(currentRestreamer)).isEqualTo(RestreamerAvailability.AVAILABLE);
    }

    @Test
    void updateRestreamerNullAvailability() throws MatchNotFoundException, RestreamerNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String restreamerName = faker.name().username();
        final Match currentMatch = createMatch(id);
        final Restreamer currentRestreamer = new Restreamer(restreamerName);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> Optional.of(currentRestreamer);
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        UpdateRestreamerAvailability updateRestreamerAvailability = new UpdateRestreamerAvailabilityUseCase(findMatchById, persistMatch, findRestreamerByName);

        //When
        assertThatThrownBy(() -> updateRestreamerAvailability.updateRestreamerAvailability(id, currentRestreamer.name(), null))
                .isInstanceOf(NullAvailabilityException.class);
    }

    @Test
    void updateRestreamerAvailabilityUndefinedMatch() throws MatchNotFoundException, RestreamerNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String restreamerName = faker.name().username();
        final Restreamer currentRestreamer = new Restreamer(restreamerName);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.empty();
        FindRestreamerByName findRestreamerByName = name -> Optional.of(currentRestreamer);
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        UpdateRestreamerAvailability updateRestreamerAvailability = new UpdateRestreamerAvailabilityUseCase(findMatchById, persistMatch, findRestreamerByName);

        //When
        //Then
        assertThatThrownBy(() -> updateRestreamerAvailability.updateRestreamerAvailability(id, currentRestreamer.name(), RestreamerAvailability.AVAILABLE))
                .isInstanceOf(MatchNotFoundException.class);
    }

    @Test
    void updateRestreamerAvailabilityUndefinedRestreamer() throws MatchNotFoundException, RestreamerNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String restreamerName = faker.name().username();
        final Match currentMatch = createMatch(id);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> Optional.empty();
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        UpdateRestreamerAvailability updateRestreamerAvailability = new UpdateRestreamerAvailabilityUseCase(findMatchById, persistMatch, findRestreamerByName);

        //When
        //Then
        assertThatThrownBy(() -> updateRestreamerAvailability.updateRestreamerAvailability(id, restreamerName, RestreamerAvailability.AVAILABLE))
                .isInstanceOf(RestreamerNotFoundException.class);
    }
}
