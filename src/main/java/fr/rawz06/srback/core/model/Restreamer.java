package fr.rawz06.srback.core.model;

import lombok.NonNull;

public record Restreamer(Long id, String name) {

    public Restreamer(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public Restreamer(String host) {
        this(null, host);
    }
}