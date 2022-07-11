package com.pahod.repository.storage;

import com.pahod.model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonInMemoryStorageTest {

    @Test
    void generateNextIdForClass() {
        //given
        CommonInMemoryStorage storage = new CommonInMemoryStorage();

        int lastId = 15;
        Class<Event> className = Event.class;

        Map<Class<?>, Integer> lastIdMap = storage.getLastIdForClass();
        lastIdMap.put(className, lastId);

        //when
        int generatedId = storage.generateNextIdForClass(className);

        //then
        assertEquals(generatedId, lastId + 1);
        assertEquals(lastIdMap.get(className), generatedId);
    }
}