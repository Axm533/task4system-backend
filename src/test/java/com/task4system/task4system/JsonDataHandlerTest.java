package com.task4system.task4system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.task4system.task4system.model.User;
import com.task4system.task4system.model.UserList;
import com.task4system.task4system.JsonDataHandler;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonDataHandlerTest {

    @Test
    public void testGenerateSampleData() throws IOException {
        String path = "test_users.json";
        int count = 3;

        JsonDataHandler.generateSampleData(path, count);

        File file = new File(path);
        assertThat(file.exists()).isTrue();

        String content = new String(Files.readAllBytes(Paths.get(path)));
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(content, List.class);
        assertThat(users.size()).isEqualTo(count);

        file.delete();
    }

    @Test
    public void testWriteToFile() throws IOException {
        String json = "[{\"name\":\"Chester\",\"surname\":\"Raccoon\",\"login\":\"chesterraccoon\"}]";
        String path = "test_write.json";

        JsonDataHandler.writeToFile(json, path);

        File file = new File(path);
        assertThat(file.exists()).isTrue();

        String content = new String(Files.readAllBytes(Paths.get(path)));
        assertThat(content).isEqualTo(json);

        file.delete();
    }

    @Test
    public void testReadJsonFile() throws IOException {
        String path = "test_read.json";
        String json = "{\"userList\":[{\"name\":\"Chester\",\"surname\":\"Raccoon\",\"login\":\"chesterraccoon\"}]}";
        FileWriter writer = new FileWriter(path);
        writer.write(json);
        writer.close();

        UserList userList = JsonDataHandler.readJsonFile(path);
        List<User> users = userList.getUserList();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getName()).isEqualTo("Chester");

        new File(path).delete();
    }

    @Test
    public void testUserListToJson() throws IOException {
        List<User> users = List.of(new User("Chester", "Raccoon", "chesterraccoon"));
        String json = JsonDataHandler.userListToJson(users);

        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        mapper.addMixIn(User.class, UserWithoutIdMixIn.class);
        String expectedJson = mapper.writeValueAsString(users);
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void testJsonToList() throws IOException {
        String path = "test_json_to_list.json";
        String json = "{\"userList\":[{\"name\":\"Chester\",\"surname\":\"Raccoon\",\"login\":\"chesterraccoon\"}]}";
        FileWriter writer = new FileWriter(path);
        writer.write(json);
        writer.close();

        List<User> users = JsonDataHandler.jsonToList(path);
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0).getName()).isEqualTo("Chester");

        new File(path).delete();
    }

    @Test
    public void testGenerateSampleDataThrowsIOException() {
        assertThrows(IOException.class, () -> JsonDataHandler.generateSampleData("/invalid/path", 3));
    }
}
