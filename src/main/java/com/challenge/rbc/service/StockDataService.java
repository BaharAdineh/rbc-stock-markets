package com.challenge.rbc.service;

import com.challenge.rbc.entity.StockData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StockDataService {


    public List<StockData> getStockDataBySymbol(String symbol);

    public List<StockData> bulkInsert(MultipartFile file, String clientId);


    public StockData addStockData(StockData stockData);

    List<StockData> findBySymbol(String symbol);
}
