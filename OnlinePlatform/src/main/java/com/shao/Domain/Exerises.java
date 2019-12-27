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
public class Exerises {
    private long id;
    private String content;
    private String answer;
    private long chapterId;

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
    @Column(name = "answer", nullable = true, length = -1)
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Basic
    @Column(name = "chapter_id", nullable = false)
    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exerises exerises = (Exerises) o;
        return id == exerises.id &&
                chapterId == exerises.chapterId &&
                Objects.equals(content, exerises.content) &&
                Objects.equals(answer, exerises.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, answer, chapterId);
    }
}
