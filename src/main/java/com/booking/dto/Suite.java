package com.booking.dto;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Qualifier;

public interface Suite {
    public default String getName(){
        return this.getClass().getAnnotation(Qualifier.class).value();
    };
    public BigDecimal getPricePerDay();
}
