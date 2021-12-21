package lv.shennar.ticketPrice.controllers;

import lv.shennar.ticketPrice.domain.LuggageItem;
import lv.shennar.ticketPrice.domain.Passenger;
import lv.shennar.ticketPrice.domain.Ticket;
import lv.shennar.ticketPrice.services.PriceService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping( "/")
public class TicketPriceControllerImpl implements TicketPriceController {

    private final PriceService priceService;
    private final ExternalServiceController externalServiceController;

    @Autowired
    public TicketPriceControllerImpl(PriceService priceService, ExternalServiceController externalServiceController) {
        this.priceService = priceService;
        this.externalServiceController = externalServiceController;
    }

    @Override
    @PostMapping(value = {"/tickets"}, consumes = {"application/json"}, produces = {"application/json"})
    @ResponseBody
    public Ticket getTickets(final String destination,
                             final DateTime purchaseDate,
                             final List<Passenger> passengers,
                             final List<LuggageItem> allLuggage) {

        BigDecimal basePrice;
        try {
            basePrice = externalServiceController.getBasePrice(destination);
        } catch (URISyntaxException e) {
            System.out.println("Error getting base prise for destination: " + destination + ", check service URL.");
            return null;
        }

        BigDecimal vatTaxRateAsDecimal;
        try {
            vatTaxRateAsDecimal = externalServiceController.getVatTaxRateForDate(purchaseDate).divide(new BigDecimal("100"), RoundingMode.HALF_UP);
        } catch (URISyntaxException e) {
            System.out.println("Error getting VAT tax rate, check service URL.");
            return null;
        }

        return priceService.calculateTicketPrice(passengers, allLuggage, basePrice, vatTaxRateAsDecimal);
    }
}
