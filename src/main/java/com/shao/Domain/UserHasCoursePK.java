package com.shao.Domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by shao on 2019/4/12 17:06.
 */
public class UserHasCoursePK implements Serializable {
    private long userId;
    private long courseId;

    public UserHasCoursePK() {
    }

    public UserHasCoursePK(long userId, long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    @Column(name = "User_id", nullable = false)
    @Id
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "Course_id", nullable = false)
    @Id
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
        UserHasCoursePK that = (UserHasCoursePK) o;
        return userId == that.userId &&
                courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId);
    }
}
