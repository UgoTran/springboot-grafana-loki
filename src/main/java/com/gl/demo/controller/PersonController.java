package com.gl.demo.controller;

import com.github.loki4j.slf4j.marker.LabelMarker;
import com.gl.demo.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/persons")
@Slf4j
public class PersonController {
    private final List<Person> persons = new ArrayList<>();

    @GetMapping
    public List<Person> findAll() {
        log.info(marker(persons.size()), "get {} person", persons.size());
        return persons;
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable("id") Long id) {
        Person p = persons.stream().filter(it -> it.getId().equals(id))
                .findFirst()
                .orElse(null);
        log.info(marker(id), "Person successfully found at {} @ {}", LocalTime.now(), p);
        return p;
    }

    @GetMapping("/name/{firstName}/{lastName}")
    public List<Person> findByName(@PathVariable("firstName") String firstName,
                                   @PathVariable("lastName") String lastName) {
        return persons.stream()
                .filter(it -> it.getFirstName().equals(firstName) && it.getLastName().equals(lastName))
                .toList();
    }

    @PostMapping
    public Person add(@RequestBody Person p) {
        p.setId((long) (persons.size() + 1));
        log.info(marker(p.getId()), "New person {}, {} successfully added {}",
                p.getId(), p.getFirstName(), LocalTime.now());
        persons.add(p);
        return p;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        Person p = persons.stream().filter(it -> it.getId().equals(id)).findFirst().orElseThrow();
        persons.remove(p);
        log.info(marker(p.getId()), "Person successfully removed {}", p.getId());
    }

    @PutMapping
    public void update(@RequestBody Person p) {
        Person person = persons.stream()
                .filter(it -> it.getId().equals(p.getId()))
                .findFirst()
                .orElseThrow();
        persons.set(persons.indexOf(person), p);
        log.info(marker(p.getId()), "Person successfully updated");
    }

    LabelMarker marker(Object value) {
        return LabelMarker.of("personId", () -> String.valueOf(value));
    }

}
