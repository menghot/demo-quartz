package com.example.demoquartz.repository;

import com.example.demoquartz.enitiy.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Simon
 */
@Repository
public interface JobInfoRepository extends JpaRepository<JobInfo, Long> {

}
