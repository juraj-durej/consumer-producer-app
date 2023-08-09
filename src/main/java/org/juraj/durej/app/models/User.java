package org.juraj.durej.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SUSERS")
public class User {

  @Id
  @Column(name = "USER_ID")
  private long userId;
  @Column(name = "USER_GUID")
  private String userGuid;
  @Column(name = "USER_NAME")
  private String userName;

  public User(long userId, String userGuid, String userName) {
    this.userId = userId;
    this.userGuid = userGuid;
    this.userName = userName;
  }
  public User(String userGuid, String userName) {
    this.userGuid = userGuid;
    this.userName = userName;
  }
  public User(){}

  @Override
  public String toString() {
    return "User{" +
        "userId=" + userId +
        ", userGuid='" + userGuid + '\'' +
        ", userName='" + userName + '\'' +
        '}';
  }

  public String getUserName() {
    return userName;
  }
}
