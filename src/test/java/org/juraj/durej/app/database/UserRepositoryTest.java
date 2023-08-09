package org.juraj.durej.app.database;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.juraj.durej.app.models.User;

class UserRepositoryTest {

  UserRepository userRepository = new UserRepository();;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void afterEach(){
    userRepository.deleteAllUsers();
  }

  @Test
  void addUserShouldCreateUserSuccessfully() {
    userRepository.addUser(new User(1, "a1", "Robert"));
    List<User> users = userRepository.getUsers();

    assertEquals(1,users.size());
    assertEquals("Robert", users.get(0).getUserName());
  }

  @Test
  void getUsersShouldReturnAllCreatedUsersSuccessfully() {
    userRepository.addUser(new User(1, "a1", "Robert"));
    userRepository.addUser(new User(2, "a2", "Martin"));
    List<User> users = userRepository.getUsers();

    assertEquals(2, users.size());
  }

  @Test
  void deleteAllUsers() {
    userRepository.addUser(new User(1, "a1", "Robert"));
    userRepository.addUser(new User(2, "a2", "Martin"));

    userRepository.deleteAllUsers();
    List<User> users = userRepository.getUsers();

    assertEquals(0, users.size());
  }
}
