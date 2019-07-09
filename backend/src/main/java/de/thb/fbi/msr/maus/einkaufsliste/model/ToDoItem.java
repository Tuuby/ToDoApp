package de.thb.fbi.msr.maus.einkaufsliste.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoItem implements Serializable, Comparable<ToDoItem> {
	private long id;

	private String name;
	private String description;
	private boolean done = false;
	private boolean highPriority = false;
	private long deadline;

	private static SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public void setHighPriority(boolean highPriority) {
		this.highPriority = highPriority;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isDone() {
		return done;
	}

	public boolean isHighPriority() {
		return highPriority;
	}

	public long getDeadline() {
		return deadline;
	}

	public long getId() {
		return id;
	}

	public ToDoItem(long id, String name, String description, boolean done, boolean highPriority, long deadline) {
		this.setId(id);
		this.setName(name);
		this.setDescription(description);
		this.setDone(done);
		this.setHighPriority(highPriority);
		this.setDeadline(deadline);
	}

	public ToDoItem(long id, String name, String description, long deadline) {
		this(id, name, description, false, false, deadline);
	}

	public ToDoItem() {

	}

	public ToDoItem updateFrom(ToDoItem item) {
		this.setName(item.getName());
		this.setDescription(item.getDescription());
		this.setDone(item.isDone());
		this.setHighPriority(item.isHighPriority());
		this.setDeadline(item.getDeadline());

		return this;
	}

	public String toString() {
		Date date = new Date(this.getDeadline());
		return this.getName() + "  -->  " + format.format(date);
	}

	@Override
	public int compareTo(ToDoItem o) {
		if (isDone() != o.isDone()) {
			return Boolean.compare(!isDone(), o.isDone());
		} else {
			return Long.compare(getDeadline(), o.getDeadline());
		}
	}

	public boolean equals(Object other) {
		return other instanceof ToDoItem && ((ToDoItem) other).getId() == this.getId();
	}
}
