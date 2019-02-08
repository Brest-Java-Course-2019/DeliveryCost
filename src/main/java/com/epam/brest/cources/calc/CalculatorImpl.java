package com.epam.brest.cources.calc;

import java.math.BigDecimal;

public class CalculatorImpl implements Calculator {

    @Override
    public BigDecimal calc(DataItem dataItem) {
        return calc(dataItem.getWeight(), dataItem.getDistance(), dataItem.getPricePerKg(), dataItem.getPricePerKm());
    }

    @Override
    public BigDecimal calc(BigDecimal weight, BigDecimal distance, BigDecimal pricePerKg, BigDecimal pricePerKm) {
        return weight.multiply(pricePerKg).add(distance.multiply(pricePerKm));
    }

}
