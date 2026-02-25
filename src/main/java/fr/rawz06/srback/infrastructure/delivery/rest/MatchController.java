package fr.rawz06.srback.infrastructure.delivery.rest;

import fr.rawz06.srback.core.exception.business.MatchNotFoundException;
import fr.rawz06.srback.core.exception.business.NullAvailabilityException;
import fr.rawz06.srback.core.exception.business.RestreamerNotFoundException;
import fr.rawz06.srback.core.exception.business.SameStaffException;
import fr.rawz06.srback.infrastructure.delivery.adapter.MatchDeliveryAdapter;
import fr.rawz06.srback.infrastructure.delivery.body.RestreamerAvailabilityBody;
import fr.rawz06.srback.infrastructure.delivery.body.StaffBody;
import fr.rawz06.srback.infrastructure.delivery.dto.MatchDTO;
import fr.rawz06.srback.infrastructure.delivery.exception.BadAvailabilitySetException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/matches")
public class MatchController {
    private final MatchDeliveryAdapter matchDeliveryAdapter;

    @GetMapping("")
    public List<MatchDTO> getMatches() {
        return this.matchDeliveryAdapter.getPublicMatches();
    }

    @PatchMapping("/{id}/staff")
    public MatchDTO updateStaffMatch(@PathVariable Long id, @RequestBody StaffBody staff) throws SameStaffException, RestreamerNotFoundException, MatchNotFoundException {
        return this.matchDeliveryAdapter.updateStaffMatch(id, staff.getHost(), staff.getCohost());
    }

    @PatchMapping("/{id}/visible")
    public MatchDTO updateVisibleMatch(@PathVariable Long id) throws MatchNotFoundException {
        return this.matchDeliveryAdapter.toggleMatchVisibility(id);
    }

    @PutMapping("/{id}/restream/{name}")
    public MatchDTO restream(@PathVariable Long id, @PathVariable String name, @RequestBody RestreamerAvailabilityBody availability) throws MatchNotFoundException, RestreamerNotFoundException, BadAvailabilitySetException, NullAvailabilityException {
        return this.matchDeliveryAdapter.updateRestreamerAvailability(id, name, availability.getAvailability());
    }
}
