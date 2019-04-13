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
public class Comment {
    private long id;
    private String content;
    private long messageBoardId;
    private long userId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content", nullable = true, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "messageBoard_id", nullable = false)
    public long getMessageBoardId() {
        return messageBoardId;
    }

    public void setMessageBoardId(long messageBoardId) {
        this.messageBoardId = messageBoardId;
    }

    @Basic
    @Column(name = "User_id", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                messageBoardId == comment.messageBoardId &&
                userId == comment.userId &&
                Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, messageBoardId, userId);
    }
}
