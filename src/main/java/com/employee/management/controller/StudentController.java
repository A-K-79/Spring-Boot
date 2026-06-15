package com.employee.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.dto.StudentResponse;
import com.employee.management.model.Student;
import com.employee.management.service.StudentServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class StudentController {
    private final StudentServices studentService; // inject the dependency of StudentServices into StudentController. final keyword is used to ensure that the studentService cannot be reassigned after it has been initialized. This promotes immutability and thread safety in the application, as the reference to the StudentServices instance cannot be changed once it has been set through constructor injection.   
    
    // get all students
    @GetMapping("/students")
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        // return studentService.getAllStudents();
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // private List<Student> students = new ArrayList<>();

    // public StudentController() {
    //     students.add(new Student(1, "John", "Doe"));
    //     students.add(new Student(2, "Jane", "Smith"));
    //     students.add(new Student(3, "Alice", "Johnson"));
    // }
    
    // add a new student to the list
    @PostMapping("/students")
    // public ResponseEntity<String> addStudent(@RequestBody Student s) {
    //     studentService.addStudent(s);
    //     // return "Student added successfully";
    //     return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");
    // }
    public ResponseEntity<StudentResponse> addStudent(@RequestBody Student s) {
        StudentResponse response = studentService.addStudent(s);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    // update the details of a student by id
    @PutMapping("/students/{id}")  // id here is the index in the list, not the student ID
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Integer id, @RequestBody Student s) {
        // short result=studentService.updateStudent(id, s);
        // if (result == 1) {
        //     // return "Student updated successfully";
        //     return ResponseEntity.status(HttpStatus.OK).body("Student updated successfully");
        // } else {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update student");
        //     // components of the response entity are status code and body. In this case, we are returning a 404 Not Found status code along with a message in the body indicating that the update operation failed because the student with the specified ID was not found.
        // }
        
        StudentResponse response = studentService.updateStudent(id, s);
        if(response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    // get the details of a student by id
    @GetMapping("/fetch/{id}")
    public ResponseEntity<StudentResponse> getstudent(@PathVariable Integer id){
        StudentResponse response = studentService.fetchStudent(id); // studentService.fetchStudent(id) is a method call to the fetchStudent method in the StudentServices class, passing the id as an argument.
        if(response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    // delete a student by id
    @DeleteMapping("/students/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer id) {
        StudentResponse result = studentService.deleteStudent(id);
        if (result != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Student deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete student");
        }
    }
}