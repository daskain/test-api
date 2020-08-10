package ru.skillbox.todolist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.skillbox.todolist.PoJo.Response;
import ru.skillbox.todolist.model.ToDoListModel;
import ru.skillbox.todolist.repository.ToDoListRepository;
import ru.skillbox.todolist.service.ToDoListService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ToDoListImpl implements ToDoListService {

    private ToDoListRepository toDoListRepository;

    @Autowired
    public ToDoListImpl(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    @Async
    @Override
    public CompletableFuture<Response> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            Response response = new Response();
            List<ToDoListModel> result = toDoListRepository.findAll();
            if (result.size() != 0) {
                response.setSuccess(true);
                response.setData(result);
                return response;
            } else {
                response.setSuccess(false);
                response.setMessage("Records not found");
                return response;
            }
        });
    }

    @Async
    @Override
    public CompletableFuture<Response> getById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Response response = new Response();
            Optional<ToDoListModel> toDoListModel = toDoListRepository.findById(id);
            if (toDoListModel.isPresent()) {
                List<ToDoListModel> result = new ArrayList<>();
                result.add(toDoListRepository.findById(id).get());
                response.setSuccess(true);
                response.setData(result);
            } else {
                response.setSuccess(false);
                response.setMessage("Record not found");
            }
            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<Response> addList(List<ToDoListModel> toDoListModels) {
        return CompletableFuture.supplyAsync(() -> {
            toDoListRepository.saveAll(toDoListModels);
            toDoListRepository.flush();
            Response response = new Response();
            response.setSuccess(true);
            response.setMessage("Records has added");
            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<Void> editList(Map<Long, String> records) {
        return CompletableFuture.runAsync(() -> {
            records.keySet().forEach(key -> changeRecord(key, records.get(key)));
        });
    }

    @Async
    @Override
    public CompletableFuture<Response> edit(Long id, String note) {
        return CompletableFuture.supplyAsync(() -> {
            Response response = new Response();
            if(changeRecord(id, note)){
                response.setSuccess(true);
                response.setMessage("Record id = " + id + " has change");
            } else {
                response.setSuccess(false);
                response.setMessage("Record by id = " + id + " not found");
            }
            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<Response> deleteAll() {
        return CompletableFuture.supplyAsync(() -> {
            Response response = new Response();
            toDoListRepository.deleteAll();
            response.setSuccess(true);
            response.setMessage("Records has been removed");
            return response;
        });
    }

    @Async
    @Override
    public CompletableFuture<Response> deleteById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            Response response = new Response();
            System.out.println(!toDoListRepository.findById(id).isEmpty());
            if (!toDoListRepository.findById(id).isEmpty()) {
                toDoListRepository.deleteById(id);
                response.setSuccess(true);
                response.setMessage("Record has been removed");
            } else {
                response.setSuccess(false);
                response.setMessage("Record not found");
            }
            return response;
        });
    }

    private boolean changeRecord(Long id, String note) {
        Optional<ToDoListModel> data = toDoListRepository.findById(id);
        if (!data.isEmpty()) {
            ToDoListModel toDoListModel = data.get();
            toDoListModel.setNote(note);
            toDoListRepository.saveAndFlush(toDoListModel);
            return true;
        }
        return false;
    }

}
