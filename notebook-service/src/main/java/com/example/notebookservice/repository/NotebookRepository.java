package com.example.notebookservice.repository;

import com.example.notebookservice.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotebookRepository extends JpaRepository<Notebook, UUID> {
}