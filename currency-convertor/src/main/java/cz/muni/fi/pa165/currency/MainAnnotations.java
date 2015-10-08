/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.currency;

import static cz.muni.fi.pa165.currency.MainXml.CZK;
import static cz.muni.fi.pa165.currency.MainXml.EUR;
import java.math.BigDecimal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author xtymel
 */
public class MainAnnotations {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");

        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);
        System.out.println(currencyConvertor.convert(EUR, CZK, BigDecimal.ONE));

    }
}
