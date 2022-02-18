package org.xander.practice.webapp.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    private static final Set<String> names;

    static {
        names = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
    }

    @Override
    public String getAuthority() {
        return name();
    }

    public static Set<String> getNames() {
        return names;
    }
}
