package com.sharework.models;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.util.Assert;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users_1")
//@DynamicInsert

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    @NotNull
    @Schema(description = "email", defaultValue = "sharework@sharework.com")
    private String email;

    @Column(name = "password")
    @Schema(description = "password", defaultValue = "password")
    private String password;

    @Column(name = "name")
    @NotNull
    @Schema(description = "name", defaultValue = "김쉐어")
    private String name;

    @Column(name = "phone_number")
    @NotNull
    @Schema(description = "phoneNumber", defaultValue = "01012345678")
    private String phoneNumber;

    @Column(name = "comment")
    @Schema(description = "comment", defaultValue = "안녕하세요.")
    private String comment;

    @Type(JsonType.class)
    @NotNull
    @Schema(description = "residentnumberFront", defaultValue = "20210101")
    private ResidentNumberJsonb residentNumber;

    @Column(name = "profile_img")
    @Schema(description = "profile_img", defaultValue = "1234.jpg || 1234.png || 1234.gif")
    private String profileImg;

    @Column(name = "img_title")
    @Schema(description = "imgTitle", defaultValue = "imgTitle.jpg")
    private String imgTitle;

    @Column(name = "delete_yn")
    @ColumnDefault("N")
    @Schema(description = "deleteYn", defaultValue = "N")
    private String deleteYn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder
    public Users(String email, String name, String phoneNumber, ResidentNumberJsonb residentNumber, Set<Role> roles,
                String password) {
        Assert.hasText(email, "이메일이 없습니다.");
        Assert.hasText(name, "이름이 없습니다.");
        Assert.hasText(phoneNumber, "핸드폰 번호가 없습니다.");
        Assert.hasText(password, "비밀번호가 없습니다.");
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
//        this.residentNumber = residentNumber;
        this.roles = roles;
        this.comment = "";
        this.password = password;
    }
}