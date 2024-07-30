package com.example.Event_mgmt_project.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="Attendees")
public class Attendees {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private long num;

    @ManyToMany(mappedBy = "attendeeSet", cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "event_attendees",
//            joinColumns = @JoinColumn(name = "attendee_id"),
//            inverseJoinColumns = @JoinColumn(name = "event_id")
//    )
    @JsonBackReference
    private Set<Event> EventSet = new HashSet<>();

    public Attendees(Long id, String email, String name, long num) {
        this.id=id;
        this.email = email;
        this.name = name;
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Attendees() {

    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Attendees{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", num=" + num +
                '}';
    }

    public Set<Event> getEventSet() {
        return EventSet;
    }

    public void setEventSet(Set<Event> eventSet) {
        EventSet = eventSet;
    }
}
