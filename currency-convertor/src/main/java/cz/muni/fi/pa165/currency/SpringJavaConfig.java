/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.currency;

import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 *
 * @author xtymel
 */
@Configuration
@ComponentScan("cz.muni.fi.pa165.currency")
@EnableAspectJAutoProxy
public class SpringJavaConfig {
    
    @Inject
    private ExchangeRateTable exchangeRateTable;

    @Bean
    public CurrencyConvertor currencyConvertor() {
        System.out.println("#### ##### #####");
        return new CurrencyConvertorImpl(exchangeRateTable);
    }
}
