package fr.rawz06.srback.core.usecase.tournament;

import fr.rawz06.srback.core.model.Match;
import fr.rawz06.srback.core.model.Tournament;
import fr.rawz06.srback.core.port.input.tournament.SyncTournaments;
import fr.rawz06.srback.core.port.output.tournament.FindTournaments;
import fr.rawz06.srback.core.port.output.tournament.PersistTournament;
import fr.rawz06.srback.core.port.output.tournament.TournamentProviderPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SyncTournamentsUseCase implements SyncTournaments {
    private final List<TournamentProviderPort> tournamentProviders;
    private final FindTournaments findTournaments;
    private final PersistTournament persistTournament;

    @Override
    public List<Tournament> syncTournaments() {
        List<Tournament> tournaments = findTournaments.findTournaments();
        List<Tournament> persistedTournaments = new ArrayList<>();
        for (TournamentProviderPort tournamentProviderPort : tournamentProviders) {
            Tournament tournament = tournamentProviderPort.getTournament();
            Optional<Tournament> existingTournament = findTournamentByName(tournaments, tournament.getName());
            if(existingTournament.isEmpty()) {
                persistedTournaments.add(persistTournament.persist(tournament));
            } else {
                existingTournament.get().updateMatches(syncMatches(tournament.getMatches(), existingTournament.get().getMatches()));
                persistedTournaments.add(persistTournament.persist(existingTournament.get()));
            }
        }
        for(Tournament tournament: tournaments) {
            Optional<Tournament> existingTournament = findTournamentByName(persistedTournaments, tournament.getName());
            if(existingTournament.isEmpty()) {
                persistedTournaments.add(persistTournament.persist(tournament));
            }
        }
        return persistedTournaments;
    }

    private Optional<Tournament> findTournamentByName(List<Tournament> tournaments, String tournamentName) {
        for (Tournament tournament : tournaments) {
            if (tournament.getName().equals(tournamentName)) {
                return Optional.of(tournament);
            }
        }
        return Optional.empty();
    }

    private List<Match> syncMatches(List<Match> matches, List<Match> existingMatches) {
        List<Match> updatedMatches = new ArrayList<>();
        for (Match match : matches) {
            Optional<Match> existingMatch = findMatchByPlayersAndRound(existingMatches, match.getPlayer1(), match.getPlayer2(), match.getRound());
            if(existingMatch.isEmpty()) {
                updatedMatches.add(match);
            } else if(existingMatch.get().getDatetime().equals(match.getDatetime())) {
                updatedMatches.add(existingMatch.get());
            } else {
                Match newMatch = new Match(
                        existingMatch.get().getId(),
                        existingMatch.get().getPlayer1(),
                        existingMatch.get().getPlayer2(),
                        match.getDatetime(),
                        existingMatch.get().getRound(),
                        null,
                        null,
                        false,
                        new HashMap<>());
                updatedMatches.add(newMatch);
            }
        }
        for(Match existingMatch : existingMatches) {
            Optional<Match> matchOptional = findMatchByPlayersAndRound(updatedMatches, existingMatch.getPlayer1(), existingMatch.getPlayer2(), existingMatch.getRound());
            if(matchOptional.isEmpty()) {
                updatedMatches.add(existingMatch);
            }
        }
        return updatedMatches;
    }

    private Optional<Match> findMatchByPlayersAndRound(List<Match> matches, String player1, String player2, String round) {
        for (Match match : matches) {
            if (match.getPlayer1().equals(player1) && match.getPlayer2().equals(player2) && match.getRound().equals(round)) {
                return Optional.of(match);
            }
        }
        return Optional.empty();
    }
}
