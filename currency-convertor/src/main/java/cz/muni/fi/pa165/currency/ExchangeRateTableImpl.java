/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.currency;

import java.math.BigDecimal;
import java.util.Currency;
import javax.inject.Named;

/**
 *
 * @author xtymel
 */
@Named
public class ExchangeRateTableImpl implements ExchangeRateTable {

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency.equals(MainXml.EUR) && sourceCurrency.equals(MainXml.EUR)) {
            return new BigDecimal(27);
        }

        return null;
    }
}
