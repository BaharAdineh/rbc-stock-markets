package com.challenge.rbc.service;

import com.challenge.rbc.entity.StockData;
import com.challenge.rbc.repository.StockDataRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;


import java.util.List;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Transactional
public class StockDataServiceImpl implements StockDataService {
    private static final Logger LOG = LoggerFactory.getLogger(StockDataServiceImpl.class);
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
            LOG.error(e.getMessage());
        }
        return stockDataList;
    }


    public StockData addStockData(StockData stockData) {
        return stockDataRepository.save(stockData);
    }

    @Override
    public List<StockData> findBySymbol(String symbol) {
        return stockDataRepository.findByStock(symbol);
    }



}