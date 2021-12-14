package com.example.demoquartz.enitiy;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Simon
 */
@Getter
@Setter
@Entity
@Table(catalog = "scheduler", name = "job_info")
public class JobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schedulerName;

    // job
    private String jobName;

    // job type
    private String jobGroup;

    private String jobClass;

    private String cronExpression;

    private Long repeatTime;

    private Boolean cronJob;

    private String jobData;

    private Date createTime;

    private Date updateTime;

    private Integer state;

    private String createUser;

    private String description;
}