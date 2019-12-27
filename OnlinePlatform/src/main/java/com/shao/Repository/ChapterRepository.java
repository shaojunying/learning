package com.shao.Repository;

import com.shao.Domain.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shao on 2019/4/13 18:48.
 */
public interface ChapterRepository extends JpaRepository<Chapter,Long> {
    List<Chapter> findAllByCourseId(long id);
}
