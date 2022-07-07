package com.TaskManager;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	List<Task> findByDueDate(LocalDate date);
	
	List<Task> findByComplete(boolean completion);
		
    @Query("select a from Task a where a.dueDate <= :dueDate")
	List<Task> findAllWithDueDateTimeBefore(@Param("dueDate") LocalDate dueDate);
}
