//package com.skillproof.skillproofapi.recommendation;
//
//
//import com.skillproof.skillproofapi.model.entity.Job;
//import com.skillproof.skillproofapi.model.entity.Post;
//import com.skillproof.skillproofapi.model.entity.User;
//import com.skillproof.skillproofapi.repositories.JobsDao;
//import com.skillproof.skillproofapi.repositories.PostDao;
//import com.skillproof.skillproofapi.repositories.UserDao;
//import com.skillproof.skillproofapi.services.user.UserService;
//
//import java.util.*;
//
//public class RecommendationAlgos {
//
//    public void recommendedJobs(UserDao userDao, JobsDao jobsDao, UserService userService) {
//
//        List<User> userList = userService.getUsersWithOutAdmin();
//        List<Job> jobList = jobsDao.findAll();
//
//        if(userList.size() > 0 && jobList.size() > 0) {
//            double[][] matrix = new double[userList.size()][jobList.size()];
//
//            for (int u = 0; u < userList.size(); u++) {
//                int count = 0;
//                double val = 0;
//                for (int d = 0; d < jobList.size(); d++) {
//                    /*
//                        Scoring:
//                        --------------------------------
//                        Average edit distance between:
//                         - user skills and
//                         - job title.
//                    */
//                    matrix[u][d] = userService.matchingSkills(userList.get(u),jobList.get(d));
//                    if (matrix[u][d] != -1) {
//                        val += matrix[u][d];
//                        count++;
//                    } else
//                        matrix[u][d] = -2;
//                }
//
//                for(int d = 0 ; d < jobList.size(); d++){
//                    if (matrix[u][d] == -2 && count != 0)
//                        matrix[u][d] = val / count;
//                    else if(count == 0) {
//                        matrix[u][d] = -1;
//                    }
//                }
//
//            }
//
//
//            Recommendation recommendation = new Recommendation();
//            recommendation.print(matrix);
//            double[][] results = recommendation.matrix_factorization(matrix, 2, 0.0002, 0.0);
//            recommendation.print(matrix);
//            recommendation.print(results);
//
//            for (int u = 0; u < userList.size(); u++) {
//                List<Job> jobs = new ArrayList<>();
//                List<Pair> pairs = new ArrayList<>();
//                for (int d = 0; d < jobList.size(); d++) {
//                    if (matrix[u][d] != -1)
//                        pairs.add(new Pair(d, results[u][d]));
//                }
//                pairs.sort((Pair p1, Pair p2) -> Double.compare(p2.value, p1.value));
//                if (pairs.size() > 0) {
//                    for (int i = 0; i < pairs.size(); i++) {
//                        jobs.add(jobList.get(pairs.get(i).index));
//                    }
//                    userList.get(u);
//                }
//            }
//            userDao.saveAll(userList);
//        }
//
//    }
//
//    public void recommendedPosts(UserDao userDao, PostDao postDao, UserService userService) {
//
//        List<User> userList = userService.getUsersWithOutAdmin();
//        List<Post> postList = postDao.findAll();
//
//        if(userList.size() > 0 && postList.size() > 0) {
//            double[][] matrix = new double[userList.size()][postList.size()];
//
//            for (int u = 0; u < userList.size(); u++) {
//                int count = 0;
//                double val = 0;
//                for (int d = 0; d < postList.size(); d++) {
//
//                    /*
//                        Scoring between -1 and 5 overall
//                        --------------------------------
//                        - base edit distance from current job and skills
//                        - *3 if interested
//                        - *numOfComments if commented
//                        * if nothing from above:
//                            -1
//                    */
//                    matrix[u][d] = userService.matchingSkills(userList.get(u),postList.get(d));
//                    if(userService.hasLiked(userList.get(u),postList.get(d)))
//                        matrix[u][d] /= (double)3;
//
//                    Integer numOfComments = userService.numOfComments(userList.get(u), postList.get(d));
//                    if (numOfComments>0)
//                        matrix[u][d] /= (double)(numOfComments+1);
//
//                    if (matrix[u][d] != 0) {
//                        val += matrix[u][d];
//                        count++;
//                    }else{
//                        matrix[u][d] = -2;
//                    }
//                }
//
//                for(int d = 0 ; d < postList.size(); d++){
//                    if (matrix[u][d] == -2 && count != 0){
//                        if(userService.hasLiked(userList.get(u),postList.get(d)) && count!=0){
//                            matrix[u][d] = (val / count)/2;
//                        }else if( userService.numOfComments(userList.get(u),postList.get(d))>0  && count!=0){
//                            matrix[u][d] = (val / count)/userService.numOfComments(userList.get(u), postList.get(d));
//                        } else
//                            matrix[u][d] = -1;
//                    }
//                    else if(count == 0) {
//                        matrix[u][d] = -1;
//                    }
//                }
//
//            }
//
//            Recommendation recommendation = new Recommendation();
//            double[][] results = recommendation.matrix_factorization(matrix, 2, 0.0002, 0.0);
//            recommendation.print(matrix);
//            recommendation.print(results);
//
//            for (int u = 0; u < userList.size(); u++) {
//                List<Post> posts = new ArrayList<>();
//                List<Pair> pairs = new ArrayList<>();
//                for (int d = 0; d < postList.size(); d++) {
//                    if (matrix[u][d] != -1)
//                        pairs.add(new Pair(d, results[u][d]));
//                }
//                pairs.sort((Pair p1, Pair p2) -> Double.compare(p2.value, p1.value));
//
//                if (pairs.size() > 0) {
//                    for (int i = 0; i < pairs.size(); i++) {
//                        posts.add(postList.get(pairs.get(i).index));
//                    }
//                    userList.get(u);
//                }
//            }
//            userDao.saveAll(userList);
//        }
//
//    }
//
//}
//
