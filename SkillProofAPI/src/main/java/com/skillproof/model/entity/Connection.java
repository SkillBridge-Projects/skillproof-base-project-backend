//package com.skillproof.skillproofapi.model.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "connection")
//@NoArgsConstructor
//public class Connection {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Boolean isAccepted = false;
//
////    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
////    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
////    private User user;
//
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "connectionRequest")
//    private Notification notification;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//
////    public Connection(User user) {
////        this.user = user;
////    }
//}
