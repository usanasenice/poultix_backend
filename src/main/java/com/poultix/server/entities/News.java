package com.poultix.server.entities;

import com.poultix.server.entities.enums.NewsCategory;
import com.poultix.server.entities.enums.NewsPriority;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Content is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @NotBlank(message = "Category is required")
    @Enumerated(EnumType.STRING)
    private NewsCategory category;
    
    @Enumerated(EnumType.STRING)
    private NewsPriority priority = NewsPriority.MEDIUM;
    
    @ElementCollection
    @CollectionTable(name = "news_tags", joinColumns = @JoinColumn(name = "news_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();
    
    @NotNull(message = "Author is required")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    
    
    private String image;
    private String location;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
