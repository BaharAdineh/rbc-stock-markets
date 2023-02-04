package com.challenge.rbc.controller;

import com.challenge.rbc.entity.StockData;
import com.challenge.rbc.repository.StockDataRepository;
import com.challenge.rbc.service.StockDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(value = "Stock Data Controller")
@RestController
@RequestMapping("/api/stock-data")
public class StockDataController {

    @Autowired
    private StockDataService stockDataService;

    @PostMapping("/bulk-insert")
    public ResponseEntity<String> bulkInsert(@RequestHeader("X-Client_Id") String clientId,
                                             @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }
        if (!clientId.equals("abc123")) {
            return new ResponseEntity<>("Invalid client Id", HttpStatus.UNAUTHORIZED);
        }
        stockDataService.bulkInsert(file, clientId);
        return new ResponseEntity<>("Data inserted successfully", HttpStatus.OK);
    }


    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockData>> getStockData(@RequestHeader("X-Client_Id") String clientId,
                                                        @PathVariable("symbol") String symbol) {
        List<StockData> stockData = stockDataService.getStockDataBySymbol(symbol, clientId);
        if (stockData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(stockData, HttpStatus.OK);
    }


}


