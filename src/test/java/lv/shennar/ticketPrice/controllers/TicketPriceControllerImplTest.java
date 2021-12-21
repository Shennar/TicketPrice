package lv.shennar.ticketPrice.controllers;

import lv.shennar.ticketPrice.domain.LuggageItem;
import lv.shennar.ticketPrice.domain.Passenger;
import lv.shennar.ticketPrice.domain.Price;
import lv.shennar.ticketPrice.domain.Ticket;
import lv.shennar.ticketPrice.services.PriceService;
import lv.shennar.ticketPrice.services.PriceServiceImpl;
import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketPriceControllerImplTest {

    private static final Passenger ADULT_PASSENGER = new Passenger(true);
    private static final Passenger CHILD_PASSENGER = new Passenger(false);

    private static final LuggageItem ADULT_PASSENGER_LUGGAGE_1 = new LuggageItem(ADULT_PASSENGER.getId());
    private static final LuggageItem ADULT_PASSENGER_LUGGAGE_2 = new LuggageItem(ADULT_PASSENGER.getId());

    private static final LuggageItem CHILD_PASSENGER_LUGGAGE = new LuggageItem(CHILD_PASSENGER.getId());

    private static final BigDecimal BASE_PRICE = BigDecimal.TEN;
    private static final BigDecimal VAT_TAX_RATE_AS_DECIMAL = new BigDecimal("0.21");
    private static final BigDecimal CORRECT_TICKET_TOTAL_PRICE = new BigDecimal("29.04");
    private static final Price ADULT_TICKET_PRICE = new Price(ADULT_PASSENGER.getId(), new BigDecimal("12.10"), new BigDecimal("7.26"));
    private static final Price CHILD_TICKET_PRICE = new Price(CHILD_PASSENGER.getId(), new BigDecimal("6.05"), new BigDecimal("3.63"));

    private final PriceService priceService = mock(PriceServiceImpl.class);
    private final ExternalServiceController externalServiceController = mock(ExternalServiceController.class);

    private final List<Passenger> passengers = new ArrayList<>();
    private final List<LuggageItem> allLuggage = new ArrayList<>();
    private Ticket expectedTicket;

    private TicketPriceController ticketPriceController;

    @BeforeEach
    public void setUp() throws URISyntaxException {
        ticketPriceController = new TicketPriceControllerImpl(priceService, externalServiceController);

        List<Price> priceComponents = new ArrayList<>();
        priceComponents.add(ADULT_TICKET_PRICE);
        priceComponents.add(CHILD_TICKET_PRICE);
        expectedTicket = new Ticket(CORRECT_TICKET_TOTAL_PRICE, priceComponents);

        when(priceService.calculateTicketPrice(anyList(), anyList(), any(), any())).thenReturn(expectedTicket);
        when(externalServiceController.getBasePrice(anyString())).thenReturn(BASE_PRICE);
        when(externalServiceController.getVatTaxRateForDate(any())).thenReturn(VAT_TAX_RATE_AS_DECIMAL);

        passengers.add(ADULT_PASSENGER);
        passengers.add(CHILD_PASSENGER);

        allLuggage.add(ADULT_PASSENGER_LUGGAGE_1);
        allLuggage.add(ADULT_PASSENGER_LUGGAGE_2);
        allLuggage.add(CHILD_PASSENGER_LUGGAGE);
    }

    @Test
    void givenExternalResourcesWorkAsExpected_whenTicketPriceRequested_thenCorrectTicketReturned() {

        final Ticket actualTicket = ticketPriceController.getTickets("", new DateTime(), passengers, allLuggage);

        assertEquals(expectedTicket, actualTicket);
        final Price actualAdultPassengerPrice = actualTicket.getPriceComponents().stream()
                .filter(price -> price.getPassengerId().equals(ADULT_PASSENGER.getId()))
                .findFirst()
                .orElse(null);
        assertEquals(ADULT_TICKET_PRICE, actualAdultPassengerPrice);
        final Price actualChildPassengerPrice = actualTicket.getPriceComponents().stream()
                .filter(price -> price.getPassengerId().equals(CHILD_PASSENGER.getId()))
                .findFirst()
                .orElse(null);
        assertEquals(CHILD_TICKET_PRICE, actualChildPassengerPrice);
    }

    @Test
    void givenExternalResourcesBroken_whenTicketPriceRequested_thenNullReturned() throws URISyntaxException {

        when(externalServiceController.getVatTaxRateForDate(any())).thenThrow(URISyntaxException.class);

        assertNull(ticketPriceController.getTickets("", new DateTime(), passengers, allLuggage));
    }
}