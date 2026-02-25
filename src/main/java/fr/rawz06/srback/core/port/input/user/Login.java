package fr.rawz06.srback.core.port.input.user;

import fr.rawz06.srback.core.model.User;

public interface Login {
    User login(String username, String password);
}
