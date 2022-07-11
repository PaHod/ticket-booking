package com.pahod.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String name;

    public void updateFrom(User userToSave) {
        this.setName(userToSave.getName());
    }
}
