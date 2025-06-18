package com.example.student_management.controller;

import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController {

    @Autowired
    private StudentService service;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listStudents", service.listAll());
        return "index";
    }

    @GetMapping("/showNewStudentForm")
    public String showNewStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "new_student";
    }



    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.save(student);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Student student = service.get(id);
        model.addAttribute("student", student);
        return "update_student";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable(value = "id") long id) {
        service.delete(id);
        return "redirect:/";
    }
}
