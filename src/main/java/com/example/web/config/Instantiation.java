package com.example.web.config;

import com.example.web.domain.Post;
import com.example.web.domain.User;
import com.example.web.dto.AuthorDTO;
import com.example.web.dto.CommentDTO;
import com.example.web.repositories.PostRepository;
import com.example.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        Post post1 = new Post(null, sdf.parse("2018/03/21"), "Trip!", "Trip to São Paulo!", new AuthorDTO(maria));
        Post post2 = new Post(null, sdf.parse("2018/03/23"), "Happy!", "Happiest day!", new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Good Trip!", sdf.parse("2018/03/21"), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Enjoy!", sdf.parse("2018/03/22"), new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Nice day!", sdf.parse("2018/03/24"), new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().addAll(Arrays.asList(c3));

        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);

    }
}
