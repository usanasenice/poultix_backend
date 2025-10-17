package com.poultix.server.service;

import com.poultix.server.dto.request.NewsCreateRequest;
import com.poultix.server.dto.request.NewsUpdateRequest;
import com.poultix.server.dto.NewsDTO;
import com.poultix.server.entities.News;
import com.poultix.server.entities.User;
import com.poultix.server.mappers.NewsMapper;
import com.poultix.server.repository.NewsRepository;
import com.poultix.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {
    
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserRepository userRepository;
    
    public NewsDTO createNews(NewsCreateRequest request, UUID authorId) {
        // Create new news
        News news = new News();
        news.setTitle(request.getTitle());
        news.setContent(request.getContent());
        news.setCategory(request.getCategory());
        news.setPriority(request.getPriority());
        news.setTags(request.getTags());
        news.setImage(request.getImage());
        news.setLocation(request.getLocation());
        
        // Fetch and set author
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));
        news.setAuthor(author);
        
        News savedNews = newsRepository.save(news);
        return newsMapper.toDTO(savedNews);
    }
    
    @Transactional(readOnly = true)
    public NewsDTO getNewsById(UUID id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("News not found"));
        return newsMapper.toDTO(news);
    }
    
    @Transactional(readOnly = true)
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(newsMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public NewsDTO updateNews(UUID id, NewsUpdateRequest request) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("News not found"));
        
        // Update news fields manually
        if (request.getTitle() != null) {
            news.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            news.setContent(request.getContent());
        }
        if (request.getCategory() != null) {
            news.setCategory(request.getCategory());
        }
        if (request.getPriority() != null) {
            news.setPriority(request.getPriority());
        }
        if (request.getTags() != null) {
            news.setTags(request.getTags());
        }
        if (request.getImage() != null) {
            news.setImage(request.getImage());
        }
        if (request.getLocation() != null) {
            news.setLocation(request.getLocation());
        }
        
        return newsMapper.toDTO(newsRepository.save(news));
    }
    
    public void deleteNews(UUID id) {
        newsRepository.deleteById(id);
    }
}
