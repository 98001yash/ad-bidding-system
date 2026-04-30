package com.adbidding.campaign_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "targeting_criteria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TargetingCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private String deviceType;
    private String interests;

    private Integer minAge;

    private String maxAge;

    @OneToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

}
