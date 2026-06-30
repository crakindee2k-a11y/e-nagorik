package com.enagorik.model;

import java.io.Serializable;

/**
 * SRP: holds only identity/auth data, nothing about applications or payments.
 * LSP: Citizen and StaffMember can replace a User anywhere without surprises.
 */
public abstract class User implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int userId;
    private final String name;
    private final String phone;
    private final String email;
    private final String passwordHash;
    private final Role role;

    protected User(int userId, String name, String phone, String email, String passwordHash, Role role)
    {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getUserId()
    {
        return userId;
    }

    public String getName()
    {
        return name;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getEmail()
    {
        return email;
    }

    public Role getRole()
    {
        return role;
    }

    protected boolean checkPassword(String rawPassword)
    {
        return passwordHash.equals(rawPassword);
    }
}