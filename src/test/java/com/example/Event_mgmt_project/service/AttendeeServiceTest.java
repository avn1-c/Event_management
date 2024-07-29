package com.example.Event_mgmt_project.service;

import com.example.Event_mgmt_project.model.Attendees;
import com.example.Event_mgmt_project.repository.AttendeeRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class AttendeeServiceTest {

    @Mock
    private AttendeeRepo attendeeRepo;
    @InjectMocks
    private AttendeeService attendeeService;


    @Test
    void testSaveAttendee() {
        //given
        Attendees attendee = new Attendees(null, "b0317024@airtel.com", "Avni C.", 9399652327L);
        Attendees savedAttendee = new Attendees(1L,"b0317024@airtel.com","Avni C.", 9399652327L);

        Mockito.when(attendeeRepo.save(attendee)).thenReturn(savedAttendee);

        //when
        Attendees result= attendeeService.saveAttendee(attendee);

        //then
        assertEquals(savedAttendee.getId(), result.getId());
        assertEquals(savedAttendee.getName(), result.getName());
        verify(attendeeRepo).save(attendee);
    }

    @Test
    void testFind() {
        //when
        attendeeService.find();
        //then
        verify(attendeeRepo).findAll();
    }

    @Test
    void testFindAttendeeById() {
        //given
        Attendees attendee= new Attendees(1L,"b0317024@airtel.com", "Avni C.", 9399652327L);
        Mockito.when(attendeeRepo.findById(1L)).thenReturn(Optional.of(attendee));

        Long id = 1L;

        //when
        Optional<Attendees> result= attendeeService.findAttendeeById(id);

        //then
        assertTrue(result.isPresent(), "Attendee should be present.");
        assertEquals(id, result.get().getId(), "Attendee ID should match.");
    }

    @Test
    void updateAttendeeById() {
    }

    @Test
    void testDeleteAttendeeById() {
        // Given
        Long id = 1L;
        Mockito.when(attendeeRepo.existsById(id)).thenReturn(true);

        // When
        attendeeService.deleteAttendeeById(id);

        // Then
        verify(attendeeRepo).deleteById(id);
    }
}