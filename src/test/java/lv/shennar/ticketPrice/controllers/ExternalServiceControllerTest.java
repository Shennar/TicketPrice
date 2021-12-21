package lv.shennar.ticketPrice.controllers;

import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExternalServiceControllerTest {

    private static final BigDecimal BASE_PRICE = BigDecimal.TEN;
    private static final BigDecimal VAT_TAX_RATE = new BigDecimal("21");

    private final String DESTINATION = "Vilnius, Lithuania";

    private final RestTemplate restTemplate = mock(RestTemplate.class);

    private ExternalServiceController externalServiceController;

    @BeforeEach
    void setUp() {
        externalServiceController = new ExternalServiceController(restTemplate);
    }

    @Test
    void getBasePrice() throws URISyntaxException {
        ResponseEntity<BigDecimal> expectedResponse = new ResponseEntity<>(BASE_PRICE, HttpStatus.OK);
        when(restTemplate.exchange(any(), any(), any(), eq(BigDecimal.class))).thenReturn(expectedResponse);

        BigDecimal actualBasePrice = externalServiceController.getBasePrice(DESTINATION);
        assertEquals(BASE_PRICE, actualBasePrice);
    }

    @Test
    void getVatTaxRateForDate() throws URISyntaxException {
        ResponseEntity<BigDecimal> expectedResponse = new ResponseEntity<>(VAT_TAX_RATE, HttpStatus.OK);
        when(restTemplate.exchange(any(), any(), any(), eq(BigDecimal.class))).thenReturn(expectedResponse);

        BigDecimal actualBasePrice = externalServiceController.getVatTaxRateForDate(new DateTime());
        assertEquals(VAT_TAX_RATE, actualBasePrice);
    }
}
