package com.adbidding.campaign_service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ad_creative")
@Builder
public class AdCreative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    private String imageUrl;

    private String redirectUrl;

    private Double baseBid;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
}
