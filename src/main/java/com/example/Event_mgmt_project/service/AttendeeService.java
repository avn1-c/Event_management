package com.example.Event_mgmt_project.service;

import com.example.Event_mgmt_project.exceptionHandling.AttendeeNotFoundException;
import com.example.Event_mgmt_project.model.Attendees;
import com.example.Event_mgmt_project.repository.AttendeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendeeService {

    @Autowired
    private AttendeeRepo attendeeRepo;

    public Attendees saveAttendee(Attendees attendee){
        return attendeeRepo.save(attendee);
    }
    public List<Attendees> find(){
        return attendeeRepo.findAll();
    }
    public Optional<Attendees> findAttendeeById(Long id){
        return attendeeRepo.findById(id);
    }
    public Attendees updateAttendeeById(Long id, Attendees newData){
        Optional<Attendees> existingAttendee = attendeeRepo.findById(id);
        if(existingAttendee.isPresent()){
            //add method
            Attendees updatedAttendee= existingAttendee.get();
            updatedAttendee.setName(newData.getName());
            updatedAttendee.setNum(newData.getNum());
            updatedAttendee.setEmail(newData.getEmail());
            return attendeeRepo.save(updatedAttendee);
        }
        else {
            throw new AttendeeNotFoundException("Attendee not found with id: " + id);
        }
    }
    public void deleteAttendeeById(Long id){
        attendeeRepo.deleteById(id);
    }
}
