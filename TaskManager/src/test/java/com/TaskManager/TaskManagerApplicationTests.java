package com.TaskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskManagerApplicationTests {
	
	@Autowired
	TaskRepository repository;
	
	@BeforeEach 
    void init() {
		repository.deleteAll();
    }
	
	@Test
	void viewAll() {
		var task1 = new Task();
		task1.setName("Laundry");
		task1.setDescription("Do Laundry");
		repository.save(task1);
		var task2 = new Task();
		task2.setName("Homework");
		task2.setDescription("Do homework");
		repository.save(task2);
		assertEquals(repository.count(), 2);
	}
	
	@Test
	void viewCompleted() {
		var task1 = new Task();
		task1.setComplete(true);
		repository.save(task1);
		var task2 = new Task();

		repository.save(task2);
		List<Task> complete = repository.findByComplete(true);
		assertEquals(complete.size(), 1);
	}
	
	@Test
	void viewToday() {
		var task1 = new Task();
		task1.setDueDate(LocalDate.now());;
		repository.save(task1);
		var task2 = new Task();

		repository.save(task2);
		List<Task> today = repository.findByDueDate(LocalDate.now());
		assertEquals(today.size(), 1);
	}
	
	@Test
	void viewTomorrow() {
		var task1 = new Task();
		task1.setDueDate(LocalDate.now().plusDays(1));;
		repository.save(task1);
		var task2 = new Task();

		repository.save(task2);
		List<Task> today = repository.findByDueDate(LocalDate.now().plusDays(1));
		assertEquals(today.size(), 1);
	}
	
	@Test
	void delete() {
		var task1 = new Task();
		repository.save(task1);
		var task2 = new Task();
		repository.save(task2);
		assertEquals(repository.count(), 2);
		repository.delete(task1);
		assertEquals(repository.count(), 1);
	}
	
	
	@Test
	void markComplete() {
		var task1 = new Task();
		var task2 = new Task();
		var task3 = new Task();
		task1.setComplete(true);
		task2.setComplete(true);
		repository.save(task1);
		repository.save(task2);
		repository.save(task3);

		assertEquals(repository.count(), 3);
		assertEquals(repository.findByComplete(true).size(), 2);

	}
	
	
	
	
	

}
