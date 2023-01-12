package com.example.umc3_teamproject.domain;

import com.example.umc3_teamproject.config.BaseTimeEntity;
import com.example.umc3_teamproject.domain.item.Comment;
import com.example.umc3_teamproject.domain.item.Script;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.umc3_teamproject.domain.Tier.BRONZE;


@Getter @Setter @Entity
@NoArgsConstructor @AllArgsConstructor
@Table(name="Member")
public class Member extends BaseTimeEntity {

    @Id //Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
    @Column(name="memberId")
    private Long id;
    @Column(nullable = false, unique=true)
    private String email;
    @Column(nullable = false)
    private String pw;
    @Column(nullable = false, length=30)
    private String nickName;
    @Column(nullable = true)
    private String imageUrl;
    @Column(nullable = false)
    private int tier ;

    private int loginType; //일반 로그인 또는 소셜로그인

    private boolean comments_alarm_permission;
    private boolean voice_permission;
    private boolean event_permission;
    private boolean report_status;


    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Script> scripts = new ArrayList<>();
    //
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Interview> interviews = new ArrayList<>();
//
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

/*
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Script> scripts = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Interview> interviews = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();


 */
    @Builder
    public Member(String email, String pw, String nickName, String imageUrl, int tier, int loginType, boolean comments_alarm_permission, boolean voice_permission, boolean event_permission, boolean report_status){
        this.email = email;
        this.pw = pw;
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.tier = tier;
        this.loginType = loginType;
        this.comments_alarm_permission = comments_alarm_permission;
        this.voice_permission = voice_permission;
        this.event_permission = event_permission;
        this.report_status = report_status;
    }
}
