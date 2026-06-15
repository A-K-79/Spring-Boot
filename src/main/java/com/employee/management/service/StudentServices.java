// Business logic for managing students is moved here from the previous contoller.java file. This class will handle all operations related to students, such as adding, updating, and retrieving student information. The controller will now only be responsible for handling HTTP requests and delegating the business logic to this service class.
package com.employee.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.employee.management.dto.AddressDTO;
import com.employee.management.dto.StudentResponse;
import com.employee.management.model.Address;
import com.employee.management.model.Student;
import com.employee.management.repository.StudentRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class StudentServices {
    //private final List<Student> students = new ArrayList<>();

    // public StudentServices() {
    //     students.add(new Student(1, "John", "Doe"));
    //     students.add(new Student(2, "Jane", "Smith"));
    //     students.add(new Student(3, "Alice", "Johnson"));
    // }


    // note: the functions such as getId()
    // get all students
    private final StudentRepository sturepo;
    public List<StudentResponse> getAllStudents() {
        return sturepo.findAll().stream()
        .map(this::mapToUserResponse)
        .collect(Collectors.toList());
    }

    // add a new student to the list
    public StudentResponse addStudent(Student s) {
        sturepo.save(s);
        // System.out.println("Student added successfully");
        // students.add(s);
        return mapToUserResponse(s);
    }

    // update the details of a student by id
    public StudentResponse updateStudent(Integer id, Student s) {
        // for(Student student : students) {
        //     if(student.getId() == id) {
        //         students.set(students.indexOf(student), s); // update the student at the specified index (id) with the new student object (s)
        //         return 1;
        //     }
        // }

        //optional is a container object which may or may not contain a non-null value. If a value is present, isPresent() will return true and get() will return the value.
        //// Optional<Student> isPresent=sturepo.findById(id);

        //// if(isPresent.isPresent()) {
        ////    Student student = isPresent.get();
        ////    student.setFname(s.getFname());
        ////    student.setLname(s.getLname());
        ////    student.setPhone(s.getPhone());
        ////    student.setEmail(s.getEmail());
        ////    student.setAddress(s.getAddress());
        ////    sturepo.save(student);
        ////    return 1;
        //// }
        //// return 0;
        
        Student student = sturepo.findById(id).orElse(null);
        if (student!=null) {
            student.setFname(s.getFname());
            student.setLname(s.getLname());
            student.setPhone(s.getPhone());
            student.setEmail(s.getEmail());
            
            if(s.getAddress()!=null){
                Address address = student.getAddress();

                if(address == null) {
                    address = new Address();
                }
                address.setStreet(s.getAddress().getStreet());
                address.setCity(s.getAddress().getCity());
                address.setState(s.getAddress().getState());
                address.setCountry(s.getAddress().getCountry());
                address.setZipCode(s.getAddress().getZipCode());
                
            }
            student.setAddress(s.getAddress());
            Student updatedStudent = sturepo.save(student);
            return mapToUserResponse(updatedStudent);
        }
        return null;
    }

    // get the details of a student by id
    public StudentResponse fetchStudent(Integer id){
        // for(Student student : students) {
        //     if(student.getId() == id) {
        //         return student;
        //     }
        // }
        // return null;
        /*return sturepo.findById(id).orElse(null); // This line of code attempts to find a student by their ID using the sturepo repository. If a student with the specified ID is found, it returns the student object; otherwise, it returns null. The orElse(null) method is used to specify that if the Optional returned by findById() is empty (i.e., no student is found), then null should be returned instead.*/
        return sturepo.findById(id).map(this::mapToUserResponse).orElse(null);
    }

    // delete a student by id
    public StudentResponse deleteStudent(Integer id) {
        Student student = sturepo.findById(id).orElse(null); // if orElse is not used, then the return type of findById() will be Optional<Student>
        if (student!=null) {
            sturepo.delete(student);
            return mapToUserResponse(student);
        }
        return null;
    }
    
    // used at getallstudents()
    private StudentResponse mapToUserResponse(Student s){
        StudentResponse response=new StudentResponse();
        response.setId(s.getId());
        response.setFname(s.getFname());
        response.setLname(s.getLname());
        response.setPhone(s.getPhone());
        response.setEmail(s.getEmail());
        response.setRole(s.getUserRole());
        if(s.getAddress()!=null) {
            AddressDTO addressDTO=new AddressDTO();
            addressDTO.setId(s.getAddress().getId());
            addressDTO.setCity(s.getAddress().getCity());
            addressDTO.setState(s.getAddress().getState());
            addressDTO.setCountry(s.getAddress().getCountry());
            addressDTO.setZipCode(s.getAddress().getZipCode());
            response.setAddress(addressDTO);    
        }
        return response;
    }

}
