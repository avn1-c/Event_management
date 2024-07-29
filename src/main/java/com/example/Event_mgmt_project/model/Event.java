package com.example.Event_mgmt_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String eventdate;
    private String location;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_attendees",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "attendee_id")
    )
    @JsonBackReference
    private Set<Attendees> attendeeSet = new HashSet<>();

    //constructor
    public Event(Long id,String eventdate, String description, String location, String name) {
        this.id=id;
        this.eventdate = eventdate;
        this.description = description;
        this.location = location;
        this.name = name;
    }
    //auto constructor
    public Event() {

    }

    //getters and setters

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //tostring
    @Override
    public String toString() {
        return "Event{" +
                "eventdate='" + eventdate + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Set<Attendees> getAttendeeSet() {
        return attendeeSet;
    }

    public void setAttendeeSet(Set<Attendees> attendeeSet) {
        this.attendeeSet = attendeeSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
