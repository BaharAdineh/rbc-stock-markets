package com.challenge.rbc.service;

import com.challenge.rbc.entity.StockData;
import com.challenge.rbc.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;


import java.io.IOException;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.text.ParseException;
import java.util.stream.Collectors;

@Service
public class StockDataService {
    @Autowired
    private StockDataRepository stockDataRepository;


    public List<StockData> findAll() {
        return stockDataRepository.findAll();
    }

    public List<StockData> findByStock(String stock) {
        return stockDataRepository.findByStock(stock);
    }

    public StockData save(StockData stockData) {
        return stockDataRepository.save(stockData);
    }


    public List<StockData> getStockDataBySymbol(String symbol) {
        return stockDataRepository.findByStock(symbol);
    }


    public List<StockData> bulkInsert(MultipartFile file, String clientId) {
        List<StockData> stockDataList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            br.readLine(); // this is for skipping first line
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                StockData stockData = StockData.builder()
                        .clientId(clientId)
                        .quarter(Integer.parseInt(data[0]))
                        .stock(data[1])
                        .date(new SimpleDateFormat("MM/dd/yyyy").parse(data[2]))
                        .open(Double.parseDouble(data[3].replace("$", "")))
                        .high(Double.parseDouble(data[4].replace("$", "")))
                        .low(Double.parseDouble(data[5].replace("$", "")))
                        .close(Double.parseDouble(data[6].replace("$", "")))
                        .volume(Integer.parseInt(data[7].replace("$", "")))
                        .percentChangePrice(Double.parseDouble(data[8].replace("$", "")))
                        .percentChangeVolumeOverLastWeek(Double.parseDouble((data[9].isEmpty() ? String.valueOf(0) : data[9])))
                        .previousWeeksVolume(Integer.parseInt((data[10].isEmpty() ? String.valueOf(0) : data[10])))
                        .nextWeeksOpen(Double.parseDouble(data[11].replace("$", "")))
                        .nextWeeksClose(Double.parseDouble(data[12].replace("$", "")))
                        .percentChangeNextWeeksPrice(Double.parseDouble(data[13]))
                        .daysToNextDividend(Integer.parseInt(data[14]))
                        .percentReturnNextDividend(Double.parseDouble(data[15]))
                        .build();
                stockDataList.add(stockData);
            }
            stockDataRepository.saveAll(stockDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stockDataList;
    }


    public List<StockData> findDataByStockTicker(String stockTicker) {
        return stockDataRepository.findByStock(stockTicker);
    }


}