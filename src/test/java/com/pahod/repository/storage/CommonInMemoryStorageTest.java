package com.pahod.repository.storage;

import com.pahod.model.Event;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonInMemoryStorageTest {

    @Test
    void generateNextIdForClass() {
        //given
        CommonInMemoryStorage storage = new CommonInMemoryStorage();

        long lastId = 15;
        Class<Event> className = Event.class;

        Map<Class<?>, Long> lastIdMap = storage.getLastIdForClass();
        lastIdMap.put(className, lastId);

        //when
        long generatedId = storage.generateNextIdForClass(className);

        //then
        assertEquals(generatedId, lastId + 1);
        assertEquals(lastIdMap.get(className), generatedId);
    }
}