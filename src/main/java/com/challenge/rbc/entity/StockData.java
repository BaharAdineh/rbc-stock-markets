package com.challenge.rbc.entity;

import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

import lombok.Data;


@Entity
@Table(name = "stock")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientId;
    private Integer quarter;
    private String stock;
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Integer volume;
    private Double percentChangePrice;
    private Double percentChangeVolumeOverLastWeek;
    private Integer previousWeeksVolume;
    private Double nextWeeksOpen;
    private Double nextWeeksClose;
    private Double percentChangeNextWeeksPrice;
    private Integer daysToNextDividend;
    private Double percentReturnNextDividend;

}