package lv.shennar.ticketPrice.domain;

import java.util.UUID;

public class LuggageItem {

    private UUID id;
    private UUID ownerId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public LuggageItem(UUID ownerId) {
        this.id = UUID.randomUUID();
        this.ownerId = ownerId;
    }
}
