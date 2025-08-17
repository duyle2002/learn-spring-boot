package duy.com.learnspringboot.utils;

import duy.com.learnspringboot.enums.Role;

public final class SecurityExpressions {
    public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String HAS_ROLE_USER = "hasRole('USER')";

    // Keep this method for programmatic use outside annotations
    public static String hasRole(Role role) {
        return "hasRole('" + role.name() + "')";
    }
}