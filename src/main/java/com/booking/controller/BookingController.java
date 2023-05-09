package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booking.dto.Booking;
import com.booking.service.BookingService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/booking")
public class BookingController {

    @Autowired
    private BookingService service;

    @GetMapping(value = "/{cpfCustomer}")
    public ResponseEntity<Booking> findBooking(@PathVariable(value = "cpfCustomer") String cpfCustomer) {

       Booking bookingResponse = this.service.findBooking(cpfCustomer);

       return new ResponseEntity<>(bookingResponse, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Booking> checkIn(@RequestBody @Valid Booking booking){
        
        Booking bookingResponse = this.service.checkIn(booking);
       return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{cpfCustomer}")
    public ResponseEntity<Booking> checkOut(@PathVariable(value = "cpfCustomer") String cpfCustomer) {

        Booking bookingResponse = this.service.checkOut(cpfCustomer);
        return new ResponseEntity<>(bookingResponse, HttpStatus.ACCEPTED);
    }




}
