package com.computerscience333.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class SerializableObservableList<E> extends ArrayList<E> implements Serializable {

    public SerializableObservableList() {
        super();
    }

    public SerializableObservableList(Collection<? extends E> c) {
        super(c);
    }

    public ObservableList<E> asObservableList() {
        return FXCollections.observableArrayList(this);
    }
}
