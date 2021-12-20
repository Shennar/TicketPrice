package lv.shennar.ticketPrice.services;

import lv.shennar.ticketPrice.domain.LuggageItem;
import lv.shennar.ticketPrice.domain.Passenger;
import lv.shennar.ticketPrice.domain.Price;
import lv.shennar.ticketPrice.domain.Ticket;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PriceServiceImpl implements PriceService {

    private static final String RATE_FOR_CHILD = "0.50";
    private static final String RATE_FOR_LUGGAGE = "0.30";

    @Override
    public Ticket calculateTicketPrice(final List<Passenger> passengers,
                                       final List<LuggageItem> luggage,
                                       final BigDecimal basePrice,
                                       final BigDecimal vatTaxRateAsDecimal) {

        BigDecimal vatTaxMultiplier = BigDecimal.ONE.add(vatTaxRateAsDecimal);
        List<Price> priceComponents = passengers.stream().map(passenger -> {
            long itemCount = luggage.stream()
                    .filter(item -> item.getOwnerId().equals(passenger.getId()))
                    .count();
            return calculatePriceForOnePassenger(passenger, itemCount, basePrice, vatTaxMultiplier);
        }).collect(toList());
        BigDecimal totalPrice = priceComponents.stream().map(price -> price.getBasePrice().add(price.getLuggagePrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Ticket(totalPrice, priceComponents);
    }

    private Price calculatePriceForOnePassenger(final Passenger passenger,
                                                final long luggageItemCount,
                                                final BigDecimal basePrice,
                                                final BigDecimal vatTaxMultiplier) {

        BigDecimal farePriceForPassenger = basePrice.multiply(passenger.isAdult() ? BigDecimal.ONE : new BigDecimal(RATE_FOR_CHILD))
                .multiply(vatTaxMultiplier).setScale(2);
        BigDecimal luggagePrice = basePrice.multiply(BigDecimal.valueOf(luggageItemCount)).multiply(new BigDecimal(RATE_FOR_LUGGAGE))
                .multiply(vatTaxMultiplier).setScale(2);
        return new Price(farePriceForPassenger, luggagePrice);
    }
}
