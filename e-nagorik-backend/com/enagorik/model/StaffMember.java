package com.enagorik.model;

/** Covers both Officer and Chairman/Mayor — same shape, different Role. */
public class StaffMember extends User
{
    private static final long serialVersionUID = 1L;

    private final String designation;
    private final String department;

    public StaffMember(int userId, String name, String phone, String email, String passwordHash, String designation, String department, Role role)
    {
        super(userId, name, phone, email, passwordHash, role);
        this.designation = designation;
        this.department = department;
    }

    public String getDesignation()
    {
        return designation;
    }

    public String getDepartment()
    {
        return department;
    }
}