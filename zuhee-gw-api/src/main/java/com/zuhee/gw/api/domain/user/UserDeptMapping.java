package com.zuhee.gw.api.domain.user;

import com.zuhee.gw.api.domain.dept.Department;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_dept_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserDeptMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_code", nullable = false)
    private Department department;

    @Column(name = "is_primary", nullable = false)
    private boolean isPrimary;

    @Column(name = "position_code", length = 20)
    private String positionCode;
}
