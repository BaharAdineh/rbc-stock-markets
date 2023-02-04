package com.challenge.rbc.service;

import com.challenge.rbc.entity.StockData;
import com.challenge.rbc.repository.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import java.text.ParseException;

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


    public List<StockData> getStockDataBySymbol(String symbol, String clientId) {
        return stockDataRepository.findByStock(symbol);
    }

    public List<StockData> bulkInsert(MultipartFile file, String clientId) {
        List<StockData> stockDataList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(file.getOriginalFilename()))) {
            stream.forEach(line -> {
                String[] data = line.split(",");
                StockData stockData = null;
                try {
                    stockData = StockData.builder()
                            .quarter(Integer.parseInt(data[0]))
                            .stock(data[1])

                            .date(new SimpleDateFormat("MM/dd/yyyy").parse(data[2]))
                            .open(Double.parseDouble(data[3]))
                            .high(Double.parseDouble(data[4]))
                            .low(Double.parseDouble(data[5]))
                            .close(Double.parseDouble(data[6]))
                            .volume(Integer.parseInt(data[7]))
                            .percentChangePrice(Double.parseDouble(data[8]))
                            .percentChangeVolumeOverLastWeek(Double.parseDouble(data[9]))
                            .previousWeeksVolume(Integer.parseInt(data[10]))
                            .nextWeeksOpen(Double.parseDouble(data[11]))
                            .nextWeeksClose(Double.parseDouble(data[12]))
                            .percentChangeNextWeeksPrice(Double.parseDouble(data[13]))
                            .daysToNextDividend(Integer.parseInt(data[14]))
                            .percentReturnNextDividend(Double.parseDouble(data[15]))
                            .build();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                stockDataList.add(stockData);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        stockDataRepository.saveAll(stockDataList);
        return stockDataList;
    }

}
