package com.enagorik.factory;

import com.enagorik.model.Citizen;
import com.enagorik.model.Role;
import com.enagorik.model.StaffMember;
import com.enagorik.model.User;

/**
 * Factory Method: callers ask for a user "by role" instead of picking a
 * constructor themselves. Adding a new role later means adding one case
 * here — existing client code is untouched (Open/Closed Principle).
 */
public final class UserFactory
{
    private UserFactory()
    {
    }

    public static User createUser(int id, String name, String phone, String email, String passwordHash, Role role, String... extra)
    {
        switch (role)
        {
            case CITIZEN:
                return new Citizen(id, name, phone, email, passwordHash, extra[0], extra[1]);
            case OFFICER:
                return new StaffMember(id, name, phone, email, passwordHash, extra[0], extra[1], Role.OFFICER);
            case MAYOR:
                return new StaffMember(id, name, phone, email, passwordHash, "Chairman/Mayor", "Mayor's Office", Role.MAYOR);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}