// while creating dto for the Student, we create dto for the Address as well

package com.employee.management.dto;

import com.employee.management.model.UserRole;

import lombok.Data;

@Data
public class StudentRequest {
    private Integer id;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private UserRole role;
    private AddressDTO address;
}
