package com.TaskManager;

import java.time.LocalDate;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

@Route("")
public class TaskView extends VerticalLayout {
	
	private TaskRepository repository;
	private TextField name = new TextField("Task");
	private TextField description = new TextField("Description");
	private DatePicker dueDate = new DatePicker("Due Date");
	private Grid<Task> grid = new Grid<>(Task.class, false);
	//reads the values in the  object and converts them from the format expected by the business object to the format expected by the field.
	private Binder<Task> binder = new Binder<>(Task.class);
	
	private Button submit = new Button("Submit");
	private Button myTasks = new Button("View My Tasks");
	private Button completed = new Button("View My Completed Tasks");
	private Button overdue = new Button("View My Overdue Tasks");
	private Button soon = new Button("View My Tasks Due Soon");
	private Button today = new Button("View My Tasks Due Today");
	private Button tomorrow = new Button("View My Tasks Due Tomorrow");

	public TaskView(TaskRepository repository) {
		this.repository = repository;
		
		grid.addColumn(Task::getName).setHeader("Task");
		grid.addColumn(Task::getDescription).setHeader("Description");
		grid.addColumn(Task::getDueDate).setHeader("Due Date");
		grid.addColumn(Task::isComplete).setHeader("Completion");
		HorizontalLayout otherButtons = new HorizontalLayout();
		otherButtons.setAlignItems(Alignment.BASELINE);
		otherButtons.add(viewTasks(), getComplete(), getOverDue(), getSoon(), getToday(), getTomorrow());
		add(getForm(), grid, otherButtons);
		grid.setSelectionMode(SelectionMode.NONE);
		grid.addItemClickListener(event -> {
			showDeleteAndMarkComplete(event.getItem());
		});
		refreshGrid();
	}
	
	 
	private Component getForm() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setAlignItems(Alignment.BASELINE);
		layout.add(name);
		layout.add(description);
		layout.add(dueDate);
		layout.add(submit);
		submit.addClickShortcut(Key.ENTER);
		
		binder.bindInstanceFields(this);
		
		submit.addClickListener(click -> {
			if (name.isEmpty() || description.isEmpty() || dueDate.isEmpty()) {
				throwBadNotification("Please populate all fields");
			} else {
				try {
					var newTask = new Task();
					binder.writeBean(newTask);
					repository.save(newTask);
					binder.readBean(new Task());
					refreshGrid();
				} catch (ValidationException e) {
					
				}
			}
		});
		return layout;
	}
	
	
	private Button viewTasks() {
		myTasks.addClickListener(click -> {
			refreshGrid();
		});
		return myTasks;
	}
	
	//passes in a class to modify as selected on grid and either deletes or sets to complete
	private void showDeleteAndMarkComplete(Task taskToModify) {
		Notification notification = new Notification();
		Button delete = new Button("Delete");
		delete.addClickListener(deleteClick -> {
			repository.delete(taskToModify);
			refreshGrid();
			notification.close();
		});
		Button markComplete = new Button("Mark Complete");
		markComplete.addClickListener(completeClick -> {
						
			taskToModify.setComplete(true);
			repository.save(taskToModify);
			refreshGrid();
			notification.close();
		});
		Button closeButton = new Button(new Icon("lumo", "cross"));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		closeButton.getElement().setAttribute("aria-label", "Close");
		closeButton.addClickListener(event -> {
		  notification.close();
		});
		HorizontalLayout layout = new HorizontalLayout(delete, markComplete, closeButton);
		notification.add(layout);
		notification.open();
	}
	
	//Notification
	private void throwBadNotification(String message) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

		Div text = new Div(new Text(message));

		Button closeButton = new Button(new Icon("lumo", "cross"));
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
		closeButton.getElement().setAttribute("aria-label", "Close");
		closeButton.addClickListener(event -> {
		  notification.close();
		});

		HorizontalLayout layout = new HorizontalLayout(text, closeButton);
		layout.setAlignItems(Alignment.CENTER);

		notification.add(layout);
		notification.open(); 
		
	}
	
	private Button getOverDue() {
		overdue.addClickListener(click -> {
			List<Task> overdueTasks = repository.findAllWithDueDateTimeBefore(LocalDate.now().plusDays(-1));
			overdueTasks.removeIf(element -> (element.isComplete()));
			grid.setItems(overdueTasks);
		});
		return overdue;
	}
	
	private Button getComplete() {
		completed.addClickListener(click -> {
			grid.setItems(repository.findByComplete(true));
		});
		return completed;
	}
	
	private Button getToday() {
		today.addClickListener(click -> {
			grid.setItems(repository.findByDueDate(LocalDate.now()));
		});
		return today;
	}
	
	private Button getTomorrow() {
		tomorrow.addClickListener(click -> {
			grid.setItems(repository.findByDueDate(LocalDate.now().plusDays(1)));
		});
		return tomorrow;
	}
	
	private Button getSoon() {
		soon.addClickListener(click -> {
			List<Task> todayAndTomorrow = repository.findByDueDate(LocalDate.now().plusDays(1));
			todayAndTomorrow.addAll(repository.findByDueDate(LocalDate.now()));
			grid.setItems(todayAndTomorrow);

		});
		return soon;
	}
	
	private void refreshGrid() {
		grid.setItems(repository.findAll());
	}
	
}
