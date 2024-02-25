package com.computerscience333.todo;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class TaskFileHandler {

    private static final String FILE_PATH = "C:/Users/admin/IdeaProjects/todo/src/main/java/com/computerscience333/todo/Task.java" ;

    public static void saveTasks(List<Task> tasks) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(new ArrayList<>(tasks));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj = inputStream.readObject();
            if (obj instanceof List) {
                tasks.addAll((List<Task>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
