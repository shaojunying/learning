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
public class Experimentsubmit {
    private long id;
    private String wordUrl;
    private long experimentId;
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
    @Column(name = "word_url", nullable = true, length = 45)
    public String getWordUrl() {
        return wordUrl;
    }

    public void setWordUrl(String wordUrl) {
        this.wordUrl = wordUrl;
    }

    @Basic
    @Column(name = "Experiment_id", nullable = false)
    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
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
        Experimentsubmit that = (Experimentsubmit) o;
        return id == that.id &&
                experimentId == that.experimentId &&
                userId == that.userId &&
                Objects.equals(wordUrl, that.wordUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wordUrl, experimentId, userId);
    }
}
