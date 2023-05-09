package com.booking;

import com.booking.config.AppConfig;
import com.booking.dto.Booking;
import com.booking.dto.PresidentialSuite;
import com.booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Import(AppConfig.class)
public class BookingControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Mock
        private BookingService bookingService;

        private List<Booking> bookingResponses;

        @BeforeEach
        void setUp() {
                this.bookingResponses = new ArrayList<>();

                Booking bookingResponse1 = Booking.builder()
                                .bookingId(UUID.randomUUID())
                                .cpfCustomer("123")
                                .checkInDate(Calendar.getInstance())
                                .suite(new PresidentialSuite())
                                .build();

                Booking bookingResponse2 = Booking.builder()
                                .bookingId(UUID.randomUUID())
                                .cpfCustomer("789")
                                .checkInDate(Calendar.getInstance())
                                .checkOutDate(Calendar.getInstance())
                                .suite(new PresidentialSuite())
                                .build();

                bookingResponses.addAll(List.of(bookingResponse1, bookingResponse2));
        }

        @Test
        public void getBookingByCpf() throws Exception {
                given(bookingService.findBooking(bookingResponses.get(0).getCpfCustomer()))
                                .willReturn(bookingResponses.get(0));

                this.mockMvc.perform(MockMvcRequestBuilders
                                .get("/booking/123")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                                .andExpect(MockMvcResultMatchers.jsonPath("$.cpfCustomer", is("123")));
        }

        @Test
        public void createBookingRecord() throws Exception {
                Booking booking = bookingResponses.get(1);

                given(bookingService.checkIn(booking))
                .willReturn(booking);

                this.mockMvc.perform(MockMvcRequestBuilders
                .post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpfCustomer", is("789")));
        }

        @Test
        public void updateBookingRecord() throws Exception {                
                given(bookingService.checkOut(bookingResponses.get(1).getCpfCustomer()))
                .willReturn(bookingResponses.get(1));

                this.mockMvc.perform(MockMvcRequestBuilders
                .put("/booking/123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpfCustomer", is("123")));
        }
}
