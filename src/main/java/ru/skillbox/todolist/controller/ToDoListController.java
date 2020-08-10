package ru.skillbox.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.todolist.model.ToDoListModel;
import ru.skillbox.todolist.service.ToDoListService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/todo_list")
public class ToDoListController {

    private ToDoListService toDoListService;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity> getAll() {
        return toDoListService.getAll().thenApply(response -> {
            if (response.isSuccess()) {
                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            }
        })
                .exceptionally(throwable -> {
                    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity> getById(@PathVariable Long id) {
        return toDoListService.getById(id).thenApply(response -> {
            if (response.isSuccess()) {
                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                return new ResponseEntity(response, HttpStatus.NOT_FOUND);
            }
        })
                .exceptionally(throwable -> {
                    return new ResponseEntity(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @PostMapping()
    public CompletableFuture<ResponseEntity> insert(@RequestBody List<ToDoListModel> toDoListModels) {
        toDoListModels.forEach(s -> {
            System.out.println(s.getId() + " " + s.getNote());
        });
        return toDoListService.addList(toDoListModels).thenApply(response -> {
            return new ResponseEntity(response, HttpStatus.OK);
        })
                .exceptionally(throwable -> {
                    return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
                });
    }

    @PutMapping()
    public CompletableFuture<ResponseEntity> editList(@RequestBody Map<Long, String> records) {
        return toDoListService.editList(records).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity> edit(@PathVariable Long id, String note) {
        System.out.println(id + " " + note);
        return toDoListService.edit(id, note).thenApply(response -> {
            if (response.isSuccess()) {
                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        })
                .exceptionally(throwable -> {
                    return new ResponseEntity(throwable.getMessage(), HttpStatus.NOT_FOUND);
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity> deleteById(@PathVariable Long id) {
        return toDoListService.deleteById(id).thenApply(response -> {
            return new ResponseEntity(response, HttpStatus.OK);
        })
                .exceptionally(throwable -> {
                    return new ResponseEntity(throwable.getMessage(), HttpStatus.EXPECTATION_FAILED);
                });
    }

    @DeleteMapping()
    public CompletableFuture<ResponseEntity> deleteAll() {
        return toDoListService.deleteAll().thenApply(response -> {
            return new ResponseEntity(response, HttpStatus.OK);
        })
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.EXPECTATION_FAILED);
                });
    }

}
