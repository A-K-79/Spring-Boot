// this file is used to transfer data between layers of the application, specifically for address information related to employees. It contains fields for the address details and uses Lombok's @Data annotation to generate boilerplate code like getters, setters, and toString method.
package com.employee.management.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Integer id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
