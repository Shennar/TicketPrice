package lv.shennar.ticketPrice.domain;

import java.util.UUID;

public class Passenger {

    private UUID id;
    private boolean adult;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public Passenger(boolean adult) {
        this.id = UUID.randomUUID();
        this.adult = adult;
    }
}
