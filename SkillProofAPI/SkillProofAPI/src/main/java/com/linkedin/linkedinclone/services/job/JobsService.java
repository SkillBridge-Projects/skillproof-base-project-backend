package com.linkedin.linkedinclone.services.job;

import com.linkedin.linkedinclone.model.Job;
import com.linkedin.linkedinclone.model.User;

import java.util.List;
import java.util.Set;

public interface JobsService {

    void newJob(Long userId, Job job);

    Set<Job> getJobs(Long userId);

    boolean newApplication( Long userId,  Long jobId);

    Set<User> getJobApplicants( Long userId,  Long jobId);

    List<Job> getRecommendedJobs( Long userId);
}
