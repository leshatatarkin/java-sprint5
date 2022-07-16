package manager;

import java.util.List;

import task.*;

public interface TaskManager {

    void create(Object obj); // 2.4 Создание

    void printAllTypeTask(); // 2.1 Получение (вывод) всех задач

    void updateSubtask(Subtask subtask);// 2.5 Обновление task.Subtask

    void updateEpic(Epic epic); // 2.5 Обновление task.Epic

    void updateTask(Task task); // 2.5 Обновление task.Task

    void deleteAll(String typeOfTask);// 2.2 Удаление всех задач

    Task getById(int id); // 2.3 Получение по идентификатору

    void deleteById(int id); // 2.6 Удаление по идентификатору

    void printSubtaskByIdEpic(int idEpic);// 3.1 Получение списка всех подзадач определённого эпика

}

