package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.NewsCategory;
import com.poultix.server.entities.enums.NewsPriority;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsCreateRequest {
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    @NotBlank(message = "Category is required")
    private NewsCategory category;
    
    private NewsPriority priority;
    private List<String> tags;
    private String image;
    private String location;
}
