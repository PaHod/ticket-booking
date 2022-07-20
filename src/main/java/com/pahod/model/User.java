package com.pahod.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;
    private String name;
    private String email;

    public void updateFrom(User userToSave) {
        this.setName(userToSave.getName());
        this.setEmail(userToSave.getEmail());
    }
}
