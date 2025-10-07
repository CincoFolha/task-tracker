package com.tasktracker.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tasktracker.model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

interface TaskRepository {
  
  void save(List<Task> tasks);

  List<Task> load();

  boolean exists();

  void clear();

  int count();
}

class FileRepository implements TaskRepository {

  private static final Logger LOGGER = Logger.getLogger(FileRepository.class.getName());
  private static final String DEFAULT_FILE_PATH = "tasks.json";

  private final Path filePath;
  private final ObjectMapper objectMapper;
  private final Object lock = new Object();

  public FileRepository() {
    this(DEFAULT_FILE_PATH);
  }

  public FileRepository(String fileName) {
    if (fileName == null || fileName.trim().isEmpty()) {
      throw new IllegalArgumentException("File name cannot be null or empty");
    }
    this.filePath = Paths.get(fileName);
    this.objectMapper = createObjectMapper();
    initializeFile();
  }

  @Override
  public void save(List<Task> tasks) {
    if (tasks == null) {
      throw new IllegalArgumentException("Tasks list cannot be null");
    }

    synchronized (lock) {
      try {
        String json = objectMapper.writeValueAsString(tasks);

        Files.writeString(
          filePath,
          json,
          StandardCharsets.UTF_8,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING,
          StandardOpenOption.WRITE
            );

        LOGGER.log(Level.FINE, "Saved {0} task(s) to: {1}",
            new Object[]{tasks.size(), filePath});
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Failed to save tasks to: " + filePath, e);
        throw new RepositoryException("Error saving tasks to file: " + filePath, e);
      }
    }
  }

  @Override
  public List<Task> load() {
    synchronized (lock) {
      try {
        if (!Files.exists(filePath) || Files.size(filePath) == 0) {
          LOGGER.log(Level.INFO, "File is empty or does not exist: {0}", filePath);
          return new ArrayList<>();
        }

        String json = Files.readString(filePath, StandardCharsets.UTF_8);

        if (json == null || json.trim().isEmpty() || json.equals("[]")) {
          return new ArrayList<>();
        }

        List<Task> tasks = objectMapper.readValue(
          json,
          new TypeReference<List<Task>>() {}
        );

        LOGGER.log(Level.FINE, "Loaded {0} task(s) from: {1}",
            new Object[]{tasks.size(), filePath});

        return new ArrayList<>(tasks);
      
      } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Failed to load tasks from: " + filePath, e);
        throw new RepositoryException("Error loading tasks from file: " + filePath, e);
      }
    }
  }

  @Override
  public boolean exists() {
    return Files.exists(filePath);
  }

  @Override
  public void clear() {
    synchronized (lock) {
      try {
        save(Collections.emptyList());
        LOGGER.log(Level.INFO, "Repository cleared: {0}", filePath);
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Failed to clear repository: " + filePath, e);
        throw new RepositoryException("Error clearing repository: " + filePath, e);
      }
    }
  }

  @Override
  public int count() {
    try {
      return load().size();
    } catch (Exception e) {
      LOGGER.log(Level.WARNING, "Error counting tasks, returning 0", e);
      return 0;
    }
  }

  public Path getFilePath() {
    return filePath;
  }

  private static ObjectMapper createObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return mapper;
  }

  private void initializeFile() {
    synchronized (lock) {
      try {
        if (!Files.exists(filePath)) {
          Path parentDir = filePath.getParent();
          if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
          }

          save(Collections.emptyList());

          LOGGER.log(Level.INFO, "Repository file create: {0}", filePath);
        }
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Failed to initialize repository file: " + filePath, e);
        throw new RepositoryException("Error creating repository file: " + filePath, e);
      }
    }
  }
}

public class TaskRepositoryFactory {
  public static TaskRepository createJSONRepository() {
    return new FileRepository();
  }

  public static TaskRepository createJSONRepository(String fileName) {
    return new FileRepository(fileName);
  }
}

class RepositoryException extends RuntimeException {
  public RepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public RepositoryException(String message) {
    super(message);
  }
}
