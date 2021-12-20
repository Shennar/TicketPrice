package lv.shennar.ticketPrice.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Ticket {

    private UUID id;
    private BigDecimal totalPrice;
    private List<Price> priceComponents;

    public Ticket() {
        this.id = UUID.randomUUID();
    }

    public Ticket(BigDecimal totalPrice, List<Price> priceComponents) {
        this.id = UUID.randomUUID();
        this.totalPrice = totalPrice;
        this.priceComponents = priceComponents;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Price> getPriceComponents() {
        return priceComponents;
    }

    public void setPriceComponents(List<Price> priceComponents) {
        this.priceComponents = priceComponents;
    }
}
