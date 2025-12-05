package org.example;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesService {

    private List<SalesRecord> records = Collections.emptyList();

    @PostConstruct
    public void init() throws IOException {
        // Load CSV from path 'src/main/resources'
        ClassPathResource resource = new ClassPathResource("sales_data_sample.csv");

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

            this.records = reader.lines()
                    .skip(1) // skip header row
                    .map(this::resolveColumnNames)
                    .map(SalesRecord::new)       // construct SalesRecord
                    .collect(Collectors.toList());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }


    private String[] resolveColumnNames(String line) {
        String[] parts = line.split(",");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i] == null ? "" : parts[i].trim();
        }
        return parts;
    }



    // getting  Total Sales
    public double getTotalSales() {
        return records.stream()
                .mapToDouble(SalesRecord::getSales)
                .sum();
    }

    // grouping per year sales
    public Map<Integer, Double> getSalesByYear() {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getYearId,
                        Collectors.summingDouble(SalesRecord::getSales)
                ));
    }



    // Fetch top 'limit' customers by revenue
    public List<Map<String, Object>> getTopCustomersByRevenue(int limit) {
        Map<String, Double> salesByCustomer = records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getCustomerName,
                        Collectors.summingDouble(SalesRecord::getSales)
                ));

        return salesByCustomer.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(limit)
                .map(e -> {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("customerName", e.getKey());
                    map.put("totalSales", e.getValue());
                    return map;
                })
                .collect(Collectors.toList());
    }

    // Average Sales per Order Status
    public Map<String, Double> getAverageSalesByStatus() {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getStatus,
                        Collectors.averagingDouble(SalesRecord::getSales)
                ));
    }


    // fetching all records
    public List<SalesRecord> getAllRecords() {
        return Collections.unmodifiableList(records);
    }
}