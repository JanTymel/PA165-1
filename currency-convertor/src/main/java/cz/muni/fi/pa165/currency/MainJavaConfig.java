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
public class MainJavaConfig {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringJavaConfig.class);
        
        CurrencyConvertor currencyConvertor = applicationContext.getBean("currencyConvertor", CurrencyConvertor.class);
        System.out.println(currencyConvertor.convert(EUR, CZK, BigDecimal.ONE));
    }
}
