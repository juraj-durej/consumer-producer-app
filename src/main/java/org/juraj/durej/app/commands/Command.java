package org.juraj.durej.app.commands;

import org.juraj.durej.app.models.User;

public record Command(CommandType type, User user) {
}
