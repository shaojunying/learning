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
public class Reply {
    private long id;
    private String content;
    private long exerisesId;
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
    @Column(name = "content", nullable = false, length = -1)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "Exerises_id", nullable = false)
    public long getExerisesId() {
        return exerisesId;
    }

    public void setExerisesId(long exerisesId) {
        this.exerisesId = exerisesId;
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
        Reply reply = (Reply) o;
        return id == reply.id &&
                exerisesId == reply.exerisesId &&
                userId == reply.userId &&
                Objects.equals(content, reply.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, exerisesId, userId);
    }
}
