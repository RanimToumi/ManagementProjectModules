package com.Marketing.MarketingAPI.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("purchaseManager:read"),
    MANAGER_UPDATE("purchaseManager:update"),
    MANAGER_CREATE("purchaseManager:create"),
    MANAGER_DELETE("purchaseManager:delete");


    @Getter
    private final String permission;

}