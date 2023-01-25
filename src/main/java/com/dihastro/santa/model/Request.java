package com.dihastro.santa.model;

import jakarta.annotation.Nullable;

public class Request {
    @Nullable
    private String groupname;
    @Nullable
    private String executor;
    @Nullable
    private String operand;

    public Request() {}
    public Request(String groupname, String executor, String operand) {
        this.groupname = groupname;
        this.executor = executor;
        this.operand = operand;
    }

    public String getGroupname() {
        return groupname;
    }

    public String getExecutor() {
        return executor;
    }

    public String getOperand() {
        return operand;
    }
}
