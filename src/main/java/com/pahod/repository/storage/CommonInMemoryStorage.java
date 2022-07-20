package com.pahod.repository.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


@Getter
@Component
@ToString
public class CommonInMemoryStorage {

    private static final Logger logger = LoggerFactory.getLogger(CommonInMemoryStorage.class);

    private JsonNode jsonTree;

    private ObjectMapper jsonMapper;

    @Value("${storage.initial.data.file}")
    private String filePath;

    Map<Long, Event> events = new HashMap<>();

    Map<Long, Ticket> tickets = new HashMap<>();

    Map<Long, User> users = new HashMap<>();

    Map<Class<?>, Long> lastIdForClass = new HashMap<>();

    public long generateNextIdForClass(Class<?> className) {
        Long lastId = lastIdForClass.get(className);
        long nextId = lastId == null ? 0 : lastId + 1;
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
        logger.info(">>> @PostConstruct " + getClass().getSimpleName());

        for (JsonNode jsonNode : jsonTree.get("events")) {
            events.put(jsonNode.get("id").longValue(), jsonMapper.treeToValue(jsonNode, Event.class));
        }
        for (JsonNode jsonNode : jsonTree.get("tickets")) {
            tickets.put(jsonNode.get("id").longValue(), jsonMapper.treeToValue(jsonNode, Ticket.class));
        }
        for (JsonNode jsonNode : jsonTree.get("users")) {
            users.put(jsonNode.get("id").longValue(), jsonMapper.treeToValue(jsonNode, User.class));
        }

        initLastId();
    }

    private void initLastId() {
        events.keySet().forEach(id -> lastIdForClass.put(Event.class, getMax(id, lastIdForClass.get(Event.class))));
        tickets.keySet().forEach(id -> lastIdForClass.put(Ticket.class, getMax(id, lastIdForClass.get(Ticket.class))));
        users.keySet().forEach(id -> lastIdForClass.put(User.class, getMax(id, lastIdForClass.get(User.class))));
    }

    private long getMax(long id, Long lastId) {
        return Math.max(id, (lastId == null ? 0 : lastId));
    }

    private void checkAndParseData() {
        if (jsonTree == null) {
            try {
                String json = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
                jsonMapper = new ObjectMapper();
                jsonMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
                jsonTree = jsonMapper.readTree(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
