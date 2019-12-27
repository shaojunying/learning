package com.shao.Repository;

import com.shao.Domain.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shao on 2019/4/17 15:21.
 */
public interface ExperimentRepository extends JpaRepository<Experiment,Long> {
    List<Experiment> findAllByCourseId(long courseId);
}
