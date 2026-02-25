package fr.rawz06.srback.core.usecase.tournament;

import com.github.javafaker.Faker;
import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.core.port.input.tournament.SyncTournaments;
import fr.rawz06.srback.core.port.output.tournament.FindTournaments;
import fr.rawz06.srback.core.port.output.tournament.PersistTournament;
import fr.rawz06.srback.core.port.output.tournament.TournamentProviderPort;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class SyncTournamentsUseCaseTest {

    private final Faker faker = new Faker();

    private Match createMatch(long id, String player1, String player2, LocalDateTime date) {
        final String round = "Round " + UUID.randomUUID().toString().substring(0, 8);
        return new Match(id, player1, player2, date, round);
    }

    private Match createSameRoundMatch(long id, String player1, String player2, LocalDateTime date, String round) {
        return new Match(id, player1, player2, date, round);
    }
    
    private Match cloneMatch(Match match) {
        return new Match(match.getId(), match.getPlayer1(), match.getPlayer2(), match.getDatetime(), match.getRound());
    }

    @Test
    public void testSyncTournamentsInitialEmpty() {
        // Init data
        final long id = faker.number().randomNumber();
        final String player1 = faker.name().name();
        final String player2 = faker.name().name();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final Match currentMatch = createMatch(id, player1, player2, date);
        final List<Match> currentMatches = List.of(currentMatch);
        final Tournament currentTournament = new Tournament(faker.name().name(), currentMatches);
        final List<Tournament> currentTournaments = List.of(currentTournament);

        //Init dependencies
        TournamentProviderPort tournamentProviders = () -> currentTournament;
        FindTournaments findTournaments = Collections::emptyList;
        PersistTournament persistTournament = (tournament) -> new Tournament(faker.number().randomNumber(), tournament.getName(), tournament.getMatches().stream().map(this::cloneMatch).toList());

        //Init usecase
        SyncTournaments syncTournaments = new SyncTournamentsUseCase(List.of(tournamentProviders),  findTournaments, persistTournament);
        
        //When
        List<Tournament> tournaments = syncTournaments.syncTournaments();
        
        //Then
        assertThat(tournaments).hasSize(currentTournaments.size());
        assertThat(tournaments.getFirst().getName()).isEqualTo(currentTournament.getName());
        assertThat(tournaments.getFirst().getMatches()).hasSize(currentMatches.size());

        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer1()).isEqualTo(currentMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer2()).isEqualTo(currentMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().getFirst().getDatetime()).isEqualTo(currentMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().getFirst().getRound()).isEqualTo(currentMatches.getFirst().getRound());
    }

    @Test
    public void testSyncTournamentsNoChange() {
        // Init data
        final long id = faker.number().randomNumber();
        final String player1 = faker.name().name();
        final String player2 = faker.name().name();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final Match currentMatch = createMatch(id, player1, player2, date);
        final List<Match> currentMatches = List.of(currentMatch);
        final Tournament currentTournament = new Tournament(faker.name().name(), currentMatches);
        final List<Tournament> currentTournaments = List.of(currentTournament);

        //Init dependencies
        TournamentProviderPort tournamentProviders = () -> currentTournament;
        FindTournaments findTournaments = () -> currentTournaments;
        PersistTournament persistTournament = (tournament) -> new Tournament(faker.number().randomNumber(), tournament.getName(), tournament.getMatches().stream().map(this::cloneMatch).toList());

        //Init usecase
        SyncTournaments syncTournaments = new SyncTournamentsUseCase(List.of(tournamentProviders),  findTournaments, persistTournament);

        //When
        List<Tournament> tournaments = syncTournaments.syncTournaments();

        //Then
        assertThat(tournaments).hasSize(currentTournaments.size());
        assertThat(tournaments.getFirst().getName()).isEqualTo(currentTournament.getName());
        assertThat(tournaments.getFirst().getMatches()).hasSize(currentMatches.size());

        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer1()).isEqualTo(currentMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer2()).isEqualTo(currentMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().getFirst().getDatetime()).isEqualTo(currentMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().getFirst().getRound()).isEqualTo(currentMatches.getFirst().getRound());
    }

    @Test
    public void testSyncTournamentsReschedule() {
        // Init data
        final long id = faker.number().randomNumber();
        final String player1 = faker.name().name();
        final String player2 = faker.name().name();
        final String tournamentName = faker.name().name();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final LocalDateTime newDate = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final Match currentMatch = createMatch(id, player1, player2, date);
        final Match currentNewMatch = createSameRoundMatch(id, player1, player2, newDate, currentMatch.getRound());
        final List<Match> currentMatches = List.of(currentMatch);
        final List<Match> currentNewMatches = List.of(currentNewMatch);
        final Tournament currentTournament = new Tournament(tournamentName, currentMatches);
        final Tournament currentNewTournament = new Tournament(tournamentName, currentNewMatches);
        final List<Tournament> currentTournaments = List.of(currentTournament);

        //Init dependencies
        TournamentProviderPort tournamentProviders = () -> currentNewTournament;
        FindTournaments findTournaments = () -> currentTournaments;
        PersistTournament persistTournament = (tournament) -> new Tournament(faker.number().randomNumber(), tournament.getName(), tournament.getMatches().stream().map(this::cloneMatch).toList());

        //Init usecase
        SyncTournaments syncTournaments = new SyncTournamentsUseCase(List.of(tournamentProviders),  findTournaments, persistTournament);

        //When
        List<Tournament> tournaments = syncTournaments.syncTournaments();

        //Then
        assertThat(tournaments).hasSize(currentTournaments.size());
        assertThat(tournaments.getFirst().getName()).isEqualTo(currentNewTournament.getName());
        assertThat(tournaments.getFirst().getMatches()).hasSize(currentNewMatches.size());

        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer1()).isEqualTo(currentNewMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer2()).isEqualTo(currentNewMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().getFirst().getDatetime()).isEqualTo(currentNewMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().getFirst().getRound()).isEqualTo(currentNewMatches.getFirst().getRound());
    }

    @Test
    public void testSyncTournamentsNewMatches() {
        // Init data
        final long id = faker.number().randomNumber();
        final String player1 = faker.name().name();
        final String newPlayer1 = faker.name().name();
        final String player2 = faker.name().name();
        final String newPlayer2 = faker.name().name();
        final String tournamentName = faker.name().name();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final LocalDateTime newDate = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final Match currentMatch = createMatch(id, player1, player2, date);
        final Match currentNewMatch = createMatch(id, newPlayer1, newPlayer2, newDate);
        final List<Match> currentMatches = List.of(currentMatch);
        final List<Match> currentNewMatches = List.of(currentNewMatch);
        final Tournament currentTournament = new Tournament(tournamentName, currentMatches);
        final Tournament currentNewTournament = new Tournament(tournamentName, currentNewMatches);
        final List<Tournament> currentTournaments = List.of(currentTournament);

        //Init dependencies
        TournamentProviderPort tournamentProviders = () -> currentNewTournament;
        FindTournaments findTournaments = () -> currentTournaments;
        PersistTournament persistTournament = (tournament) -> new Tournament(faker.number().randomNumber(), tournament.getName(), tournament.getMatches().stream().map(this::cloneMatch).toList());

        //Init usecase
        SyncTournaments syncTournaments = new SyncTournamentsUseCase(List.of(tournamentProviders),  findTournaments, persistTournament);

        //When
        List<Tournament> tournaments = syncTournaments.syncTournaments();

        //Then
        assertThat(tournaments).hasSize(currentTournaments.size());
        assertThat(tournaments.getFirst().getName()).isEqualTo(currentNewTournament.getName());
        assertThat(tournaments.getFirst().getMatches()).hasSize(currentNewMatches.size() + currentMatches.size());

        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer1()).isEqualTo(currentNewMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer2()).isEqualTo(currentNewMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().getFirst().getDatetime()).isEqualTo(currentNewMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().getFirst().getRound()).isEqualTo(currentNewMatches.getFirst().getRound());

        assertThat(tournaments.getFirst().getMatches().get(1).getPlayer1()).isEqualTo(currentMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().get(1).getPlayer2()).isEqualTo(currentMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().get(1).getDatetime()).isEqualTo(currentMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().get(1).getRound()).isEqualTo(currentMatches.getFirst().getRound());
    }

    @Test
    public void testSyncTournamentsNewTournament() {
        // Init data
        final long id = faker.number().randomNumber();
        final String player1 = faker.name().name();
        final String newPlayer1 = faker.name().name();
        final String player2 = faker.name().name();
        final String newPlayer2 = faker.name().name();
        final String tournamentName = faker.name().name();
        final String newTournamentName = faker.name().name();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final LocalDateTime newDate = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final Match currentMatch = createMatch(id, player1, player2, date);
        final Match currentNewMatch = createMatch(id, newPlayer1, newPlayer2, newDate);
        final List<Match> currentMatches = List.of(currentMatch);
        final List<Match> currentNewMatches = List.of(currentNewMatch);
        final Tournament currentTournament = new Tournament(tournamentName, currentMatches);
        final Tournament currentNewTournament = new Tournament(newTournamentName, currentNewMatches);
        final List<Tournament> currentTournaments = List.of(currentTournament);

        //Init dependencies
        TournamentProviderPort tournamentProviders = () -> currentNewTournament;
        FindTournaments findTournaments = () -> currentTournaments;
        PersistTournament persistTournament = (tournament) -> new Tournament(faker.number().randomNumber(), tournament.getName(), tournament.getMatches().stream().map(this::cloneMatch).toList());

        //Init usecase
        SyncTournaments syncTournaments = new SyncTournamentsUseCase(List.of(tournamentProviders),  findTournaments, persistTournament);

        //When
        List<Tournament> tournaments = syncTournaments.syncTournaments();

        //Then
        assertThat(tournaments).hasSize(currentTournaments.size() + 1);
        assertThat(tournaments.getFirst().getName()).isEqualTo(currentNewTournament.getName());
        assertThat(tournaments.getFirst().getMatches()).hasSize(currentNewMatches.size());

        assertThat(tournaments.get(1).getName()).isEqualTo(currentTournament.getName());
        assertThat(tournaments.get(1).getMatches()).hasSize(currentMatches.size());

        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer1()).isEqualTo(currentNewMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer2()).isEqualTo(currentNewMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().getFirst().getDatetime()).isEqualTo(currentNewMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().getFirst().getRound()).isEqualTo(currentNewMatches.getFirst().getRound());

        assertThat(tournaments.get(1).getMatches().getFirst().getPlayer1()).isEqualTo(currentMatches.getFirst().getPlayer1());
        assertThat(tournaments.get(1).getMatches().getFirst().getPlayer2()).isEqualTo(currentMatches.getFirst().getPlayer2());
        assertThat(tournaments.get(1).getMatches().getFirst().getDatetime()).isEqualTo(currentMatches.getFirst().getDatetime());
        assertThat(tournaments.get(1).getMatches().getFirst().getRound()).isEqualTo(currentMatches.getFirst().getRound());
    }

    @Test
    public void testSyncTournamentsPlayerReplayNewRound() {
        // Init data
        final long id = faker.number().randomNumber();
        final String player1 = faker.name().name();
        final String player2 = faker.name().name();
        final String tournamentName = faker.name().name();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final LocalDateTime newDate = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final Match currentMatch = createMatch(id, player1, player2, date);
        final Match currentNewMatch = createMatch(id, player1, player2, newDate);
        final List<Match> currentMatches = List.of(currentMatch);
        final List<Match> currentNewMatches = List.of(currentNewMatch);
        final Tournament currentTournament = new Tournament(tournamentName, currentMatches);
        final Tournament currentNewTournament = new Tournament(tournamentName, currentNewMatches);
        final List<Tournament> currentTournaments = List.of(currentTournament);

        //Init dependencies
        TournamentProviderPort tournamentProviders = () -> currentNewTournament;
        FindTournaments findTournaments = () -> currentTournaments;
        PersistTournament persistTournament = (tournament) -> new Tournament(faker.number().randomNumber(), tournament.getName(), tournament.getMatches().stream().map(this::cloneMatch).toList());

        //Init usecase
        SyncTournaments syncTournaments = new SyncTournamentsUseCase(List.of(tournamentProviders),  findTournaments, persistTournament);

        //When
        List<Tournament> tournaments = syncTournaments.syncTournaments();

        //Then
        assertThat(tournaments).hasSize(currentTournaments.size());
        assertThat(tournaments.getFirst().getName()).isEqualTo(currentNewTournament.getName());
        assertThat(tournaments.getFirst().getMatches()).hasSize(currentNewMatches.size() + currentMatches.size());

        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer1()).isEqualTo(currentNewMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer2()).isEqualTo(currentNewMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().getFirst().getDatetime()).isEqualTo(currentNewMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().getFirst().getRound()).isEqualTo(currentNewMatches.getFirst().getRound());

        assertThat(tournaments.getFirst().getMatches().get(1).getPlayer1()).isEqualTo(currentMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().get(1).getPlayer2()).isEqualTo(currentMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().get(1).getDatetime()).isEqualTo(currentMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().get(1).getRound()).isEqualTo(currentMatches.getFirst().getRound());
    }

    @Test
    public void testSyncTournamentsPlayerReplayWithNewPlayer() {
        // Init data
        final long id = faker.number().randomNumber();
        final String player1 = faker.name().name();
        final String player2 = faker.name().name();
        final String tournamentName = faker.name().name();
        final String newPlayer2 = faker.name().name();
        final LocalDateTime date = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final LocalDateTime newDate = faker.date().future(15, TimeUnit.DAYS).toInstant()
                .atZone(ZoneOffset.UTC)
                .toLocalDateTime();
        final Match currentMatch = createMatch(id, player1, player2, date);
        final Match currentNewMatch = createMatch(id, player1, newPlayer2, newDate);
        final List<Match> currentMatches = List.of(currentMatch);
        final List<Match> currentNewMatches = List.of(currentNewMatch);
        final Tournament currentTournament = new Tournament(tournamentName, currentMatches);
        final Tournament currentNewTournament = new Tournament(tournamentName, currentNewMatches);
        final List<Tournament> currentTournaments = List.of(currentTournament);

        //Init dependencies
        TournamentProviderPort tournamentProviders = () -> currentNewTournament;
        FindTournaments findTournaments = () -> currentTournaments;
        PersistTournament persistTournament = (tournament) -> new Tournament(faker.number().randomNumber(), tournament.getName(), tournament.getMatches().stream().map(this::cloneMatch).toList());

        //Init usecase
        SyncTournaments syncTournaments = new SyncTournamentsUseCase(List.of(tournamentProviders),  findTournaments, persistTournament);

        //When
        List<Tournament> tournaments = syncTournaments.syncTournaments();

        //Then
        assertThat(tournaments).hasSize(currentTournaments.size());
        assertThat(tournaments.getFirst().getName()).isEqualTo(currentNewTournament.getName());
        assertThat(tournaments.getFirst().getMatches()).hasSize(currentNewMatches.size() + currentMatches.size());

        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer1()).isEqualTo(currentNewMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().getFirst().getPlayer2()).isEqualTo(currentNewMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().getFirst().getDatetime()).isEqualTo(currentNewMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().getFirst().getRound()).isEqualTo(currentNewMatches.getFirst().getRound());

        assertThat(tournaments.getFirst().getMatches().get(1).getPlayer1()).isEqualTo(currentMatches.getFirst().getPlayer1());
        assertThat(tournaments.getFirst().getMatches().get(1).getPlayer2()).isEqualTo(currentMatches.getFirst().getPlayer2());
        assertThat(tournaments.getFirst().getMatches().get(1).getDatetime()).isEqualTo(currentMatches.getFirst().getDatetime());
        assertThat(tournaments.getFirst().getMatches().get(1).getRound()).isEqualTo(currentMatches.getFirst().getRound());
    }
}
