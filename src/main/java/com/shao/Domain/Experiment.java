package com.shao.Domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by shao on 2019/4/12 17:06.
 */
@Entity
public class Experiment {
    private long id;
    private String instructionUrl;
    private long courseId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "instruction_url", nullable = true, length = 45)
    public String getInstructionUrl() {
        return instructionUrl;
    }

    public void setInstructionUrl(String instructionUrl) {
        this.instructionUrl = instructionUrl;
    }

    @Basic
    @Column(name = "Course_id", nullable = false)
    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experiment that = (Experiment) o;
        return id == that.id &&
                courseId == that.courseId &&
                Objects.equals(instructionUrl, that.instructionUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instructionUrl, courseId);
    }
}
