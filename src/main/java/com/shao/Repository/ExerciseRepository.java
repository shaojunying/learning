package com.shao.Repository;

import com.shao.Domain.Exerises;
import com.shao.Domain.Experiment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shao on 2019/4/17 15:46.
 */
public interface ExerciseRepository extends JpaRepository<Exerises,Long> {
    List<Exerises> findAllByChapterId(long id);
}
