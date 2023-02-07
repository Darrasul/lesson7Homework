package com.buzas.lesson7homework.controllers;

import com.buzas.lesson7homework.entities.students.Student;
import com.buzas.lesson7homework.entities.students.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.module.FindException;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StudentController {
    private final StudentRepository repository;

//    Отрабатывает, но проблема с кнопками при пагинации. При ссылках проблемы нет(screens/wtf/png и комментарий в MainPage.html)
    @GetMapping()
    public ModelAndView getAllStudents(@RequestParam(required = false, defaultValue = "1")Optional<Integer> page,
                                       @RequestParam(required = false, defaultValue = "10")Optional<Integer> size,
                                       Model model
                                       ) {
        int currentPage = page.orElse(1) - 1;
        int sizeValue = size.orElse(10);
        Page<Student> resultPage = repository.findByPage(PageRequest.of(currentPage, sizeValue));
        model.addAttribute("students", resultPage);
        model.addAttribute("newStudent", new Student("name", 1));
        return new ModelAndView("MainPage");
    }

//    Отрабатывает
    @PostMapping("/create")
    public void createStudent(@RequestParam String name, @RequestParam int age) {
        repository.addStudent(name, age);
    }

//    Отрабатывает
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id") Long id) {
        return repository.findById(id).orElseThrow(() -> new FindException("No such student with id: " + id));
    }

//    Отрабатывает, но без кнопок: скрины до/запрос в Postman/после по скринам screens/..  ..before.png/afterPostman.png/afterBase.png
//    Отдельную страницу не стал делать потому, что сегмент на основной странице с добавлением студента отличен лишь ссылкой
    @PostMapping("/{id}")
    public void updateStudent(@PathVariable("id") Long id, @RequestParam String name, @RequestParam int age) {
        repository.updateStudent(id, name, age);
    }

//    Отрабатывает
    @PostMapping("/delete/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
