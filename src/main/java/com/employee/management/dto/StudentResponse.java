package com.employee.management.dto;

import com.employee.management.model.UserRole;

import lombok.Data;

@Data
public class StudentResponse {
    private Integer id;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private UserRole role;
    private AddressDTO address;
}
