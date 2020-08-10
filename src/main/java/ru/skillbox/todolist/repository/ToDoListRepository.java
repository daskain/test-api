package ru.skillbox.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skillbox.todolist.model.ToDoListModel;

@Repository
public interface ToDoListRepository extends JpaRepository<ToDoListModel, Long> {

}
