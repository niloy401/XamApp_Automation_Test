package data;

import utils.JsonUtils;
import org.testng.annotations.DataProvider;
import test.User;

import java.io.IOException;
import java.util.List;

public class DataContributors {

    @DataProvider(name = "userDataProvider")
    public Object[][] userDataProvider() throws IOException {
        List<User> users = JsonUtils.parseUsersFromJson("src/test/resources/testdata/users.json");
        Object[][] data = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i);
        }
        return data;
    }

    @DataProvider(name = "lastUserDataProvider")
    public Object[][] lastUserDataProvider() throws IOException {
        List<User> users = JsonUtils.parseUsersFromJson("src/test/resources/testdata/users.json");
        User lastUser = users.get(users.size() - 1);
        return new Object[][]{{lastUser}};
    }
}
