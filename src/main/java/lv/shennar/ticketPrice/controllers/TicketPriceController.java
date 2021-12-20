package lv.shennar.ticketPrice.controllers;

import lv.shennar.ticketPrice.domain.LuggageItem;
import lv.shennar.ticketPrice.domain.Passenger;
import lv.shennar.ticketPrice.domain.Ticket;
import org.joda.time.DateTime;

import java.util.List;

public interface TicketPriceController {

    Ticket getTickets(String destination,
                      DateTime purchaseDate,
                      List<Passenger> passengers,
                      List<LuggageItem> allLuggage);
}
