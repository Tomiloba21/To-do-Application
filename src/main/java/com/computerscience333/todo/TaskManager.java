package com.computerscience333.todo;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class TaskManager {

    private ObservableList<Task> tasks;
    private ListView<Task> tasksListView; // Make sure to set this variable appropriately

    public TaskManager(ObservableList<Task> tasks, ListView<Task> tasksListView) {
        this.tasks = tasks;
        this.tasksListView = tasksListView;
    }

    public void addTask(String taskName, String description, String priority, String category) {
        Task newTask = new Task(taskName, description, null, priority, category);
        tasks.add(newTask);
    }

    public void editSelectedTask() {
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            // Open a dialog for editing the task
            TextInputDialog dialog = new TextInputDialog(selectedTask.getName());
            dialog.setTitle("Edit Task");
            dialog.setHeaderText(null);
            dialog.setContentText("Edit task name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(editedName -> selectedTask.setName(editedName));
        } else {
            showAlert("No Task Selected", "Please select a task to edit.");
        }
    }

    public void deleteSelectedTask() {
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
        } else {
            showAlert("No Task Selected", "Please select a task to delete.");
        }
    }

    public void markAsComplete() {
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            selectedTask.setCompletionStatus(true);
        } else {
            showAlert("No Task Selected", "Please select a task to mark as complete.");
        }
    }

    public void saveTasksToFile(String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(new SerializableObservableList<>(tasks));
        } catch (IOException e) {
            showAlert("Error", "Failed to save tasks to file.");
            e.printStackTrace();
        }
    }

    public void loadTasksFromFile(String filePath) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            SerializableObservableList<Task> loadedTasks = (SerializableObservableList<Task>) inputStream.readObject();
            tasks.setAll(loadedTasks);
        } catch (IOException | ClassNotFoundException e) {
            showAlert("Error", "Failed to load tasks from file.");
            e.printStackTrace();
        }
    }

    private Task getSelectedTask() {
        return tasksListView.getSelectionModel().getSelectedItem();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void clearCompletedTasks() {
        tasks.removeIf(Task::isCompletionStatus);
    }

    public void filterTasksByCategory(String value) {
        tasksListView.getItems().clear();
        tasks.stream()
                .filter(task -> task.getCategory().equals(value))
                .forEach(tasksListView.getItems()::add);
    }

    public void setReminder() {
        showAlert("Reminder", "Reminder set for selected task.");
    }

    public void toggleRecurring() {
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            selectedTask.setRecurring(!selectedTask.isRecurring());
        } else {
            showAlert("No Task Selected", "Please select a task to toggle recurring.");
        }
    }

    public void searchTasks(String newValue) {
        tasksListView.getItems().clear();
        tasks.stream()
                .filter(task -> task.getName().contains(newValue) || task.getDescription().contains(newValue))
                .forEach(tasksListView.getItems()::add);
    }

    public void checkReminders() {
        showAlert("Reminders", "No reminders to display.");
    }
}
