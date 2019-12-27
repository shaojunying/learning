package com.shao.Repository;

import com.shao.Domain.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shao on 2019/4/13 18:49.
 */
public interface SectionRepository extends JpaRepository<Section,Long> {
    List<Section> findAllByChapterId(long id);
}
