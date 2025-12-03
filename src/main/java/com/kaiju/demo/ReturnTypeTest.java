package com.kaiju.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaiju.model.Person;
import com.kaiju.enums.Priority;
import com.kaiju.service.PersonExtractor;
import com.kaiju.service.PriorityAnalyzer;
import com.kaiju.service.SentimentAnalyzer;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;

public class ReturnTypeTest {

    private static OpenAiChatModel getChatModel() {
        return OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
    }

    private static OpenAiChatModel getJsonChatModel() {
        return OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
                .strictJsonSchema(true)
                .build();
    }

    private static void returnBooltest() {
        SentimentAnalyzer sentimentAnalyzer = AiServices.create(SentimentAnalyzer.class, getChatModel());
        boolean positive = sentimentAnalyzer.isPositive("It's wonderful!");
        System.out.println(positive);
    }

    private static void returnPriorityTest() {
        PriorityAnalyzer priorityAnalyzer = AiServices.create(PriorityAnalyzer.class, getChatModel());

        Priority priority = priorityAnalyzer.analyzePriority(
                "The main payment gateway is down, and customers cannot process transactions.");
        System.out.println(priority);
    }

    private static void returnEntityTest(){
        PersonExtractor personExtractor = AiServices.create(PersonExtractor.class, getJsonChatModel());

        String text = """
            In 1968, amidst the fading echoes of Independence Day,
            a child named John arrived under the calm evening sky.
            This newborn, bearing the surname Doe, marked the start of a new journey.
            He was welcomed into the world at 345 Whispering Pines Avenue
            a quaint street nestled in the heart of Springfield
            an abode that echoed with the gentle hum of suburban dreams and aspirations.
            """;
        Person person = personExtractor.extractPersonFrom(text);
        System.out.println(person);
        // Person { firstName = "John", lastName = "Doe", birthDate = 1968-07-04, address = Address { ... } }
    }

    public static void main(String[] args) {
//        returnBooltest();
//        returnPriorityTest();
//        returnEntityTest();
    }
}
