package uniregistrar.driver.did.btc1.job;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class JobRegistry {

    private Cache<String, Job> jobs;

    public JobRegistry() {
        jobs = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .build();
    }

    public void addJob(Job job) {
        this.jobs.put(job.getJobId(), job);
    }

    public Job getJob(String jobId) {
        return this.jobs.getIfPresent(jobId);
    }

    public void removeJob(Job job) {
        this.jobs.invalidate(job.getJobId());
    }
}
