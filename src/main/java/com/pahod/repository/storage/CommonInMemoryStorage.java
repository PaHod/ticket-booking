package com.pahod.repository.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


@Getter
@Component
@ToString
public class CommonInMemoryStorage {



    private JsonNode jsonTree;

    private ObjectMapper jsonMapper;

    @Value("${storage.initial.data.file}")
    private String filePath;

    Map<Integer, Event> events = new HashMap<>();

    Map<Integer, Ticket> tickets = new HashMap<>();

    Map<Integer, User> users = new HashMap<>();

    Map<Class<?>, Integer> lastIdForClass = new HashMap<>();

    public int generateNextIdForClass(Class<?> className) {
        Integer lastId = lastIdForClass.get(className);
        int nextId = lastId == null ? 0 : lastId + 1;
        lastIdForClass.put(className, nextId);
        return nextId;
    }

    @PostConstruct
    void init() {
        try {
            checkAndParseData();
            initMaps();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMaps() throws IOException {
        System.out.println(">>> @PostConstruct " + getClass().getSimpleName());

        for (JsonNode jsonNode : jsonTree.get("events")) {
            events.put(jsonNode.get("id").intValue(), jsonMapper.treeToValue(jsonNode, Event.class));
        }
        for (JsonNode jsonNode : jsonTree.get("tickets")) {
            tickets.put(jsonNode.get("id").intValue(), jsonMapper.treeToValue(jsonNode, Ticket.class));
        }
        for (JsonNode jsonNode : jsonTree.get("users")) {
            users.put(jsonNode.get("id").intValue(), jsonMapper.treeToValue(jsonNode, User.class));
        }

        initLastId();
    }

    private void initLastId() {
        events.keySet().forEach(id -> lastIdForClass.put(Event.class, getMax(id, lastIdForClass.get(Event.class))));
        tickets.keySet().forEach(id -> lastIdForClass.put(Ticket.class, getMax(id, lastIdForClass.get(Ticket.class))));
        users.keySet().forEach(id -> lastIdForClass.put(User.class, getMax(id, lastIdForClass.get(User.class))));
    }

    private int getMax(Integer id, Integer lastId) {
        return Math.max(id, (lastId == null ? 0 : lastId));
    }

    private void checkAndParseData() {
        if (jsonTree == null) {
            try {
                String json = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
                jsonMapper = new ObjectMapper();
                jsonTree = jsonMapper.readTree(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
