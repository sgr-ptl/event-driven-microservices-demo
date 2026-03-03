package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.event.UserCreatedEvent;
import com.example.userservice.repository.UserRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserService(UserRepository repository,
                       KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public User createUser(User user) {

        User saved = repository.save(user);

        UserCreatedEvent event = new UserCreatedEvent(
                saved.getId(),
                saved.getName(),
                saved.getEmail(),
                saved.getCreatedAt()
        );

        kafkaTemplate.send("user.created", saved.getId().toString(), event);

        return saved;
    }
}