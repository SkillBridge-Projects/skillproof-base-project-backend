//package com.skillproof.skillproofapi.model.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.*;
//
//import javax.persistence.*;
//import javax.validation.constraints.Size;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Table(name = "job")
//public class Job {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column
//    @NonNull
//    private String title;
//
//    @Column
//    @NonNull
//    @Size(max = 1500)
//    private String description;
//
//    @Column
//    private Timestamp timestamp;
//
////    @ManyToOne(fetch = FetchType.LAZY)
////    @JsonIgnoreProperties({"jobsCreated", "jobApplied", "recommendedJobs", "interestReactions"})
////    private User userMadeBy;
////
////    @ManyToMany(fetch = FetchType.LAZY)
////    @JsonIgnoreProperties({"jobApplied", "jobsCreated", "recommendedJobs", "interestReactions"})
////    private Collection<User> usersApplied;
//
////    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "recommendedJobs")
////    @JsonIgnoreProperties({"recommendedJobs", "jobsCreated", "jobApplied", "interestReactions",
////            "usersFollowing", "userFollowedBy", "posts"})
////    private Collection<User> recommendedTo;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//}