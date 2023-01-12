package com.example.umc3_teamproject.domain.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Table(name = "script")
@Getter
@Setter
// @SQLDelete(sql = "UPDATE umc3.script SET deleted = true WHERE script_id = ?")   // JPA Soft Delete
@Where(clause = "deleted = false")
@AllArgsConstructor
@NoArgsConstructor
public class Script {

    @Id                   // Primary Key와 연결
    @GeneratedValue(strategy = GenerationType.AUTO)       // 식별자 값을 자동 생성
    @Column(name="scriptId", updatable = false)
    private Long scriptId;

    @Column(name="userId", updatable = false)
    private Long userId;

//     @ManyToOne(fetch = LAZY)
//     @JoinColumn(name = "user_id")
//     private Member userId;

    @Column
    private String title;

    @Column
    private String type;

    @Column
    private boolean deleted;

    // paragraph를 list로 추가
    // @OneToMany(mappedBy = "users")
    // @JsonBackReference
    // private List<Paragraph> paragraphList = new ArrayList<>();


}
