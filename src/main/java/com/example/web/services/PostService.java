package com.example.web.services;

import com.example.web.domain.Post;
import com.example.web.repositories.PostRepository;
import com.example.web.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    public Post findById(String id) {
        Optional<Post> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Post> findByTitle(String text){
        return repository.searchTitle(text);
    }

    public List<Post> fullSearch(String text, Date min, Date max){
        max = new Date(max.getTime() + 24 * 60 * 60 * 1000);
        return repository.fullSearch(text, min, max);
    }

}
