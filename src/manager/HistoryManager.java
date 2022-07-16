package manager;

import task.*;

import java.util.List;

public interface HistoryManager {
    void add(Task task); // помечать задачи как просмотренные

    List<Task> getHistory(); //возвращать список просмотренных задач
}
