package com.kaiju.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Person {
    // you can add an optional description to help an LLM have a better understanding
    @Description("first name of a person")
    String firstName;
    String lastName;
    LocalDate birthDate;
    Address address;
}
