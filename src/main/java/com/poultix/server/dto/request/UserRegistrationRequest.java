package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.UserRole;
import com.poultix.server.entities.embeddables.Location;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    @NotNull(message = "Name is required")
    private String name;
    
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Password is required")
    private String password;
    
    private String avatar;

    @NotNull(message = "Location is required")
    private Location location;
    
    @NotNull(message = "Role is required")
    private UserRole role;
}
