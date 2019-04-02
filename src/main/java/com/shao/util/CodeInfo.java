package com.shao.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by shao on 2019/4/2 19:34.
 */
public class CodeInfo {
    @NotNull private int status;
    private String description;

    public CodeInfo(@NotNull int status) {
        this.status = status;
    }

    public CodeInfo(@NotNull int status, String description) {
        this.status = status;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
