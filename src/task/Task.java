package task;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected Status status;

    public static enum Status {
        NEW,
        DONE,
        IN_PROGRESS
    }

    public Task(String name, String description) { // конструктор при создании объекта (id генерируется первый свободный)
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Task(String name, String description, int id) { // конструктор при обновлении объекта по id (без обновления status)
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Task(String name, String description, int id, Status status) { // конструктор при обновлении объекта по id (id известен)
        this.name = name;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "task.Task{" +
                "id=" + id +

                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
