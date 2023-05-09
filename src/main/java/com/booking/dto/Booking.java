package com.booking.dto;

import java.util.Calendar;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import com.booking.service.SuiteStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Booking {
    private UUID bookingId;
    @NotEmpty(message = "CPF customer may not be empty")
    private String cpfCustomer;
    private Calendar checkInDate;
    private Calendar checkOutDate;
    @NotEmpty(message = "Suite may not be empty")
    @JsonDeserialize(using = SuiteStrategy.class)
    private Suite suite;
}
