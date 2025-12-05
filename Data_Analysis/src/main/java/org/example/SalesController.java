package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Inject
    SalesService salesService;

    // Fetch Total sales
    @GetMapping("/total")
    public Map<String, Double> getTotalSales() {
        double total = salesService.getTotalSales();
        return Collections.singletonMap("totalSales", total);
    }

    //Fetch Total Sales by year
    @GetMapping("/by-year")
    public Map<Integer, Double> getSalesByYear() {
        return salesService.getSalesByYear();
    }


    // Fetch top "limit" customers by Revenue default value=1
    @GetMapping("/top-customers")
    public List<Map<String, Object>> getTopCustomers(
            @RequestParam(name = "limit", defaultValue = "1") int limit) {
        return salesService.getTopCustomersByRevenue(limit);
    }

    // Fetch  Average sales per order status
    @GetMapping("/avg-by-status")
    public Map<String, Double> getAverageSalesByStatus() {
        return salesService.getAverageSalesByStatus();
    }


    // Fetch All Records From the CSV
    @GetMapping("/records")
    public List<SalesRecord> getAllRecords() {
        return salesService.getAllRecords();
    }
}