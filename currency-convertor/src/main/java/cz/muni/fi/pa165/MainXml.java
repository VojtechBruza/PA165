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
        //springAnnotationContext();
//        springJavaConfigContext();
    }

    private static void noSpring() {
        ExchangeRateTable exchangeRateTable = new ExchangeRateTableImpl();
        CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    private static void springXmlContext() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);
        System.err.println("EUR to CZK test: "+currencyConvertor.convert(Currency.getInstance("EUR"),Currency.getInstance("CZK"),new BigDecimal(1)));
    }

    private static void springAnnotationContext() {
        System.err.println("Not supported yet");
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("cz.muni.fi.pa165");
//        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);
    }

    private static void springJavaConfigContext() {
        System.err.println("Not supported yet");
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringJavaConfig.class);
//        CurrencyConvertor currencyConvertor = applicationContext.getBean("currencyConvertor", CurrencyConvertor.class);
    }

}

