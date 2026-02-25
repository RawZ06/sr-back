package fr.rawz06.srback.core.model;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Tournament {
    private final Long id;
    private final String name;
    private final List<Match> matches;

    public Tournament(Long id, @NonNull String name, List<Match> matches) {
        this.id = id;
        this.name = name;
        this.matches = new ArrayList<>(matches);
    }

    public Tournament(@NonNull String name, List<Match> matches) {
        this.id = null;
        this.name = name;
        this.matches = new ArrayList<>(matches);
    }

    public List<Match> getMatches() {
        return List.copyOf(matches);
    }

    public void updateMatches(List<Match> matches) {
        this.matches.clear();
        this.matches.addAll(matches);
    }
}
