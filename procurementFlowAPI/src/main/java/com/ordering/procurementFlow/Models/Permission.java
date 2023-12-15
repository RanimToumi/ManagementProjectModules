package com.ordering.procurementFlow.Models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    PURCHASE_MANAGER_READ("purchaseManager:read"),
    PURCHASE_MANAGER_UPDATE("purchaseManager:update"),
    PURCHASE_MANAGER_CREATE("purchaseManager:create"),
    PURCHASE_MANAGER_DELETE("purchaseManager:delete");


    @Getter
    private final String permission;

}