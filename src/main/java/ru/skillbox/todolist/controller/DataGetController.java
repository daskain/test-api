package ru.skillbox.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequestMapping()
public class DataGetController {

    @PostMapping
    public String getCurrentData() {
        Calendar calendar = Calendar.getInstance();
        return "Current date " + calendar.getTime().toString();
    }
}
