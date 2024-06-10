package com.task4system.task4system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UserWithoutIdMixIn {

    @JsonIgnore
    Long getId();

    @JsonProperty("name")
    String getName();

    @JsonProperty("surname")
    String getSurname();

    @JsonProperty("login")
    String getLogin();

}
