package com.example.Event_mgmt_project.service;

import com.example.Event_mgmt_project.model.Event;
import com.example.Event_mgmt_project.repository.EventRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
@SpringBootTest
class EventServiceTest {


    @Mock
    private EventRepo eventRepo;
    @InjectMocks
    private EventService eventService;


//    @BeforeEach
//    void setUp() {
//        Event event=new Event(1L,"23-07-24","Meeting","UG Floor","Wynk Meet");
//        Mockito.when(eventRepo.findById(1L)).thenReturn(Optional.of(event));
//    }

    @Test
    void testSaveEvent() {
        // Given
        Event event = new Event(null, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");
        Event savedEvent = new Event(1L, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");

        Mockito.when(eventRepo.save(event)).thenReturn(savedEvent);

        // When
        Event result = eventService.saveEvent(event);

        // Then
        assertEquals(savedEvent.getId(), result.getId());
        assertEquals(savedEvent.getName(), result.getName());
        verify(eventRepo).save(event);
    }

    @Test
    void testFindAllEvents() {
        //when
        eventService.findAllEvents();
        //then
        verify(eventRepo).findAll();
    }

    @Test
    void testFindEventById() {
        // Given
        Event event = new Event(1L, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");
        Mockito.when(eventRepo.findById(1L)).thenReturn(Optional.of(event));

        Long id = 1L;

        // When
        Optional<Event> result = eventService.findEventById(id);

        // Then
        assertTrue(result.isPresent(), "Event should be present");
        assertEquals(id, result.get().getId(), "Event ID should match");
    }


    @Test
    void updateEventById() {
        // Given
        Event existingEvent = new Event(1L, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");
        Event updatedEvent = new Event(1L, "23-07-25", "Updated Meeting", "first Floor", "Wynk Meet Updated");

        Mockito.when(eventRepo.findById(1L)).thenReturn(Optional.of(existingEvent));
        Mockito.when(eventRepo.save(updatedEvent)).thenReturn(updatedEvent);

        // When
        Event result = eventService.updateEventById(1L, updatedEvent);

        // Then
        assertEquals(updatedEvent.getName(), result.getName());
        assertEquals(updatedEvent.getLocation(), result.getLocation());
        verify(eventRepo).save(updatedEvent);
    }


    @Test
    void deleteEventById() {
        // Given
        Long id = 1L;
        Mockito.when(eventRepo.existsById(id)).thenReturn(true);

        // When
        eventService.deleteEventById(id);

        // Then
        verify(eventRepo).deleteById(id);
    }


    @Test
    @Disabled
    void assignAttendeeToEvent() {
    }

    @Test
    void testFindEventByName() {
        // Given
        String name = "Wynk Meet";
        Event event = new Event(1L, "23-07-24", "Meeting", "UG Floor", "Wynk Meet");

        Mockito.when(eventRepo.findByName(name)).thenReturn(Collections.singletonList(event));

        // When
        Optional<Event> result = eventService.findEventByName(name);

        // Then
        assertEquals(name, result.get().getName());
        verify(eventRepo).findByName(name);
    }

}