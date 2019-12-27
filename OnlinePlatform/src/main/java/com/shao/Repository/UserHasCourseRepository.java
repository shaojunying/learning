package com.shao.Repository;

import com.shao.Domain.UserHasCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shao on 2019/4/13 21:04.
 */
public interface UserHasCourseRepository extends JpaRepository<UserHasCourse,Long> {
    List<UserHasCourse> findAllByCourseId(long courseId);

    void deleteByUserIdAndCourseId(long id, long courseId);
}
