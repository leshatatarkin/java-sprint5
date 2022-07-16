package manager;

import task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int countId; // количество задач всего (кол-во id)
    private Map<Integer, Task> tasks; // коллекция задач
    private Map<Integer, Epic> epics; // коллекция эпиков
    private Map<Integer, Subtask> subtasks; // коллекция подзадач
    static List<Task> listTask;

    public InMemoryTaskManager() {
        this.countId = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        listTask = new ArrayList<>();
    }

    private void setCountId(int countId) {
        this.countId = countId;
    }

    @Override
    public void create(Object obj) { // 2.4 Создание
        // String status = "NEW"; // при создании у всех типов задач статут по умолчанию "NEW"
        countId++; // задаем новый id

        if (obj.getClass().equals(Task.class)) {
            Task task = (Task) obj;
            task.setId(countId);
            tasks.put(countId, task);
        } else if (obj.getClass().equals(Epic.class)) {
            Epic epic = (Epic) obj;
            epic.setId(countId);
            epics.put(countId, epic);
        } else if (obj.getClass().equals(Subtask.class)) {
            Subtask subtask = (Subtask) obj;
            subtask.setId(countId);
            // вызов функции для внесения id сабтаска, в список сабтасков его эпика
            if (!epics.containsKey(subtask.getIdEpic())) { // проверяем наличие task.Epic с введенным id
                System.out.println("task.Epic с id " + subtask.getIdEpic() + " не существует! task.Subtask не создан!");
                return;
            }
            addIdSubtaskToEpics(countId, subtask.getIdEpic());
            subtasks.put(countId, subtask); //  добавляем сабтаск
            checkStatusSubtask(subtask.getIdEpic()); // обновляем статус у родительского эпика
        }
    }

    @Override
    public void printAllTypeTask() { // 2.1 Получение (вывод) всех задач
        System.out.println("Вывод всех задач:");
        printTask();
        printEpic();
        printSubtask();
    }

    @Override
    public void updateSubtask(Subtask subtask) { // 2.5 Обновление task.Subtask
        int idSubtask = subtask.getId();
        int idEpic = subtask.getIdEpic();
        Subtask oldSubtask = subtasks.get(idSubtask);
        int idOldEpic = oldSubtask.getIdEpic();

        if (subtasks.containsKey(idSubtask) && epics.containsKey(idEpic)) {
            System.out.println("Обновляем task.Subtask с id " + idSubtask);
            subtasks.put(idSubtask, subtask);
            addIdSubtaskToEpics(idSubtask, idEpic); // добавляем сабтакс в список нового эпика
            deleteIdSubtaskFromEpics(idSubtask, idOldEpic); // удаляем сабтакс из списка старого эпика
            checkStatusSubtask(idEpic); // обновляем статус у нового эпика
            checkStatusSubtask(idOldEpic); // обновляем статус у старого эпика
        } else {
            System.out.println("Ошибка обновления! Данные не сохранены! id Эпика / id Сабтаска не существует!");
        }
    }

    @Override
    public void updateEpic(Epic epic) { // 2.5 Обновление task.Epic
        int idEpic = epic.getId();

        if (epics.containsKey(idEpic)) {
            System.out.println("Обновляем task.Subtask с id " + idEpic);
            Epic oldEpic = epics.get(idEpic);
            epic.setIdSubtask(oldEpic.getIdSubtask()); // сохраняем старый список сабтасков у эпика
            epics.put(idEpic, epic);
            checkStatusSubtask(idEpic); // обновляем статус у  эпика
        } else {
            System.out.println("Ошибка обновления! Данные у задачи №" + idEpic + " не сохранены! id Эпика не существует!");
        }
    }

    @Override
    public void updateTask(Task task) { // 2.5 Обновление task.Task
        int idTask = task.getId();

        if (tasks.containsKey(idTask)) {
            System.out.println("Обновляем task.Task с id " + idTask);
            tasks.put(idTask, task);
        } else {
            System.out.println("Ошибка обновления! Данные у задачи №" + idTask + " не сохранены! id Таска не существует!");
        }
    }

    @Override
    public void deleteAll(String typeOfTask) { // 2.2 Удаление всех задач
        switch (typeOfTask) {
            case ("task.Task"):
                tasks.clear();
                break;
            case ("task.Epic"):
                epics.clear();
                break;
            case ("task.Subtask"):
                subtasks.clear();
                break;
            default:
                System.out.println("Ошибка удаления! Такого типа не существует. Выберете из task.Task / task.Epic / task.Subtask");
                return;
        }
    }

    @Override
    public Task getById(int id) { // 2.3 Получение по идентификатору
        HistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        if (tasks.containsKey(id)) { // проверка на наличие переданного ключа
            for (Integer key : tasks.keySet()) {
                if (key == id) {
                    System.out.println(tasks.get(key));
                    inMemoryHistoryManager.add(tasks.get(id));
                    return tasks.get(key);
                }
            }
        } else if (epics.containsKey(id)) {
            for (Integer key : epics.keySet()) {
                if (key == id) {
                    System.out.println(epics.get(key));
                    inMemoryHistoryManager.add(epics.get(id));
                    return epics.get(key);
                }
            }
        } else if (subtasks.containsKey(id)) {
            for (Integer key : subtasks.keySet()) {
                if (key == id) {
                    System.out.println(subtasks.get(key));
                    inMemoryHistoryManager.add(subtasks.get(id));
                    return subtasks.get(key);
                }
            }
        }
        System.out.println("Задача с id " + id + " не найдена");
        return null;
    }

    @Override
    public void deleteById(int id) { // 2.6 Удаление по идентификатору
        if (tasks.containsKey(id)) {
            System.out.println("Удаляем task.Task с id " + id);
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            System.out.println("Удаляем task.Epic с id " + id);

            //сначала удаляем все сабтаски эпика
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getIdSubtask().size(); i++) {
                deleteById(epic.getIdSubtask().get(i));
            }
            //удаляем сам эпик
            epics.remove(id);
        } else if (subtasks.containsKey(id)) {
            System.out.println("Удаляем task.Subtask с id " + id);
            subtasks.remove(id);
        } else {
            System.out.println("Ошибка удаления! Такого id не существует!");
        }
    }

    @Override
    public void printSubtaskByIdEpic(int idEpic) { // 3.1 Получение списка всех подзадач определённого эпика
        System.out.println("Все task.Subtask для task.Epic'a №" + idEpic + ":");
        for (Subtask value : subtasks.values()) {
            if (value.getIdEpic() == idEpic) {
                System.out.println(value);
            }
        }
    }

    private void addIdSubtaskToEpics(int idSubtask, int idEpic) { // добавление id сабтаска в список сабтасков эпика
        Epic epic = epics.get(idEpic); // получаем объект task.Epic с указанным id
        epic.getIdSubtask().add(idSubtask); // добавляем id сабтаска в ArrayList task.Epic'а
    }

    private void deleteIdSubtaskFromEpics(int idSubtask, int idEpic) {
        Epic epic = epics.get(idEpic); // получаем объект task.Epic с указанным id
        int position = 0;
        for (Integer valueId : epic.getIdSubtask()) {
            if (valueId == idSubtask) {
                break;
            }
            position++;
        }
        epic.getIdSubtask().remove(position);

    }

    private void printTask() { // 2.1 Получение всех задач (task.Task)
        System.out.println("***********task.Task**********");
        for (Task value : tasks.values()) {
            System.out.println(value);
        }
        System.out.println();
    }

    private void printSubtask() { // 2.1 Получение всех задач (task.Subtask)
        System.out.println("*********task.Subtask*********");
        for (Subtask value : subtasks.values()) {
            System.out.println(value);
        }
        System.out.println();
    }

    private void printEpic() { // 2.1 Получение всех задач (task.Epic)
        System.out.println("***********task.Epic**********");
        for (Epic value : epics.values()) {
            System.out.println(value);
        }
        System.out.println();
    }

    private void checkStatusSubtask(int idEpic) { // проверяем статусы всех сабтасков при обновлении каждого из них и меняем у эпика
        int countNew = 0;
        int countDone = 0;
        int countSubtasksOfEpic = 0;
        Epic epic = epics.get(idEpic); // берем из HashMap task.Epic с необходимым id
        for (Subtask value : subtasks.values()) {
            if (value.getIdEpic() == idEpic) {
                countSubtasksOfEpic++; // считаем количество сабтасков у определенного эпика
                /*if (value.getStatus().equalsIgnoreCase("DONE")) {
                    countDone++;
                } else if (value.getStatus().equalsIgnoreCase("NEW")) {
                    countNew++;
                }*/
                if (value.getStatus() == Task.Status.DONE) {
                    countDone++;
                } else if (value.getStatus() == Task.Status.NEW) {
                    countNew++;
                }

            }
        }
        // меняем статус у родительского эпика в зависимости от статусов его сабтасков
        if (countDone == countSubtasksOfEpic) { // done
            epic.setStatus(Task.Status.DONE);
        } else if (countNew == countSubtasksOfEpic) { // new
            epic.setStatus(Task.Status.NEW);
        } else {
            epic.setStatus(Task.Status.IN_PROGRESS);
        }
    }


}
