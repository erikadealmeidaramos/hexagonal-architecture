package com.booking.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.booking.dto.Booking;
import com.booking.dto.CommonSuite;
import com.booking.dto.PresidentialSuite;
import com.booking.exception.AlreadyExistsException;
import com.booking.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingService {

    public Booking findBooking(String cpfCustomer) {
        log.info("Encontrando reserva");

        Booking bookingResponse = getByCpfCustomer(cpfCustomer);

        if (bookingResponse == null)
            throw new NotFoundException("Booking with this CPF not found");

        return bookingResponse;
    }

    public Booking checkIn(Booking booking) {
        log.info("Criando reserva");

        if (getByCpfCustomer(booking.getCpfCustomer()) == null) {
            Booking bookingResponse = Booking.builder()
                    .bookingId(UUID.randomUUID())
                    .cpfCustomer(booking.getCpfCustomer())
                    .checkInDate(Calendar.getInstance())
                    .suite(booking.getSuite())
                    .build();

            return bookingResponse;

        }

        throw new AlreadyExistsException("Customer with this CPF already has an active booking");

    }

    public Booking checkOut(String cpfCustomer) {
        log.info("Fazendo checkout na reserva");

        Booking bookingResponse = getByCpfCustomer(cpfCustomer);

        if (bookingResponse == null)
            throw new NotFoundException("Booking with this CPF not found");

        bookingResponse.setCheckOutDate(Calendar.getInstance());

        return bookingResponse;
    }

    private Booking getByCpfCustomer(String cpfCustomer) {
        List<Booking> bookingResponses = this.getBookingResponses();
        Booking bookingResponse = bookingResponses.stream()
                .filter(bookingRespFilter -> Objects.equals(bookingRespFilter.getCpfCustomer(), cpfCustomer)
                        && Objects.equals(bookingRespFilter.getCheckOutDate(), null))
                .findFirst().orElse(null);

        return bookingResponse;
    }

    private List<Booking> getBookingResponses() {
        Booking bookingResponse1 = Booking.builder()
                .bookingId(UUID.randomUUID())
                .cpfCustomer("123")
                .checkInDate(Calendar.getInstance())
                .suite(new PresidentialSuite())
                .build();

        Booking bookingResponse2 = Booking.builder()
                .bookingId(UUID.randomUUID())
                .cpfCustomer("456")
                .checkInDate(Calendar.getInstance())
                .suite(new CommonSuite())
                .build();

        return Arrays.asList(bookingResponse1, bookingResponse2);
    }

}
