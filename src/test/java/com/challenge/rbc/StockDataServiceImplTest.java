package com.challenge.rbc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.challenge.rbc.service.StockDataServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.challenge.rbc.entity.StockData;
import com.challenge.rbc.repository.StockDataRepository;

@RunWith(MockitoJUnitRunner.class)
public class StockDataServiceImplTest {

    @InjectMocks
    private StockDataServiceImpl stockDataService;

    @Mock
    private StockDataRepository stockDataRepository;

    private List<StockData> stockDataList;

    @Before
    public void setUp() {
        stockDataList = new ArrayList<>();
        StockData stockData = StockData.builder()
                .clientId("client1")
                .quarter(1)
                .stock("AAPL")
                .date(new Date())
                .open(100.0)
                .high(101.0)
                .low(99.0)
                .close(100.5)
                .volume(1000)
                .percentChangePrice(0.1)
                .percentChangeVolumeOverLastWeek(0.2)
                .previousWeeksVolume(900)
                .nextWeeksOpen(102.0)
                .nextWeeksClose(103.0)
                .percentChangeNextWeeksPrice(0.3)
                .daysToNextDividend(5)
                .percentReturnNextDividend(0.05)
                .build();
        stockDataList.add(stockData);
    }

    @Test
    public void testFindAll() {
        when(stockDataRepository.findAll()).thenReturn(stockDataList);
        List<StockData> result = stockDataService.findAll();
        assertEquals(stockDataList, result);
    }

    @Test
    public void testFindByStock() {
        when(stockDataRepository.findByStock("AAPL")).thenReturn(stockDataList);
        List<StockData> result = stockDataService.findByStock("AAPL");
        assertEquals(stockDataList, result);
    }

    @Test
    public void testSave() {
        StockData stockData = stockDataList.get(0);
        when(stockDataRepository.save(stockData)).thenReturn(stockData);
        StockData result = stockDataService.save(stockData);
        assertEquals(stockData, result);
    }


}

