package fr.rawz06.srback.core.usecase.match;

import com.github.javafaker.Faker;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.exception.business.SameStaffException;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Restreamer;
import fr.rawz06.srback.core.port.input.match.AssignStaff;
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

public class AssignStaffUseCaseTest {

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
    void assignAllCorrectStaff() throws SameStaffException, RestreamerNotFoundException, MatchNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String host = faker.name().username();
        final String cohost =  faker.name().username();
        final Match currentMatch = createMatch(id);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> Optional.of(new Restreamer(name));
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        AssignStaff assignStaffUseCase = new AssignStaffUseCase(findMatchById, findRestreamerByName, persistMatch);

        //When
        Match match = assignStaffUseCase.assignStaff(id, host, cohost);

        //Then
        assertThat(match.getHost().name()).isEqualTo(host);
        assertThat(match.getCohost().name()).isEqualTo(cohost);
        assertThat(match.getDatetime()).isEqualTo(currentMatch.getDatetime());
    }

    @Test
    void assignOnlyHostStaff() throws SameStaffException, RestreamerNotFoundException, MatchNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String host = faker.name().username();
        final Match currentMatch = createMatch(id);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> name == null ? Optional.empty() : Optional.of(new Restreamer(name));
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        AssignStaff assignStaffUseCase = new AssignStaffUseCase(findMatchById, findRestreamerByName, persistMatch);

        //When
        Match match = assignStaffUseCase.assignStaff(id, host, null);

        //Then
        assertThat(match.getHost().name()).isEqualTo(host);
        assertThat(match.getCohost()).isNull();
        assertThat(match.getDatetime()).isEqualTo(currentMatch.getDatetime());
    }

    @Test
    void assignOnlyCohostStaff() throws SameStaffException, RestreamerNotFoundException, MatchNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String cohost = faker.name().username();
        final Match currentMatch = createMatch(id);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> name == null ? Optional.empty() : Optional.of(new Restreamer(name));
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        AssignStaff assignStaffUseCase = new AssignStaffUseCase(findMatchById, findRestreamerByName, persistMatch);

        //When
        Match match = assignStaffUseCase.assignStaff(id, null, cohost);

        //Then
        assertThat(match.getHost()).isNull();
        assertThat(match.getCohost().name()).isEqualTo(cohost);
        assertThat(match.getDatetime()).isEqualTo(currentMatch.getDatetime());
    }

    @Test
    void assignSameHostAndCohostStaff() throws SameStaffException, RestreamerNotFoundException, MatchNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String host = faker.name().username();
        final Match currentMatch = createMatch(id);
        final Restreamer restreamer = new Restreamer(host);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> Optional.of(restreamer);
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        AssignStaff assignStaffUseCase = new AssignStaffUseCase(findMatchById, findRestreamerByName, persistMatch);

        //When
        //Then
        assertThatThrownBy(() -> assignStaffUseCase.assignStaff(id, host, host))
                .isInstanceOf(SameStaffException.class);
    }

    @Test
    void unassignStaff() throws SameStaffException, RestreamerNotFoundException, MatchNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final Match currentMatch = createMatch(id);
        currentMatch.assignStaff(new Restreamer(faker.name().username()), new Restreamer(faker.name().username()));

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.of(currentMatch);
        FindRestreamerByName findRestreamerByName = name -> name == null ? Optional.empty() : Optional.of(new Restreamer(name));
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        AssignStaff assignStaffUseCase = new AssignStaffUseCase(findMatchById, findRestreamerByName, persistMatch);

        //When
        Match match = assignStaffUseCase.assignStaff(id, null, null);

        //Then
        assertThat(match.getHost()).isNull();
        assertThat(match.getCohost()).isNull();
        assertThat(match.getDatetime()).isEqualTo(currentMatch.getDatetime());
    }

    @Test
    void assignUndefinedMatchStaff() throws SameStaffException, RestreamerNotFoundException, MatchNotFoundException {
        // Init data
        final long id = faker.number().randomNumber();
        final String host = faker.name().username();
        final String cohost = faker.name().username();
        final Restreamer restreamer = new Restreamer(host);

        //Init dependencies
        FindMatchById findMatchById = matchId -> Optional.empty();
        FindRestreamerByName findRestreamerByName = name -> Optional.of(restreamer);
        PersistMatch persistMatch = match -> new Match(faker.number().randomNumber(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound(), match.getHost(), match.getCohost(), match.isVisible(), match.getAvailabilities());

        //Init usecase
        AssignStaff assignStaffUseCase = new AssignStaffUseCase(findMatchById, findRestreamerByName, persistMatch);

        //When
        //Then
        assertThatThrownBy(() -> assignStaffUseCase.assignStaff(id, host, cohost))
                .isInstanceOf(MatchNotFoundException.class);
    }
}
