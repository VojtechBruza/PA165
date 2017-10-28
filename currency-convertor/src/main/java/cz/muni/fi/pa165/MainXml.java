package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Vojta
 */

public class MainXml {

    public static void main(String ... args) {

        //noSpring();
        springXmlContext();
//        springJavaConfigContext();
    }

    private static void noSpring() {
        ExchangeRateTable exchangeRateTable = new ExchangeRateTableImpl();
        CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    private static void springXmlContext() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);
        System.out.println("XML - EUR to CZK test: "+currencyConvertor.convert(Currency.getInstance("EUR"),Currency.getInstance("CZK"),new BigDecimal(1)));
    }

}

