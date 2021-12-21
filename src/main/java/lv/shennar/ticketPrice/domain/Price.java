package lv.shennar.ticketPrice.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Price {

    private UUID passengerId;
    private BigDecimal basePrice;

    public UUID getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(UUID passengerId) {
        this.passengerId = passengerId;
    }

    private BigDecimal luggagePrice;

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getLuggagePrice() {
        return luggagePrice;
    }

    public void setLuggagePrice(BigDecimal luggagePrice) {
        this.luggagePrice = luggagePrice;
    }

    public Price(UUID passengerId, BigDecimal basePrice, BigDecimal luggagePrice) {
        this.passengerId = passengerId;
        this.basePrice = basePrice;
        this.luggagePrice = luggagePrice;
    }
}
