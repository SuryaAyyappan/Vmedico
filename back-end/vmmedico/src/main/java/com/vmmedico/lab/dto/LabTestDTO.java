package com.vmmedico.lab.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LabTestDTO {
    private Long id;
    private String name;
    private String testCode;
    private String description;
    private Double price;
    private Boolean active;
}
