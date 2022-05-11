package manager;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int id = 0;
    private final HashMap<Integer, Task> tasksMap;
    private final HashMap<Integer, Epic> epicsMap;
    private final HashMap<Integer, SubTask> subTasksMap;

    public Manager() {
        this.tasksMap = new HashMap<>();
        this.epicsMap = new HashMap<>();
        this.subTasksMap = new HashMap<>();
    }

    public HashMap<Integer, Task> getTasksMap() {
        return tasksMap;
    }

    public HashMap<Integer, Epic> getEpicsMap() {
        return epicsMap;
    }

    public HashMap<Integer, SubTask> getSubTasksMap() {
        return subTasksMap;
    }

    public void clearAllTask() {
        tasksMap.clear();
    }

    public void clearAllSubTask() {
        subTasksMap.clear();
    }

    public void clearAllEpic() {
        subTasksMap.clear();
        epicsMap.clear();
    }

    public Task getTask(int id) {
        return tasksMap.getOrDefault(id, null);
    }

    public Epic getEpic(int id) {
        return epicsMap.get(id);
    }

    public SubTask getSubTask(int id) {
        return subTasksMap.get(id);
    }

    public void addTask(Task task) {
        task.setId(++id);
        tasksMap.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epic.setId(++id);
        epicsMap.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        Epic epic = epicsMap.getOrDefault(subTask.getEpicId(), null);
        if (epic == null) {
            return;
        }
        subTask.setId(++id);
        int epicId = subTask.getEpicId();
        int subTaskId = subTask.getId();
        subTasksMap.put(subTaskId, subTask);
        getEpicSubTaskList(epicId).add(subTaskId);
        checkTheStatus(epicId);
    }

    public ArrayList<Integer> getEpicSubTaskList(int epicId) {
        Epic epic = epicsMap.getOrDefault(epicId, null);
        if (epic == null) {
            return null;
        }
        return getEpic(epicId).getSubTaskList();
    }

    public void checkTheStatus(int epicId) {    //Проверка и обновление статуса Эпика
        int statusInProgress = 0;
        int statusNew = 0;
        int ststusDone = 0;
        int i;
        if (getEpicSubTaskList(epicId)!=null) {
            for (i = 0; i < getEpicSubTaskList(epicId).size() - 1; i++) {
                if (subTasksMap.get(getEpicSubTaskList(epicId).get(i)).getStatus()==TaskStatus.IN_PROGRESS) {
                    statusInProgress++;
                } else if (subTasksMap.get(getEpicSubTaskList(epicId).get(i)).getStatus()==TaskStatus.DONE) {
                    ststusDone++;
                } else {
                    statusNew++;
                }
                if (statusInProgress < 1 && ststusDone < 1) {
                    epicsMap.get(epicId).setStatus(TaskStatus.NEW);
                } else if (statusInProgress == 0 && statusNew == 0 && ststusDone >= 1) {
                    epicsMap.get(epicId).setStatus(TaskStatus.DONE);
                } else if (statusInProgress >= 1) {
                    epicsMap.get(epicId).setStatus(TaskStatus.IN_PROGRESS);
                }
            }
        }
    }

    public void updateTask(Task task) {
        if (tasksMap.containsKey(task.getId())) {
            tasksMap.replace(task.getId(), task);
        }
    }

    public void updateEpic(Epic epic) {
        if (epicsMap.containsKey(epic.getId())) {
            epicsMap.replace(epic.getId(), epic);

            for (SubTask subTask : subTasksMap.values()) {
                if (subTask.getEpicId() == epic.getId()) {
                    getEpicSubTaskList(epic.getId()).add(subTask.getId());
                }
            }
            checkTheStatus(epic.getId());
        }
    }

    public void updateSubTask(SubTask subTask) {
        if (subTasksMap.containsKey(subTask.getId())) {
            subTasksMap.replace(subTask.getId(), subTask);
            checkTheStatus(subTask.getEpicId());
        }
    }

    public void deleteTask(int id) {
        if (getTask(id) != null) {
            tasksMap.remove(id);
        }
    }

    public void deleteEpic(int id) {
        if (getEpic(id) != null) {
            if (getEpicSubTaskList(id) != null) {
                for (Integer subTaskID : getEpicSubTaskList(id)) {
                    subTasksMap.remove(subTaskID);
                }
                epicsMap.remove(id);
            }
        }
    }

    public void deleteSubTask(int id) {
        if (getSubTask(id) != null) {
            getEpicSubTaskList(getSubTask(id).getEpicId()).remove(id);
            subTasksMap.remove(id);
        }
    }

    public void printAllMap() {
        System.out.println(getTasksMap());
        System.out.println(getEpicsMap());
        System.out.println(getSubTasksMap());
    }

}
