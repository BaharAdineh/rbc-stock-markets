package com.challenge.rbc;


import com.challenge.rbc.controller.StockDataController;
import com.challenge.rbc.entity.StockData;
import com.challenge.rbc.service.StockDataServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockDataControllerTest {
    @InjectMocks
    private StockDataController stockDataController;

    @Mock
    private StockDataServiceImpl stockDataService;

    @Test
    void testGetAllStockData() {
        List<StockData> stockDataList = new ArrayList<>();
        StockData stockData = new StockData();
        stockData.setStock("AAPL");
        stockDataList.add(stockData);

        when(stockDataService.findAll()).thenReturn(stockDataList);

        ResponseEntity<List<StockData>> responseEntity = stockDataController.getAllStockData();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("AAPL", responseEntity.getBody().get(0).getStock());
    }



    @Test
    void testAddStockData() {
        StockData stockData = new StockData();
        stockData.setStock("AAPL");

        when(stockDataService.addStockData(stockData)).thenReturn(stockData);

        ResponseEntity<StockData> responseEntity = stockDataController.addStockData(stockData);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("AAPL", responseEntity.getBody().getStock());
    }


    @Test
    public void testGetStockData_ValidClientId_Success() {
        String symbol = "AAPL";
        String clientId = "abc123";
        List<StockData> expectedStockData = new ArrayList<>();
        expectedStockData.add(new StockData());
        when(stockDataService.getStockDataBySymbol(symbol)).thenReturn(expectedStockData);

        ResponseEntity<?> response = stockDataController.getStockData(clientId, symbol);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStockData, response.getBody());
    }

    @Test
    public void testGetStockData_InvalidClientId_Failure() {
        String symbol = "AAPL";
        String clientId = "invalid";

        ResponseEntity<?> response = stockDataController.getStockData(clientId, symbol);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid client Id", response.getBody());
    }

    @Test
    public void testGetStockData_NoStockDataFound_Failure() {
        String symbol = "AAPL";
        String clientId = "abc123";
        when(stockDataService.getStockDataBySymbol(symbol)).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = stockDataController.getStockData(clientId, symbol);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }


    @Test
    public void testGetStockDataBySymbol_ShouldReturnNotFound_WhenNoDataFound() {
        // Arrange
        when(stockDataService.findBySymbol("symbol")).thenReturn(null);

        // Act
        ResponseEntity<List<StockData>> response = stockDataController.getStockDataBySymbol("symbol");

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetStockDataBySymbol_ShouldReturnOK_WhenDataFound() {
        // Arrange
        List<StockData> expectedStockDataList = new ArrayList<>();
        expectedStockDataList.add(new StockData());
        when(stockDataService.findBySymbol("symbol")).thenReturn(expectedStockDataList);

        // Act
        ResponseEntity<List<StockData>> response = stockDataController.getStockDataBySymbol("symbol");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedStockDataList, response.getBody());
    }
}

