package com.kaiju.model;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
// you can add an optional description to help an LLM have a better understanding
@Description("an address")
public class Address {
    String street;
    Integer streetNumber;
    String city;
}
