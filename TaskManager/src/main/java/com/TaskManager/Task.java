package com.TaskManager;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Task {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	private String description;
	private LocalDate dueDate;
	private boolean complete;
//	
//	public Task(String name, String description, LocalDate dueDate) {
//		this.name = name;
//		this.description = description;
//		this.dueDate = dueDate;
//		complete = false;
//		//TODO: eventually change to current time
//		id = name;
//	}
//	public Task(String name, String description) {
//		this.description = description;
//		complete = false;
//	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	//TODO: change to string builder
	@Override
	public String toString() {
		String completion = complete ? "complete" : "incomplete";
		return   name + ":" + description + " Due: " + dueDate + " is  " + completion;
	}
	
//	private static String currTimeToString() {
//		
//	}
}
