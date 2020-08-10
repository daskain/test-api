package ru.skillbox.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PageController {

    @GetMapping
    public String indexPage(Model model) {
        return "index";
    }

    @GetMapping("/get_all")
    public String getAllPage(Model model) {
        return "get_all";
    }

    @GetMapping("/get_by_id")
    public String getById(Model model) {
        return "get_by_id";
    }

    @GetMapping("/add")
    public String addRecord(Model model) {
        return "add";
    }

    @GetMapping("/edit_list")
    public String editRecord(Model model) {
        return "edit_list";
    }

    @GetMapping("/edit_by_id")
    public String editById(Model model) {
        return "edit_by_id";
    }

    @GetMapping("/delete_all")
    public String deleteAll(Model model) {
        return "delete_all";
    }

    @GetMapping("/delete_by_id")
    public String deleteById(Model model) {
        return "delete_by_id";
    }
}
