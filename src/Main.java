import manager.Manager;
import model.*;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Помыть машину", "Автомойка на ул. Бублика");
        Task task2 = new Task("Поменять резину на машине", "Шиномонтаж за углом");
        Epic epic1 = new Epic("Убраться в доме", "Генеральная уборка запланирована на среду");
        manager.addEpic(epic1);
        SubTask subtask1 = new SubTask("Убраться в ванной", "", epic1.getId());
        SubTask subtask2 = new SubTask("Убраться в комнате", "", epic1.getId());
        Epic epic2 = new Epic("Приготовить обед", "Обед должен состоять из первого, второго и напитка. " +
                "На 6 персон");
        manager.addEpic(epic2);
        SubTask subtask3 = new SubTask("Приготовить рыбный суп ", "Суп из лосося со сливками. Рецепт...",
                epic2.getId());

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        manager.addSubTask(subtask3);
        manager.printAllMap();

        Task task1New = new Task("Помыть машину", "Автомойка на ул. Бублика", TaskStatus.IN_PROGRESS,
                3);
        SubTask subtask2New = new SubTask("Убраться в комнате", "", epic1.getId(), TaskStatus.IN_PROGRESS,
                6);
        Epic epic2New = new Epic("Приготовить обед", "Обед должен состоять из первого, " +
                "второго и напитка.На 6 персон", TaskStatus.IN_PROGRESS, 2);
        System.out.println("Изменили статусы Таска, Сабтаска и Эпика"); //статус epic2 не изменился на IN_Progress, так
        // как статус единственного сабтаска - NEW

        manager.updateTask(task1New);
        manager.updateSubTask(subtask2New);
        manager.updateEpic(epic2New);
        manager.printAllMap();

        manager.deleteTask(3);
        manager.deleteEpic(1);
        System.out.println("Удалили одну задачу и один Эпик");
        manager.printAllMap();
    }
}
