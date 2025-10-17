package com.poultix.server.repository;

import com.poultix.server.entities.News;
import com.poultix.server.entities.enums.NewsCategory;
import com.poultix.server.entities.enums.NewsPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {
    List<News> findByAuthor_Id(UUID authorId);
    
    List<News> findByCategory(NewsCategory category);
    
    List<News> findByPriority(NewsPriority priority);
    
    List<News> findByCategoryOrderByCreatedAtDesc(NewsCategory category);
    
    List<News> findAllByOrderByCreatedAtDesc();
}
