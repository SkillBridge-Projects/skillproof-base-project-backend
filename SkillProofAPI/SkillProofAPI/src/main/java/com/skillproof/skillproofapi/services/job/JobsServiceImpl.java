package com.skillproof.skillproofapi.services.job;

import com.skillproof.skillproofapi.constants.ErrorMessageConstants;
import com.skillproof.skillproofapi.constants.ObjectConstants;
import com.skillproof.skillproofapi.exceptions.ResourceNotFoundException;
import com.skillproof.skillproofapi.model.entity.Job;
import com.skillproof.skillproofapi.model.entity.Picture;
import com.skillproof.skillproofapi.model.entity.User;
import com.skillproof.skillproofapi.recommendation.RecommendationAlgos;
import com.skillproof.skillproofapi.repositories.JobsRepository;
import com.skillproof.skillproofapi.repositories.UserDao;
import com.skillproof.skillproofapi.services.user.UserService;
import com.skillproof.skillproofapi.utils.PictureSave;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class JobsServiceImpl implements JobsService {

    private final UserService userService;
    private final UserDao userDao;
    private final JobsRepository jobRepository;

    @Override
    public void newJob(Long userId, Job job) {
        User currentUser = userService.getUserById(userId);
        job.setUserMadeBy(currentUser);
        jobRepository.save(job);
    }

    @Override
    public Set<Job> getJobs(Long userId) {
        Set<Job> jobs = new HashSet<>(jobRepository.findAll());

        /*User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        jobs.addAll(currentUser.getJobsCreated());
        Set<User> network = userService.getUserNetwork(currentUser);
        for(User u: network){
            jobs.addAll(u.getJobsCreated());
        }*/

        for (Job j : jobs) {
            User owner = j.getUserMadeBy();
            Picture pic = owner.getProfilePicture();
            if (pic != null) {
                if (pic.isCompressed()) {
                    Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(),
                            PictureSave.decompressBytes(pic.getBytes()));
                    pic.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }
            Set<User> usersApplied = j.getUsersApplied();
            for (User user : usersApplied) {
                Picture cpic = user.getProfilePicture();
                if (cpic != null) {
                    if (cpic.isCompressed()) {
                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(),
                                PictureSave.decompressBytes(cpic.getBytes()));
                        cpic.setCompressed(false);
                        user.setProfilePicture(tempPicture);
                    }
                }
            }
        }
        return jobs;
    }

    @Override
    public boolean newApplication(Long userId, Long jobId) {
        User currentUser = userService.getUserById(userId);
        Job job = getJobById(jobId);
        Set<User> usersApplied = job.getUsersApplied();
        if (!usersApplied.contains(currentUser)) {
            usersApplied.add(currentUser);
            job.setUsersApplied(usersApplied);
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    @Override
    public Set<User> getJobApplicants(Long userId, Long jobId) {
//        userService.getUserById(userId); //this line is not required i guess
        Job job = getJobById(jobId);
        return job.getUsersApplied();
    }

    @Override
    public List<Job> getRecommendedJobs(Long userId) {
        RecommendationAlgos recAlgos = new RecommendationAlgos();
        recAlgos.recommendedJobs(userDao, jobRepository, userService);
        User currentUser = userService.getUserById(userId);
        List<Job> recommendedJobs;
        if (!currentUser.getRecommendedJobs().isEmpty()) {
            recommendedJobs = currentUser.getRecommendedJobs();
        } else {
            return new ArrayList<>(getJobs(userId));
        }
        Collections.reverse(recommendedJobs);
        for (Job j : recommendedJobs) {
            User owner = j.getUserMadeBy();
            Picture pic = owner.getProfilePicture();
            if (pic != null) {
                if (pic.isCompressed()) {
                    Picture tempPicture = new Picture(pic.getId(), pic.getName(), pic.getType(),
                            PictureSave.decompressBytes(pic.getBytes()));
                    tempPicture.setCompressed(false);
                    owner.setProfilePicture(tempPicture);
                }
            }
            Set<User> usersApplied = j.getUsersApplied();
            for (User user : usersApplied) {
                Picture cpic = user.getProfilePicture();
                if (cpic != null) {
                    if (cpic.isCompressed()) {
                        Picture tempPicture = new Picture(cpic.getId(), cpic.getName(), cpic.getType(), PictureSave.decompressBytes(cpic.getBytes()));
                        tempPicture.setCompressed(false);
                        user.setProfilePicture(tempPicture);
                    }
                }
            }
        }
        return recommendedJobs;
    }

    public Job getJobById(Long id){
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessageConstants.NOT_FOUND, ObjectConstants.JOB, id)));
    }
}
