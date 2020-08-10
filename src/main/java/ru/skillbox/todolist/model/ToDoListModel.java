package ru.skillbox.todolist.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "todolist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoListModel {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "note")
    private String note;
}
