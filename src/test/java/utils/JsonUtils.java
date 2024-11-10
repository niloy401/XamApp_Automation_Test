package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import test.User;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class JsonUtils {
    public static List<User> parseUsersFromJson(String filePath) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filePath);
        List<User> users = gson.fromJson(reader, new TypeToken<List<User>>(){}.getType());
        reader.close();
        return users;
    }
}

