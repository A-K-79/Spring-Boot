package com.employee.management.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class Student {
//     Integer id;
//     String fname;
//     String lname;
    
// }

@Data

@Entity
public class Student {
    @Id // Primary key annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the primary key value using the database's identity column feature
    Integer id;
    private String fname;
    private String lname;
    private String phone;
    private String email;
    
    @Enumerated(EnumType.ORDINAL) // Specifies that the enum should be stored as an integer in the database
    private UserRole userRole= UserRole.STUDENT;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true) // Specifies a one-to-one relationship between Student and Address entities, with cascading operations
    @JoinColumn(name = "address_id", referencedColumnName = "id") // Specifies the foreign key column in the Student table that references the primary key of the Address table
    private Address address;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}