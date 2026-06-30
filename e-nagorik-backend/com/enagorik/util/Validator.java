package com.enagorik.util;

import com.enagorik.exception.ENagorikException;

import java.util.regex.Pattern;

/** Centralizes input rules instead of letting every form re-implement them (DRY). */
public final class Validator
{
    private static final Pattern NID_PATTERN = Pattern.compile("\\d{10}|\\d{13}|\\d{17}");
    private static final Pattern PHONE_PATTERN = Pattern.compile("01[3-9]\\d{8}");

    private Validator()
    {
    }

    public static void requireValidNid(String nid)
    {
        if (nid == null || !NID_PATTERN.matcher(nid).matches())
        {
            throw new ENagorikException("Invalid NID number.");
        }
    }

    public static void requireValidPhone(String phone)
    {
        if (phone == null || !PHONE_PATTERN.matcher(phone).matches())
        {
            throw new ENagorikException("Invalid Bangladeshi mobile number.");
        }
    }
}