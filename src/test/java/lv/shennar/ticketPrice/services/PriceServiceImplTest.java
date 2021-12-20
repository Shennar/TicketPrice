package lv.shennar.ticketPrice.services;

import lv.shennar.ticketPrice.domain.LuggageItem;
import lv.shennar.ticketPrice.domain.Passenger;
import lv.shennar.ticketPrice.domain.Price;
import lv.shennar.ticketPrice.domain.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PriceServiceImplTest {

    private final Passenger ADULT_PASSENGER = new Passenger(true);
    private final Passenger CHILD_PASSENGER = new Passenger(false);

    private final LuggageItem ADULT_PASSENGER_LUGGAGE_1 = new LuggageItem(ADULT_PASSENGER.getId());
    private final LuggageItem ADULT_PASSENGER_LUGGAGE_2 = new LuggageItem(ADULT_PASSENGER.getId());

    private final LuggageItem CHILD_PASSENGER_LUGGAGE = new LuggageItem(CHILD_PASSENGER.getId());

    private final BigDecimal BASE_PRICE = BigDecimal.TEN;
    private final BigDecimal VAT_TAX_RATE_AS_DECIMAL = new BigDecimal("0.21");
    private final BigDecimal CORRECT_TICKET_TOTAL_PRICE = new BigDecimal("29.04");
    private final Price ADULT_TICKET_PRICE = new Price(new BigDecimal("12.10"), new BigDecimal("7.26"));
    private final Price CHILD_TICKET_PRICE = new Price(new BigDecimal("6.05"), new BigDecimal("3.63"));

    private PriceService priceService = new PriceServiceImpl();

    @Test
    void givenOneAdultAndOneChildPassengersWith3Bags_whenPriceCalculationServiceCalled_thenCorrectTotalPriceReturned() {

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(ADULT_PASSENGER);
        passengers.add(CHILD_PASSENGER);

        List<LuggageItem> allLuggage = new ArrayList<>();
        allLuggage.add(ADULT_PASSENGER_LUGGAGE_1);
        allLuggage.add(ADULT_PASSENGER_LUGGAGE_2);
        allLuggage.add(CHILD_PASSENGER_LUGGAGE);

        Ticket actualTicket = priceService.calculateTicketPrice(passengers, allLuggage, BASE_PRICE, VAT_TAX_RATE_AS_DECIMAL);

        assertEquals(CORRECT_TICKET_TOTAL_PRICE, actualTicket.getTotalPrice());
        Price actualAdultPassengerTicketPrice = actualTicket.getPriceComponents().get(0);
        assertEquals(ADULT_TICKET_PRICE.getBasePrice(), actualAdultPassengerTicketPrice.getBasePrice());
        assertEquals(ADULT_TICKET_PRICE.getLuggagePrice(), actualAdultPassengerTicketPrice.getLuggagePrice());
        Price actualChildPassengerTicketPrice = actualTicket.getPriceComponents().get(1);
        assertEquals(CHILD_TICKET_PRICE.getBasePrice(), actualChildPassengerTicketPrice.getBasePrice());
        assertEquals(CHILD_TICKET_PRICE.getLuggagePrice(), actualChildPassengerTicketPrice.getLuggagePrice());
    }
}
