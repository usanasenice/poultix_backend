package com.poultix.server.controller;

import com.poultix.server.dto.ApiResponse;
import com.poultix.server.dto.request.NewsCreateRequest;
import com.poultix.server.dto.request.NewsUpdateRequest;
import com.poultix.server.dto.NewsDTO;
import com.poultix.server.dto.UserDTO;
import com.poultix.server.service.NewsService;
import com.poultix.server.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
public class NewsController {
    
    private final NewsService newsService;
    private final UserService userService;

    public UUID getUserId(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        return user.getId();
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<NewsDTO>> createNews(@Valid @RequestBody NewsCreateRequest request, Principal principal) {
        UUID authorId = getUserId(principal);
        NewsDTO response = newsService.createNews(request, authorId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("News created successfully", response));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NewsDTO>> getNewsById(@PathVariable UUID id) {
        NewsDTO response = newsService.getNewsById(id);
        return ResponseEntity.ok(ApiResponse.success("News retrieved successfully", response));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<NewsDTO>>> getAllNews() {
        List<NewsDTO> news = newsService.getAllNews();
        return ResponseEntity.ok(ApiResponse.success("News retrieved successfully", news));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NewsDTO>> updateNews(@PathVariable UUID id, @Valid @RequestBody NewsUpdateRequest request) {
        NewsDTO response = newsService.updateNews(id, request);
        return ResponseEntity.ok(ApiResponse.success("News updated successfully", response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNews(@PathVariable UUID id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok(ApiResponse.success("News deleted successfully", null));
    }
}
