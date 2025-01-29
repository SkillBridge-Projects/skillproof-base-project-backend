//package com.skillproof.model.entity;
//
//import com.skillproof.enums.RoleType;
//import lombok.Getter;
//import lombok.NonNull;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//import javax.validation.constraints.Size;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@ToString
//@Table(name = "role")
//public class Role {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Size(max = 100)
//    @Column(name = "description")
//    private String description;
//
//    @NonNull
//    @Enumerated(EnumType.STRING)
//    @Column(name = "name", nullable = false)
//    private RoleType name;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//}