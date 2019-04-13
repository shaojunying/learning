package com.shao.Domain;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by shao on 2019/4/12 17:06.
 */
@Entity
@Table(name = "user_has_course", schema = "onlineplatform", catalog = "")
@IdClass(UserHasCoursePK.class)
public class UserHasCourse {
    private long userId;
    private long courseId;

    public UserHasCourse() {
    }

    public UserHasCourse(long userId, long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    @Id
    @Column(name = "User_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Id
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
        UserHasCourse that = (UserHasCourse) o;
        return userId == that.userId &&
                courseId == that.courseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId);
    }
}
