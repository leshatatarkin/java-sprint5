import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {
    public static void main(String[] args) {

        TaskManager inMemoryTaskManager = Managers.getDefault();


        //создаем объекты
        inMemoryTaskManager.create(new Task("Таск 1", "описание Таск 1"));
        inMemoryTaskManager.create(new Task("Таск 2", "описание Таск 2"));

        inMemoryTaskManager.create(new Epic("Эпик 1", "описание Эпик 1"));
        inMemoryTaskManager.create(new Subtask("Сабтаск 1", "описание Сабтаск 1", 3));
        inMemoryTaskManager.create(new Subtask("Сабтаск 2", "описание Сабтаск 2", 3));

        inMemoryTaskManager.create(new Epic("Эпик 2", "описание Эпик 1"));
        inMemoryTaskManager.create(new Subtask("Сабтаск 1", "описание Сабтаск 1", 6));

        //печатаем все объекты
        inMemoryTaskManager.printAllTypeTask();
        //изменяем имя, описание, статус у task.Task с id 2
        inMemoryTaskManager.updateTask(new Task("Обновленное имя", "Обновленное описание", 2, Task.Status.DONE));
        //изменяем имя, описание у task.Epic с id 3
        inMemoryTaskManager.updateEpic(new Epic("Обновленное имя", "Обновленное описание", 3));
        //изменяем имя, описание, статус, task.Epic у task.Subtask с id 4
        inMemoryTaskManager.updateSubtask(new Subtask("Обновленное имя", "Обновленное описание", 4, Task.Status.DONE, 3));
        //изменяем имя, описание, статус у task.Subtask с id 7
        inMemoryTaskManager.updateSubtask(new Subtask("Обновленное имя", "Обновленное описание", 7, Task.Status.NEW, 6));
        //изменяем имя, описание, статус у task.Subtask с id 7
        inMemoryTaskManager.updateSubtask(new Subtask("Обновленное имя", "Обновленное описание", 5, Task.Status.DONE, 6));
        //печатаем все объекты
        inMemoryTaskManager.printAllTypeTask();
        //удаляем task.Task с id 2
        inMemoryTaskManager.deleteById(2);
        //удаляем task.Epic с id 3
        inMemoryTaskManager.deleteById(3);
        //печатаем все объекты
        inMemoryTaskManager.printAllTypeTask();


        inMemoryTaskManager.getById(1);
        for (int i = 0; i < 15; i++) {
            inMemoryTaskManager.getById(6);
        }

        HistoryManager inMemoryTaskManager2 = Managers.getDefaultHistory(); // создаем объект с типом интерфейса
        inMemoryTaskManager2.getHistory();

    }
}
