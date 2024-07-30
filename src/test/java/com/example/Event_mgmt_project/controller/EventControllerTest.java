package com.example.Event_mgmt_project.controller;

import com.example.Event_mgmt_project.model.Event;
import com.example.Event_mgmt_project.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = EventController.class)
class EventControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventService eventService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void addEvent_ShouldReturnEvent() throws Exception {
        Event event = new Event(1L, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");
        when(eventService.saveEvent(any(Event.class))).thenReturn(event);

        RequestBuilder requestBuilder= MockMvcRequestBuilders.post("/addEvent")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result=mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response= result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getAllEvents_ShouldReturnEventsList() throws Exception {
        Event event1 = new Event(1L, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");
        Event event2 = new Event(952L, "24-07-25", "COM YTL Meet", "MS Teams", "Project Meeting");
        when(eventService.findAllEvents()).thenReturn(Arrays.asList(event1, event2));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAllEvents")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(outputInJson.contains("Meeting"));
        assertTrue(outputInJson.contains("COM YTL Meet"));
    }

    @Test
    void getEventById_ShouldReturnEvent() throws Exception {
        Long id = 1L;
        Event event = new Event(id, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");
        when(eventService.findEventById(id)).thenReturn(Optional.of(event));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getEventById/{id}", id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(outputInJson.contains("Meeting"));
    }

//    @Test
//    void getEventById_ShouldReturnNotFound() throws Exception {
//        Long id = 1L;
//        when(eventService.findEventById(id)).thenReturn(Optional.empty());
//
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getEventById/{id}", id)
//                .accept(MediaType.APPLICATION_JSON);
//
//        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = result.getResponse();
//
//        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
//    }

    @Test
    void getEventByName_ShouldReturnEvent() throws Exception {
        String name = "Meeting";
        Event event = new Event(1L, "23-07-24", name, "UG Floor", "Wynk Meet");
        when(eventService.findEventByName(name)).thenReturn(Optional.of(event));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getEventByName/{name}", name)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(outputInJson.contains(name));
    }

    @Test
    void updateEventById_ShouldReturnUpdatedEvent() throws Exception {
        Long id = 1L;
        Event updatedEvent = new Event(id, "23-07-25", "Updated Meeting", "1st Floor", "Wynk Meet Updated");
        when(eventService.updateEventById(eq(id), any(Event.class))).thenReturn(updatedEvent);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateEventById/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEvent))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(objectMapper.writeValueAsString(updatedEvent), outputInJson);
    }

    @Test
    void deleteEventById_ShouldReturnNoContent() throws Exception {
        Long id = 1L;
        doNothing().when(eventService).deleteEventById(id);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteEventById/{id}", id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void assignAttendeeToEvent_ShouldReturnEvent() throws Exception {
        Long eventId = 1L;
        Long attendeeId = 1L;
        Event event = new Event(eventId, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");
        when(eventService.assignAttendeeToEvent(eq(eventId), eq(attendeeId))).thenReturn(event);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/{eventId}/attendee/{attendeeId}", eventId, attendeeId)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        String outputInJson = response.getContentAsString();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(outputInJson.contains("Meeting"));
    }
}