package lv.shennar.ticketPrice.domain;

import java.math.BigDecimal;

public class Price {

    private BigDecimal basePrice;
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

    public Price(BigDecimal basePrice, BigDecimal luggagePrice) {
        this.basePrice = basePrice;
        this.luggagePrice = luggagePrice;
    }
}
