package com.pahod.repository.storage;

import com.pahod.model.Event;
import com.pahod.model.Ticket;
import com.pahod.model.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Getter
@Component
@ToString
public class CommonInMemoryStorage {

    @AutoInitWithDemoData
    Map<Integer, Event> events = new HashMap<>();

    @AutoInitWithDemoData
    Map<Integer, Ticket> tickets = new HashMap<>();

    @AutoInitWithDemoData
    Map<Integer, User> users = new HashMap<>();

    Map<Class<?>, Integer> lastIdForClass = new HashMap<>();

    public int generateNextIdForClass(Class<?> className) {
        Integer lastId = lastIdForClass.get(className);
        int nextId = lastId == null ? 0 : lastId + 1;
        lastIdForClass.put(className, nextId);
        return nextId;
    }

    // TODO: try to use @PostConstruct
//    @PostConstruct
//    void init() {
//
//    }
}
