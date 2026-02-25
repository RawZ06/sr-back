package fr.rawz06.srback.infrastructure.delivery.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class StaffBody {
    private String host;
    private String cohost;
}
