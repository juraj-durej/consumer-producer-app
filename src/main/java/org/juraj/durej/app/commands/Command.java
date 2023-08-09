package org.juraj.durej.app.commands;

import org.juraj.durej.app.models.User;

public class Command {
  private CommandType type;
  private User user;

  public Command(CommandType type, User user) {
    this.type = type;
    this.user = user;
  }

  public CommandType getType() {
    return type;
  }

  public User getUser() {
    return user;
  }
}
