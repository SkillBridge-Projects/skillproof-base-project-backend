//package com.skillproof.skillproofapi.model.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.*;
//import org.hibernate.proxy.HibernateProxy;
//
//import javax.persistence.*;
//import java.util.Objects;
//
//@Entity
//@Getter
//@Setter
//@ToString
//@RequiredArgsConstructor
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "picture")
//public class Picture {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "isCompressed")
//    @NonNull
//    private boolean isCompressed = false;
//
//    @Column(name = "type")
//    @NonNull
//    private String type;
//
//    @Column(name = "name")
//    @NonNull
//    private String name;
//
//    @Column(name = "bytes", length = 200000)
//    @NonNull
//    private byte[] bytes;
//
////    @OneToOne(cascade = CascadeType.ALL, mappedBy = "profilePicture")
////    @JsonIgnoreProperties("profilePicture")
////    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnoreProperties("pictures")
//    private Post post;
//
//    public Picture(Long id, String name, String type, byte[] bytes) {
//        this.id = id;
//        this.name = name;
//        this.type = type;
//        this.bytes = bytes;
//    }
//}