package com.example.notebookservice.service;

import com.example.notebookservice.entity.Notebook;
import com.example.notebookservice.event.UserCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.notebookservice.repository.NotebookRepository;



@Service
public class UserEventListener {

    private final NotebookRepository repository;
    private static final Logger log = LoggerFactory.getLogger(UserEventListener.class);
    public UserEventListener(NotebookRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "user.created", groupId = "notebook-group-v4")
    public void handleUserCreated(UserCreatedEvent event) {
        log.info("Received event: {}", event);
        Notebook notebook = new Notebook();
        notebook.setUserId(event.userId());
        notebook.setTitle("Default Notebook for " + event.name());

        repository.save(notebook);

        log.info("Notebook created for user: {}", event.userId());
    }
}