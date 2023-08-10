package org.juraj.durej.app.database;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.juraj.durej.app.models.User;

class UserRepositoryIntegrationTest {

  UserRepository userRepository = new UserRepository();

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void afterEach() {
    userRepository.deleteAllUsers();
  }

  @Test
  void addUserShouldCreateUserSuccessfully() {
    // given
    User user = new User(1, "a1", "Robert");

    // when
    userRepository.addUser(user);

    // then
    List<User> users = userRepository.getUsers();
    assertEquals(1, users.size());
    assertEquals("Robert", users.get(0).getUserName());
  }

  @Test
  void getUsersShouldReturnAllCreatedUsersSuccessfully() {
    // given
    User user1 = new User(1, "a1", "Robert");
    User user2 = new User(2, "a2", "Martin");

    // when
    userRepository.addUser(user1);
    userRepository.addUser(user2);

    // then
    List<User> users = userRepository.getUsers();
    assertEquals(2, users.size());
  }

  @Test
  void deleteAllUsers() {
    // given
    User user1 = new User(1, "a1", "Robert");
    User user2 = new User(2, "a2", "Martin");

    // when
    userRepository.addUser(user1);
    userRepository.addUser(user2);
    userRepository.deleteAllUsers();

    // then
    List<User> users = userRepository.getUsers();
    assertEquals(0, users.size());
  }
}
