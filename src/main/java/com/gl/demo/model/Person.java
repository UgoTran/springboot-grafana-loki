package com.gl.demo.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;

}
