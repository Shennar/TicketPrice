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
import org.mockito.Mockito;

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

    private PriceService priceService = mock(PriceServiceImpl.class);
    private ExternalServiceController externalServiceController = mock(ExternalServiceController.class);

    private TicketPriceController ticketPriceController;

    @BeforeEach
    public void setUp(){
        ticketPriceController = new TicketPriceControllerImpl(priceService, externalServiceController);
    }

    @Test
    void givenExternalResourcesWorkAsExpected_whenTicketPriceRequested_thenCorrectTicketReturned() throws URISyntaxException {

        List<Price> priceComponents = new ArrayList<>();
        priceComponents.add(ADULT_TICKET_PRICE);
        priceComponents.add(CHILD_TICKET_PRICE);
        Ticket expectedTicket = new Ticket(CORRECT_TICKET_TOTAL_PRICE, priceComponents);

        when(priceService.calculateTicketPrice(anyList(), anyList(), any(), any())).thenReturn(expectedTicket);
        when(externalServiceController.getBasePrice(anyString())).thenReturn(BASE_PRICE);
        when(externalServiceController.getVatTaxRateForDate(any())).thenReturn(VAT_TAX_RATE_AS_DECIMAL);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(ADULT_PASSENGER);
        passengers.add(CHILD_PASSENGER);

        List<LuggageItem> allLuggage = new ArrayList<>();
        allLuggage.add(ADULT_PASSENGER_LUGGAGE_1);
        allLuggage.add(ADULT_PASSENGER_LUGGAGE_2);
        allLuggage.add(CHILD_PASSENGER_LUGGAGE);

        Ticket actualTicket = ticketPriceController.getTickets("", new DateTime(), passengers, allLuggage);

        assertEquals(expectedTicket, actualTicket);
    }

    @Test
    void givenExternalResourcesBroken_whenTicketPriceRequested_thenNullReturned() throws URISyntaxException {

        List<Price> priceComponents = new ArrayList<>();
        priceComponents.add(ADULT_TICKET_PRICE);
        priceComponents.add(CHILD_TICKET_PRICE);
        Ticket expectedTicket = new Ticket(CORRECT_TICKET_TOTAL_PRICE, priceComponents);

        when(priceService.calculateTicketPrice(anyList(), anyList(), any(), any())).thenReturn(expectedTicket);
        when(externalServiceController.getBasePrice(anyString())).thenReturn(BASE_PRICE);
        when(externalServiceController.getVatTaxRateForDate(any())).thenThrow(URISyntaxException.class);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(ADULT_PASSENGER);
        passengers.add(CHILD_PASSENGER);

        List<LuggageItem> allLuggage = new ArrayList<>();
        allLuggage.add(ADULT_PASSENGER_LUGGAGE_1);
        allLuggage.add(ADULT_PASSENGER_LUGGAGE_2);
        allLuggage.add(CHILD_PASSENGER_LUGGAGE);

        assertNull(ticketPriceController.getTickets("", new DateTime(), passengers, allLuggage));
    }

    //TODO: Add REST assured tests here
}