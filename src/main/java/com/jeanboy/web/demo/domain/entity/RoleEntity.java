package com.jeanboy.web.demo.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "role")
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(name = "create_time", length = 13, nullable = false)
    private long createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
