package com.cs.example;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cs.example.service.FileService;
import com.cs.example.service.FileServiceImpl;

@SpringBootApplication
public class LogInspectorApplication implements CommandLineRunner{
	private static final Logger LOGGER = LoggerFactory.getLogger(LogInspectorApplication.class);
	
	@Autowired
	private FileService fileService;
	
	public static void main(String[] args) {
		SpringApplication.run(LogInspectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Instant start = Instant.now();
		fileService.analyzeFile(args);
        Instant end = Instant.now();
        LOGGER.info("Total time: {}ms", Duration.between(start, end).toMillis());		
	}
}
