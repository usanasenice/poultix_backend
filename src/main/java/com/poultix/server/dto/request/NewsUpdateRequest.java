package com.poultix.server.dto.request;

import com.poultix.server.entities.enums.NewsCategory;
import com.poultix.server.entities.enums.NewsPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsUpdateRequest {
    private String title;
    private String content;
    private NewsCategory category;
    private NewsPriority priority;
    private List<String> tags;
    private String image;
    private String location;
}
