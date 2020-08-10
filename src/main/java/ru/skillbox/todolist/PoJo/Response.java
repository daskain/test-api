package ru.skillbox.todolist.PoJo;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import ru.skillbox.todolist.model.ToDoListModel;

import java.util.List;

@Getter
@Setter
public class Response {

    private boolean success;
    private String message;
    private List<ToDoListModel> data;

}
