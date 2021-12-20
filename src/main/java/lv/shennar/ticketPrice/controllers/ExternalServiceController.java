package lv.shennar.ticketPrice.controllers;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${basePriceUrl}")
    private String basePriceUrl;

    @Value("${vatTaxUrl}")
    private String vatTaxUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public BigDecimal getBasePrice(final String destination) throws URISyntaxException {

        URI uri = new URI(basePriceUrl);

        HttpEntity<String> requestEntity = new HttpEntity<>(destination, null);

        ResponseEntity<BigDecimal> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, BigDecimal.class);
        return result.getBody();
    }

    public BigDecimal getVatTaxRateForDate(final DateTime actualDateForTax) throws URISyntaxException {
        URI uri = new URI(basePriceUrl);

        HttpEntity<DateTime> requestEntity = new HttpEntity<>(actualDateForTax, null);

        ResponseEntity<BigDecimal> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, BigDecimal.class);
        return result.getBody();
    }
}
