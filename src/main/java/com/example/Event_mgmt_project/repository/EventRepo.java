package com.example.Event_mgmt_project.repository;

import com.example.Event_mgmt_project.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepo extends JpaRepository<Event, Long> {

    //native query, parameterized
    @Query(value = "SELECT * FROM event WHERE name = :name", nativeQuery = true)
    List<Event> findByName(@Param("name") String name);
}
