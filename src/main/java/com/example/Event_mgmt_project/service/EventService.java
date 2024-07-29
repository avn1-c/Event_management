package com.example.Event_mgmt_project.service;

import com.example.Event_mgmt_project.exceptionHandling.EventNotFoundException;
import com.example.Event_mgmt_project.model.Attendees;
import com.example.Event_mgmt_project.model.Event;
import com.example.Event_mgmt_project.repository.AttendeeRepo;
import com.example.Event_mgmt_project.repository.EventRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private AttendeeRepo attendeeRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public Event saveEvent(Event event){
        return eventRepo.save(event);
    }
    public List<Event> findAllEvents(){
        return eventRepo.findAll();
    }
    public Optional<Event> findEventById(Long id){
        return eventRepo.findById(id);
    }
    public Event updateEventById(Long id, Event newEventData){
        Optional<Event> existingEvent = eventRepo.findById(id);
        if(existingEvent.isPresent()){
            Event updatedEvent= existingEvent.get();
            updatedEvent.setName(newEventData.getName());
            updatedEvent.setLocation(newEventData.getLocation());
            updatedEvent.setEventdate(newEventData.getEventdate());
            updatedEvent.setDescription(newEventData.getDescription());
            return eventRepo.save(updatedEvent);
        }
        else {
            throw new EventNotFoundException("Event not found with id: " + id);
        }
    }
    public void deleteEventById(Long id){
        eventRepo.deleteById(id);
    }


    public Event assignAttendeeToEvent(Long eventId, Long attendeeId) {
        Set<Attendees> attendeesSet= null;
        Event event= eventRepo.findById(eventId).get();
        Attendees attendee = attendeeRepo.findById(attendeeId).get();
        attendeesSet = event.getAttendeeSet();
        attendeesSet.add(attendee);
        event.setAttendeeSet(attendeesSet);
        return eventRepo.save(event);

    }

    public Optional<Event> findEventByName(String name) {
        List<Event> events = eventRepo.findByName(name);
        return events.stream().findFirst();
    }
}
