package cz.fi.muni.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class) //Injecting mock
public class CurrencyConvertorImplTest {

    @Mock
    ExchangeRateTable exchangeRateTable;

    CurrencyConvertor currencyConvertor;

    Currency USD = Currency.getInstance("USD");
    Currency CZK = Currency.getInstance("CZK");

    @Before
    public void init(){
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.
        when(exchangeRateTable.getExchangeRate(USD,CZK))
                .thenReturn(new BigDecimal("22.09"));

        assertEquals(new BigDecimal("220.90"), currencyConvertor.convert(USD,CZK,new BigDecimal("10")));
        when(exchangeRateTable.getExchangeRate(USD,CZK))
                .thenReturn(new BigDecimal("0.01"));
        //border values:
        assertThat(currencyConvertor.convert(USD,CZK,new BigDecimal("100.50"))).isEqualTo(new BigDecimal("1.00"));
        assertThat(currencyConvertor.convert(USD,CZK,new BigDecimal("100.51"))).isEqualTo(new BigDecimal("1.01"));
        assertThat(currencyConvertor.convert(USD,CZK,new BigDecimal("101.50"))).isEqualTo(new BigDecimal("1.02"));
        assertThat(currencyConvertor.convert(USD,CZK,new BigDecimal("101.49"))).isEqualTo(new BigDecimal("1.01"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() {
        currencyConvertor.convert(null, CZK, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() {
        currencyConvertor.convert(CZK, null, BigDecimal.TEN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() {
        currencyConvertor.convert(CZK, USD, null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(Currency.getInstance("INR"),CZK)).thenReturn(null);
        currencyConvertor.convert(Currency.getInstance("INR"),CZK,BigDecimal.TEN);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTable.getExchangeRate(USD, CZK))
                .thenThrow(UnknownExchangeRateException.class);
        currencyConvertor.convert(USD, CZK, BigDecimal.ONE);
    }

}
