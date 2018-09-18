package com.jeanboy.web.demo.domain.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "user")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    private String username;
    private String password;
    private String realname;
    private int gender;
    private long birthday;
    private int educationLevel;
    @Column(name = "update_time", length = 13, nullable = false)
    private long updateTime;
    @Column(name = "create_time", length = 13, nullable = false)
    private long createTime;

}
