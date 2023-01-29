package com.dihastro.santa.model;

import jakarta.annotation.Nullable;

public class Request {
    @Nullable
    private String executor;
    @Nullable
    private String groupname;
    @Nullable
    private String operand;

    public Request() {}
    public Request(String executor, String groupname, String operand) {
        this.executor = executor;
        this.groupname = groupname;
        this.operand = operand;
    }

    public String getExecutor() {
        return executor;
    }
    public String getGroupname() {
        return groupname;
    }
    public String getOperand() {
        return operand;
    }
}
