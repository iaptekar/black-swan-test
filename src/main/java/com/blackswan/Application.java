package com.blackswan;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Application {

	@Value("Black-Swan-Java-Test-1.0.csv")
	private String file;

	@Autowired
	private UserRepository repository;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void loadData() throws Exception {
		boolean skipFirst = false;
		try (BufferedReader r = new BufferedReader(new FileReader(file))) {
			for (String line = r.readLine(); line != null; line = r.readLine()) {
				if (skipFirst) {
					User user = User.create(line);
					repository.save(user);
				}
				skipFirst = true;
			}
		}
	}
}
