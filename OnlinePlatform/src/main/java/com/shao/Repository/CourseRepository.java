package com.shao.Repository;

import com.shao.Domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shao on 2019/4/13 17:23.
 */
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAllByTeacherId(long userId);
}
