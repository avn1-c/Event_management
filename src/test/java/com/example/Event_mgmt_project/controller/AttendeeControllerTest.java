package com.example.Event_mgmt_project.controller;

import com.example.Event_mgmt_project.model.Attendees;
import com.example.Event_mgmt_project.service.AttendeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static java.lang.reflect.Array.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;


@WebMvcTest(controllers = AttendeeController.class)
class AttendeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendeeService attendeeService;

    @InjectMocks
    private AttendeeController attendeeController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void addAttendee_ShouldReturnAttendee() throws Exception {
        Attendees attendee = new Attendees(1L,"b0317024@airtel.com", "Avni C.", 9399652327L);
        when(attendeeService.saveAttendee(any(Attendees.class))).thenReturn(attendee);

        RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/addAttendee")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(attendee))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result=mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response= result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    void getAllAttendees_ShouldReturnAttendeesList() throws Exception {
        Attendees attendee1 = new Attendees(1L, "b0317024@airtel.com", "Avni C.", 9399652327L);
        Attendees attendee2 = new Attendees(952L, "anushka2001@gmail.com", "Anushka Shrivastava", 9399652328L);
        when(attendeeService.find()).thenReturn(Arrays.asList(attendee1, attendee2));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAllAttendees")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(outputInJson.contains("Avni C."));
        assertTrue(outputInJson.contains("Anushka Shrivastava"));
    }

    @Test
    void getAttendeeById_ShouldReturnAttendee() throws Exception {
        Long id = 1L;
        Attendees attendee = new Attendees(id, "b0317024@airtel.com", "Avni C.", 9399652327L);
        when(attendeeService.findAttendeeById(id)).thenReturn(Optional.of(attendee));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAttendeeById/{id}", id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(outputInJson.contains("Avni C."));
    }

    @Test
    void updateAttendeeById_ShouldReturnUpdatedAttendee() throws Exception {
        Long id = 1L;
        Attendees updatedAttendee = new Attendees(id, "b0317024@airtel.com", "Avni Updated", 9399652327L);
        when(attendeeService.updateAttendeeById(eq(id), any(Attendees.class))).thenReturn(updatedAttendee);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateAttendeeById/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAttendee))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(objectMapper.writeValueAsString(updatedAttendee), outputInJson);
    }


    @Test
    void deleteAttendeeById_ShouldReturnNoContent() throws Exception {
        Long id = 1L;
        doNothing().when(attendeeService).deleteAttendeeById(id);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteAttendeeById/{id}", id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}