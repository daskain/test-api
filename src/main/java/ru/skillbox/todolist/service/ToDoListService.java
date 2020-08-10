package ru.skillbox.todolist.service;

import ru.skillbox.todolist.PoJo.Response;
import ru.skillbox.todolist.model.ToDoListModel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ToDoListService {

    CompletableFuture<Response> getAll();
    CompletableFuture<Response> getById(Long id);
    CompletableFuture<Response> addList(List<ToDoListModel> toDoListModels);
    CompletableFuture<Void> editList(Map<Long, String> records);
    CompletableFuture<Response> edit(Long id, String note);
    CompletableFuture<Response> deleteAll();
    CompletableFuture<Response> deleteById(Long id);


}
