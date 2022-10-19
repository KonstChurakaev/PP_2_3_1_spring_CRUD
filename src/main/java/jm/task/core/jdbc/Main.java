package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Ivan", "Ivanovich", (byte) 44);
        userService.saveUser("Petr", "Smirnov", (byte) 24);
        userService.saveUser("Oleg", "Ivanov", (byte) 23);
        userService.saveUser("Olga", "Petrova", (byte) 19);

//        userService.removeUserById(2);
//
//        userService.getAllUsers();
//
//        userService.cleanUsersTable();
//
//        userService.dropUsersTable();

    }
}
