package com.example.spring_data;

import com.example.spring_data.entity.feed.FeedEntity;
import com.example.spring_data.entity.user.UserEntity;
import com.example.spring_data.repository.FeedRepository;
import com.example.spring_data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void testUser() {
        FeedEntity feed = new FeedEntity();
        feed.setTitle("title 1");
        feed.setDescription("description 1");

        UserEntity user = new UserEntity();
        user.setUserName("user 1");
        user.setUserEmail("email 1");
        user.setFeeds(List.of(feed));

        feed.setUser(user);

        user = userRepository.save(user);
        feedRepository.save(feed);

        Assertions.assertNotNull(user);
    }

}
