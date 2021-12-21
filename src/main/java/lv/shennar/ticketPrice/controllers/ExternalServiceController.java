package lv.shennar.ticketPrice.controllers;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class ExternalServiceController {

    // @Value("${basePriceUrl: http://www.api.bus/base-price}") - does not work - can't find why (of course, without static final)
    private static final String BASE_PRICE_URL = "http://www.api.bus/base-price";

    //@Value("${vatTaxUrl: www.api.bus/vat-tax}")
    private static final String VAT_TAX_URL = "www.api.bus/vat-tax";

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalServiceController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BigDecimal getBasePrice(final String destination) throws URISyntaxException {

        URI uri = new URI(BASE_PRICE_URL);

        HttpEntity<String> requestEntity = new HttpEntity<>(destination, null);

        ResponseEntity<BigDecimal> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, BigDecimal.class);
        return result.getBody();
    }

    public BigDecimal getVatTaxRateForDate(final DateTime actualDateForTax) throws URISyntaxException {
        URI uri = new URI(VAT_TAX_URL);

        HttpEntity<DateTime> requestEntity = new HttpEntity<>(actualDateForTax, null);

        ResponseEntity<BigDecimal> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, BigDecimal.class);
        return result.getBody();
    }
}
