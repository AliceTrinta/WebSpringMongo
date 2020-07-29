package com.example.web.resources;

import com.example.web.domain.Post;
import com.example.web.resources.util.URL;
import com.example.web.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

    @Autowired
    PostService postService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id){
        Post obj = postService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text){
        text = URL.decodeParam(text);
        List<Post> posts = postService.findByTitle(text);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping(value = "/fullsearch")
    public ResponseEntity<List<Post>> fullSearch(@RequestParam(value = "text", defaultValue = "") String text,
                                                 @RequestParam(value = "min", defaultValue = "") String min,
                                                 @RequestParam(value = "max", defaultValue = "") String max){
        text = URL.decodeParam(text);
        Date minDate = URL.convertDate(min, new Date(0L));
        Date maxDate = URL.convertDate(max, new Date(0L));
        List<Post> posts = postService.fullSearch(text, minDate, maxDate);
        return ResponseEntity.ok().body(posts);
    }

}
