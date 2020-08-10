package ru.skillbox.todolist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.todolist.PoJo.ToDoListPoJo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/todo_list/json")
public class ToDoListWithoutDBController {

    private static HashMap<Integer, ToDoListPoJo> toDoListPoJoHashMap = new HashMap<>();

    @GetMapping
    public CompletableFuture<ResponseEntity> getAll() {
        return CompletableFuture.supplyAsync(() -> {
            return toDoListPoJoHashMap;
        }).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity> getById(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> {
            return toDoListPoJoHashMap.get(id);
        }).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping()
    public CompletableFuture<ResponseEntity> insert(@RequestBody List<ToDoListPoJo> toDoListPoJo) {
        return CompletableFuture.runAsync(() -> {
            for (ToDoListPoJo element : toDoListPoJo) {
                toDoListPoJoHashMap.put(toDoListPoJo.size(), element);
            }
        }).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.EXPECTATION_FAILED);
                });
    }

    @PutMapping()
    public CompletableFuture<ResponseEntity> editList(@RequestBody Map<Long, String> records) {
        return CompletableFuture.runAsync(() -> {
            records.forEach((key, value) -> {
                changeNote(key, value);
            });
        }).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity> edit(@PathVariable("{id}") Long id, String note) {
        return CompletableFuture.runAsync(() -> {
            changeNote(id, note);
        }).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.NOT_FOUND);
                });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity> deleteById(@PathVariable("id") Long id) {
        return CompletableFuture.runAsync(() -> {
            toDoListPoJoHashMap.remove(id);
        }).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.EXPECTATION_FAILED);
                });
    }

    @DeleteMapping()
    public CompletableFuture<ResponseEntity> deleteAll() {
        return CompletableFuture.runAsync(() -> {
            toDoListPoJoHashMap.clear();
        }).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(throwable -> {
                    return new ResponseEntity<>(throwable.getMessage(), HttpStatus.EXPECTATION_FAILED);
                });
    }

    private void changeNote(Long id, String note) {
        ToDoListPoJo src = toDoListPoJoHashMap.get(id);
        src.setNote(note);
        toDoListPoJoHashMap.put(id.intValue(), src);
    }

}
