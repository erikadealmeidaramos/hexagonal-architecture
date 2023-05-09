package com.booking.dto;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("presidential")
public class PresidentialSuite implements Suite{

    @Override
    public BigDecimal getPricePerDay() {
        return BigDecimal.valueOf(90);
    }
    
}
