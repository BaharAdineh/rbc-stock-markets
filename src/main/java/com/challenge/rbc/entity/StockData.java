package com.challenge.rbc.entity;

import lombok.*;


import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "stock_data")
public class StockData {

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