package fr.rawz06.srback.core.port.input.user;

import fr.rawz06.srback.core.model.User;

public interface GetConnectedUserInformation {
    User getConnectedUserInformation(String userId);
}
