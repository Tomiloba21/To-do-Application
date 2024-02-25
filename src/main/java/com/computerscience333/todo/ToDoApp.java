package com.computerscience333.todo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.util.Optional;

public class ToDoApp extends Application {

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private ListView<Task> taskListView;
    private Spinner<Integer> recurrenceIntervalSpinner;
    private TaskManager taskManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Create ListView for tasks
        taskListView = new ListView<>(tasks);
        taskListView.setCellFactory(CheckBoxListCell.forListView(Task::completionStatusProperty));
        taskListView.setPrefHeight(400);

        // Create TaskManager
        taskManager = new TaskManager(tasks, taskListView);

        // Create other UI components
        TextField newTaskField = new TextField();
        newTaskField.setPromptText("Add a new task");
        newTaskField.setOnAction(e -> taskManager.addTask(newTaskField.getText(), "", "", ""));

        Button clearCompletedButton = new Button("Clear Completed");
        clearCompletedButton.setOnAction(e -> {
            taskManager.clearCompletedTasks();
        });

        // ComboBox for task categories
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Personal", "Work", "Study", "Other");
        categoryComboBox.setPromptText("Select Category");
        categoryComboBox.setOnAction(e -> taskManager.filterTasksByCategory(categoryComboBox.getValue()));

        // TextField for searching tasks
        TextField searchField = new TextField();
        searchField.setPromptText("Search tasks");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> taskManager.searchTasks(newValue));

        // DatePicker for due dates
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Due Date");

        // Add Button for setting reminders
        Button setReminderButton = new Button("Set Reminder");
        setReminderButton.setOnAction(e -> taskManager.setReminder());

        // Add CheckBox for recurring tasks
        CheckBox recurringCheckBox = new CheckBox("Recurring Task");
        recurringCheckBox.setOnAction(e -> taskManager.toggleRecurring());

        // Add Spinner for setting recurrence interval
        recurrenceIntervalSpinner = new Spinner<>(0, Integer.MAX_VALUE, 0);
        recurrenceIntervalSpinner.setPromptText("Recurrence Interval");
        recurrenceIntervalSpinner.setEditable(true);

        // Add TextArea for task notes
        TextArea notesTextArea = new TextArea();
        notesTextArea.setPromptText("Add notes...");
        notesTextArea.setWrapText(true);

        // Set up the layout
        VBox leftVBox = new VBox(categoryComboBox, searchField, datePicker, setReminderButton, recurringCheckBox, recurrenceIntervalSpinner);
        VBox rightVBox = new VBox(newTaskField, clearCompletedButton, notesTextArea);
        HBox topHBox = new HBox(leftVBox, rightVBox);

        root.setTop(topHBox);
        root.setCenter(taskListView);

        // Create a Timeline for reminders
        Timeline reminderTimeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> taskManager.checkReminders()));
        reminderTimeline.setCycleCount(Timeline.INDEFINITE);
        reminderTimeline.play();

        primaryStage.setTitle("Enhanced To-Do App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public void stop() {
        // Save tasks when the application closes
        taskManager.saveTasksToFile("tasks.ser");
    }
}
