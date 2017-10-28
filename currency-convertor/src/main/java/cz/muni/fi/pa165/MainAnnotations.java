package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Vojtech Bruza
 */
public class MainAnnotations {

    public static void main(String ... args) {
        springAnnotationContext();
    }

    private static void springAnnotationContext() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("cz.muni.fi.pa165");
        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);
        System.out.println("Annotations - EUR to CZK test: "+currencyConvertor.convert(Currency.getInstance("EUR"),Currency.getInstance("CZK"),new BigDecimal(1)));
    }
}
