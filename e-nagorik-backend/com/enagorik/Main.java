package com.enagorik;

import com.enagorik.facade.ENagorikFacade;
import com.enagorik.model.Citizen;
import com.enagorik.model.Role;
import com.enagorik.model.StaffMember;
import com.enagorik.web.WebServer;

import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        try {
            ENagorikFacade eNagorik = new ENagorikFacade();

            System.out.println("===== E-Nagorik Backend =====");

            File usersFile = new File("./data/users.dat");
            if (!usersFile.exists()) {
                eNagorik.registerCitizen("Mohammad Riyad", "01712345678", "riyad@mail.com", "pass123", "1990123456789", "Dhanmondi, Dhaka");
                eNagorik.registerStaff("Nazmul Islam", "01811112222", "nazmul@dcc.gov.bd", "officerpass", "Helpdesk Officer", "Civil Registration", Role.OFFICER);
                eNagorik.registerStaff("Chairman", "01999999999", "mayor@dcc.gov.bd", "mayorpass", "Chairman/Mayor", "Mayor's Office", Role.MAYOR);
                System.out.println("Seeded default users for the frontend (Citizen=1, Officer=2, Mayor=3)");
            }

            WebServer server = new WebServer(8080, eNagorik);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}