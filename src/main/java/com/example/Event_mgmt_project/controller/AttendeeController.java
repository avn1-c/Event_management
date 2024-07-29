package com.example.Event_mgmt_project.controller;

import com.example.Event_mgmt_project.exceptionHandling.AttendeeNotFoundException;
import com.example.Event_mgmt_project.model.Attendees;
import com.example.Event_mgmt_project.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AttendeeController {
    @Autowired
    private AttendeeService attendeeService;

    @PostMapping("/addAttendee")
    public Attendees addAttendee(@RequestBody Attendees attendee){
        return attendeeService.saveAttendee(attendee);
    }

    @GetMapping("/getAllAttendees")
    public List<Attendees> getAllAttendees(){
//        try{
//            List<Attendees> attendeesList = new ArrayList<>();
//            attendeeService.find().forEach(attendeesList::add);
//
//            if(attendeesList.isEmpty()){
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return attendeeService.find();
    }

    @GetMapping("/getAttendeeById/{id}")
    public Optional<Attendees> getAttendeeById(@PathVariable Long id){
        return attendeeService.findAttendeeById(id);
    }

    @PostMapping("/updateAttendeeById/{id}")
    public Attendees updateAttendeeById(@PathVariable Long id, @RequestBody Attendees newData) throws AttendeeNotFoundException {
        return attendeeService.updateAttendeeById(id,newData);
    }

    @DeleteMapping("/deleteAttendeeById/{id}")
    public void deleteAttendeeById(@PathVariable Long id){
        attendeeService.deleteAttendeeById(id);
    }
}
