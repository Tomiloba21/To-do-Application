package com.computerscience333.todo;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Task {

    private final StringProperty name;
    private final StringProperty description;
    private final ObjectProperty<LocalDate> dueDate;
    private final StringProperty priority;
    private final StringProperty category;
    private final BooleanProperty completionStatus;
    private final ObjectProperty<LocalDate> reminderDate;
    private final BooleanProperty recurring;
    private final List<String> notes;

    public Task(String name, String description, LocalDate dueDate, String priority, String category) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.priority = new SimpleStringProperty(priority);
        this.category = new SimpleStringProperty(category);
        this.completionStatus = new SimpleBooleanProperty(false);
        this.reminderDate = new SimpleObjectProperty<>(null);
        this.recurring = new SimpleBooleanProperty(false);
        this.notes = new ArrayList<>();
    }

    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public String getPriority() {
        return priority.get();
    }

    public String getCategory() {
        return category.get();
    }

    public boolean isCompletionStatus() {
        return completionStatus.get();
    }

    public LocalDate getReminderDate() {
        return reminderDate.get();
    }

    public boolean isRecurring() {
        return recurring.get();
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus.set(completionStatus);
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate.set(reminderDate);
    }

    public void setRecurring(boolean recurring) {
        this.recurring.set(recurring);
    }

    public void addNote(String note) {
        notes.add(note);
    }

    public void removeNote(String note) {
        notes.remove(note);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public ObjectProperty<LocalDate> dueDateProperty() {
        return dueDate;
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public BooleanProperty completionStatusProperty() {
        return completionStatus;
    }

    public ObjectProperty<LocalDate> reminderDateProperty() {
        return reminderDate;
    }

    public BooleanProperty recurringProperty() {
        return recurring;
    }

    @Override
    public String toString() {
        return getName();
    }
}
