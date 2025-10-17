package com.poultix.server.dto.request;

import com.poultix.server.entities.embeddables.Location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    private String avatar;
    private String phone;
    private Location location;
}
