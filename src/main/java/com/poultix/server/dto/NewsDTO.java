package com.poultix.server.dto;

import com.poultix.server.entities.enums.NewsPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsDTO {
    private UUID id;
    private String title;
    private String content;
    private String category;
    private NewsPriority priority;
    private List<String> tags;
    private UserDTO author;
    private String image; 
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
