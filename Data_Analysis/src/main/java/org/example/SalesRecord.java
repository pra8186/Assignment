package org.example;


import java.time.format.DateTimeFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class SalesRecord {

    private final int orderNumber;
    private final int quantityOrdered;
    private final double priceEach;
    private final int orderLineNumber;
    private final double sales;
    private final LocalDateTime orderDate;
    private final String status;
    private final int quarterId;
    private final int monthId;
    private final int yearId;
    private final String productLine;
    private final double msrp;
    private final String productCode;
    private final String customerName;
    private final String phone;
    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String country;
    private final String territory;
    private final String contactLastName;
    private final String contactFirstName;
    private final String dealSize;

    private static final DateTimeFormatter[] DATE_FORMATS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("M/d/yyyy H:mm"),
            DateTimeFormatter.ofPattern("M/d/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss"),
            DateTimeFormatter.ofPattern("M/d/yyyy HH:mm:ss"),


            DateTimeFormatter.ofPattern("M/d/yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),


            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm")
    };
    public SalesRecord(String[] rawColumns) {

        // Trim all values first
        String[] columns = new String[rawColumns.length];
        for (int i = 0; i < rawColumns.length; i++) {
            columns[i] = rawColumns[i] == null ? "" : rawColumns[i].trim();
        }

        this.orderNumber      = Integer.parseInt(columns[0]);
        this.quantityOrdered  = Integer.parseInt(columns[1]);
        this.priceEach        = Double.parseDouble(columns[2]);
        this.orderLineNumber  = Integer.parseInt(columns[3]);
        this.sales            = Double.parseDouble(columns[4]);

        //  parsing order date from custom list of parsers
        this.orderDate = parseOrderDate(columns[5]);

        this.status           = columns[6];
        this.quarterId        = Integer.parseInt(columns[7]);
        this.monthId          = Integer.parseInt(columns[8]);
        this.yearId           = Integer.parseInt(columns[9]);
        this.productLine      = columns[10];
        this.msrp             = Double.parseDouble(columns[11]);
        this.productCode      = columns[12];
        this.customerName     = columns[13];
        this.phone            = columns[14];
        this.addressLine1     = columns[15];
        this.addressLine2     = columns[16];
        this.city             = columns[17];
        this.state            = columns[18];
        this.postalCode       = columns[19];
        this.country          = columns[20];
        this.territory        = columns[21];
        this.contactLastName  = columns[22];
        this.contactFirstName = columns[23];
        this.dealSize         = columns[24];
    }

    /**
     * Attempts to parse a date using multiple allowed formats.
     */
    private LocalDateTime parseOrderDate(String raw) {
        String value = raw == null ? "" : raw.trim();

        // Parsing the Date from all available parsers
        for (DateTimeFormatter fmt : DATE_FORMATS) {
            try {
                return LocalDateTime.parse(value, fmt);
            } catch (DateTimeParseException ignore) {
            }
        }

        throw new IllegalArgumentException("Error Parsing Date ORDERDATE: '" + value + "'"); // Catching the Unparsable date since none formats matched
    }

    // getters
    public int getOrderNumber() {
        return orderNumber;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public double getPriceEach() {
        return priceEach;
    }

    public int getOrderLineNumber() {
        return orderLineNumber;
    }

    public double getSales() {
        return sales;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public int getQuarterId() {
        return quarterId;
    }

    public int getMonthId() {
        return monthId;
    }

    public int getYearId() {
        return yearId;
    }

    public String getProductLine() {
        return productLine;
    }

    public double getMsrp() {
        return msrp;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getTerritory() {
        return territory;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getDealSize() {
        return dealSize;
    }

    @Override
    public String toString() {
        return "SalesRecord{" +
                "orderNumber=" + orderNumber +
                ", sales=" + sales +
                ", customerName='" + customerName + '\'' +
                ", country='" + country + '\'' +
                ", productLine='" + productLine + '\'' +
                ", orderDate=" + orderDate +
                '}';
    }
}