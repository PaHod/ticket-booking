package com.pahod.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserTest {
    @Test
    void updateFrom_all_field_updated() {
        //given
       long id = 55;
        User userTemplate = new User(id, "Peter", "Peter@mail.test");
        User userToUpdate = new User();
        userToUpdate.setId(id);

        //when
        userToUpdate.updateFrom(userTemplate);

        //then
        assertEquals(userToUpdate, userTemplate);
    }

    @Test
    void updateFrom_all_id_not_updated() {
        //given
        User userTemplate = new User(55, "Peter", "Peter@mail.test");
        User userToUpdate = new User();

        //when
        userToUpdate.updateFrom(userTemplate);

        //then
        assertNotEquals(userToUpdate.getId(), userTemplate.getId());
    }
}