package com.Marketing.MarketingAPI.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_DELETE
    )),
    MANAGER(Set.of(
            Permission.MANAGER_CREATE,
            Permission.MANAGER_DELETE,
            Permission.MANAGER_UPDATE,
            Permission.MANAGER_READ
    )),
    EMPLOYEE(Collections.emptySet())
    ;
    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority>getAuthorities(){
        var authorities=getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}