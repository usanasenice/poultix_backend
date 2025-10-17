package com.poultix.server.config;

import com.poultix.server.entities.enums.UserRole;
import com.poultix.server.dto.request.UserRegistrationRequest;
import com.poultix.server.entities.User;
import com.poultix.server.entities.embeddables.Location;
import com.poultix.server.service.AuthService;
import com.poultix.server.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserService userService;
    private final AuthService authService;

    private final String adminEmail="admin@admin.com";

    private final String adminPassword="admin";

    private final String adminName="admin";

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void createAdminIfNotExists() {
        if (!userService.checkUserByEmail(adminEmail)) {
            UserRegistrationRequest admin = new UserRegistrationRequest();
            Location location = new Location();
            location.setLatitude(0.0);
            location.setLongitude(0.0);
            admin.setLocation(location);
            admin.setEmail(adminEmail);
            admin.setPassword(adminPassword);
            admin.setName(adminName);
            admin.setRole(UserRole.ADMIN);
            authService.registerAndLogin(admin);
        }
    }
}