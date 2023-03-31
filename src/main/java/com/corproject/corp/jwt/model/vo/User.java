package com.corproject.corp.jwt.model.vo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @Column(name = "u_uid")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int uid;
    @Column(name="u_id")
    private String id;
    @Column(name="u_password")
    private String password;
    @Column(name="u_name")
    private String name;
    @Column(name="u_status")
    private String status = "S";
    @Column(name="u_date_create")
    private Date createDate = new Date();

    public User(int uid, String id, String password, String name, String status){
        this.uid = uid;
        this.id = id;
        this.password = password;
        this.name = name;
        this.status = status;
        this.createDate = new Date();
    }
}
