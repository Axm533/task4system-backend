package com.task4system.task4system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.task4system.task4system.model.User;
import com.task4system.task4system.model.UserList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDataHandler {

    public static void generateSampleData(String path, int count) throws IOException {
        try {
            List<User> users = generateUserList(count);
            String json = userListToJson(users);
            writeToFile(json, path);
        }
        catch(IOException e)
        {
            throw new IOException("Error occurred: "+ e.getMessage());
        }
    }

    public static void writeToFile(String json, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        writer.write((json));
        writer.close();
    }

    public static UserList readJsonFile(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(path), UserList.class);
    }

    public static String userListToJson(List<User> users) throws JsonProcessingException {
        ObjectMapper mapper= new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.addMixIn(User.class, UserWithoutIdMixIn.class);
        return mapper.writeValueAsString(users);
    }

    public static List<User> jsonToList(String path) throws IOException {
        UserList userList = readJsonFile(path);
        return userList.getUserList();
    }

    private static List<User> generateUserList(int count){
        List<User> users = new ArrayList<>();

        for(int i = 1; i <= count; i++){
            users.add(new User("name" + i, "surname" + i, "login" + i));
        }

        return users;
    }

}
