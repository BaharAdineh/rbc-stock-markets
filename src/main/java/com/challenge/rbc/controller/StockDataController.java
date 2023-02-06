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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Api(value = "Stock Data Controller")
@RestController
@RequestMapping("/api/stock-data")
public class StockDataController {
    private static final Logger log = LoggerFactory.getLogger(StockDataController.class);
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

        try {
            stockDataService.bulkInsert(file, clientId);
            return new ResponseEntity<>("Data inserted successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error inserting data for clientId: {}", clientId, e);
            return new ResponseEntity<>("Error inserting data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{symbol}")
    public ResponseEntity<?> getStockData(@RequestHeader("X-Client_Id") String clientId,
                                          @PathVariable("symbol") String symbol) {
        if (!clientId.equals("abc123")) {
            return new ResponseEntity<>("Invalid client Id", HttpStatus.UNAUTHORIZED);
        } else {
            List<StockData> stockData = stockDataService.getStockDataBySymbol(symbol);
            if (stockData.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(stockData, HttpStatus.OK);
        }
    }


    @GetMapping
    public List<StockData> getDataByStockTicker(@RequestParam String stockTicker) {
        return stockDataService.findDataByStockTicker(stockTicker);
    }

}


