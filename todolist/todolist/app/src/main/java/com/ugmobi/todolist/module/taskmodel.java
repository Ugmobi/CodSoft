package com.ugmobi.todolist.module;

public class taskmodel {
    String name;
    String status;
    long id;

    public taskmodel(long id,String name, String status) {
        this.name = name;
        this.status = status;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
