package com.example.Event_mgmt_project.controller;

import com.example.Event_mgmt_project.exceptionHandling.AttendeeNotFoundException;
import com.example.Event_mgmt_project.exceptionHandling.EventNotFoundException;
import com.example.Event_mgmt_project.model.Event;
import com.example.Event_mgmt_project.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @PostMapping("/addEvent")
    public Event addEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }

    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents(){
        return eventService.findAllEvents();
    }

    @GetMapping("/getEventById/{id}")
    public Optional<Event> getEventById(@PathVariable Long id){
        return eventService.findEventById(id);
    }

    @GetMapping("/getEventByName/{name}")
    public ResponseEntity<Event> getEventByName(@PathVariable String name) {
        Optional<Event> eventOpt = eventService.findEventByName(name);
        return eventOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/updateEventById/{id}")
    public Event updateEventById(@PathVariable Long id, @RequestBody Event newEventData) throws EventNotFoundException {
        return eventService.updateEventById(id, newEventData);
    }

    @DeleteMapping("/deleteEventById/{id}")
    public void deleteEventById(@PathVariable Long id){
        eventService.deleteEventById(id);
    }

    @PutMapping("/{eventId}/attendee/{attendeeId}")
    public Event assignAttendeeToEvent(@PathVariable Long eventId, @PathVariable Long attendeeId){
        return eventService.assignAttendeeToEvent(eventId, attendeeId);
    }
}
