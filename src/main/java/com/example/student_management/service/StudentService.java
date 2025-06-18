package com.example.student_management.service;

import com.example.student_management.model.Student;
import com.example.student_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    public List<Student> listAll() {
        return repo.findAll();
    }

    public void save(Student student) {
        repo.save(student);
    }

    public Student get(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Student> sortByField(String field) {
        return repo.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    public List<Student> searchAndSort(String keyword, String field) {
        List<Student> searchResults = repo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrCourseContainingIgnoreCase(
                keyword, keyword, keyword
        );
        // manually sort after searching, since repo doesn’t support combined search+sort natively
        searchResults.sort((s1, s2) -> {
            switch (field) {
                case "email":
                    return s1.getEmail().compareToIgnoreCase(s2.getEmail());
                case "course":
                    return s1.getCourse().compareToIgnoreCase(s2.getCourse());
                default:
                    return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
        return searchResults;
    }
}
