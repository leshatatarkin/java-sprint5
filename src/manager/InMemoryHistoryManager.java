package manager;

import task.*;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> listTask = InMemoryTaskManager.listTask;
    private final int countHistory = 9;

    @Override
    public List<Task> getHistory() {
        System.out.println("Просмотренные задачи:");
        for (Task task : listTask) {
            System.out.println(task);
        }
        return listTask;
    }

    @Override
    public void add(Task task) {
        if (listTask.size() > countHistory) {
            listTask.remove(0);
        }
        listTask.add(task);
    }
}
