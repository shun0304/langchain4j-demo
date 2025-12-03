package com.kaiju.service;

import com.kaiju.model.Person;
import dev.langchain4j.service.UserMessage;

public interface PersonExtractor {
    @UserMessage("Extract information about a person from {{it}}")
    Person extractPersonFrom(String text);
}
