package lv.shennar.ticketPrice.services;

import lv.shennar.ticketPrice.domain.LuggageItem;
import lv.shennar.ticketPrice.domain.Passenger;
import lv.shennar.ticketPrice.domain.Ticket;

import java.math.BigDecimal;
import java.util.List;

public interface PriceService {
    Ticket calculateTicketPrice(List<Passenger> passengers,
                                List<LuggageItem> luggage,
                                BigDecimal basePrice,
                                BigDecimal vatTaxRateAsDecimal);
}
