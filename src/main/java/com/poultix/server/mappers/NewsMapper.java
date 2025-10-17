package com.poultix.server.mappers;

import com.poultix.server.dto.NewsDTO;
import com.poultix.server.entities.News;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring")
public interface NewsMapper {
    News toEntity(NewsDTO newsDTO);
    NewsDTO toDTO(News news);
    List<News> toEntities(List<NewsDTO> newsDTOs);
    List<NewsDTO> toDTOs(List<News> news);
}
