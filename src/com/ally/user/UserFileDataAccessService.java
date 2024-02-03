package com.ally.user;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class UserFileDataAccessService implements UserDao {
    @Override
    public User[] getUsers() {
        File file = new File("src/com/ally/users.csv");

        User[] users = new User[4];

        try {
            int index = 0;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] split = scanner.nextLine().split(",");
                users[index] = new User(UUID.fromString(split[0]), split[1]);
                index++;
            }

            return users;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
