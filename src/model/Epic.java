package model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subTaskList;

    public Epic(String name, String description) {
        super(name, description, TaskStatus.NEW);
        this.subTaskList = new ArrayList<>();
    }

    public Epic(String name, String description, TaskStatus status, int id) {
        super(name, description, status, id);
        this.subTaskList = new ArrayList<>();
    }

    public ArrayList<Integer> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(ArrayList<Integer> subTaskList) {
        this.subTaskList = subTaskList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subTaskList, epic.subTaskList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskList);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskList=" + subTaskList +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
