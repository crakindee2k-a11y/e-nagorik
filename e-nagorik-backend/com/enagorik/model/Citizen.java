package com.enagorik.model;

public class Citizen extends User
{
    private static final long serialVersionUID = 1L;

    private final String nid;
    private final String address;

    public Citizen(int userId, String name, String phone, String email, String passwordHash, String nid, String address)
    {
        super(userId, name, phone, email, passwordHash, Role.CITIZEN);
        this.nid = nid;
        this.address = address;
    }

    public String getNid()
    {
        return nid;
    }

    public String getAddress()
    {
        return address;
    }
}