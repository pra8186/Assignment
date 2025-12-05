import org.example.SalesRecord;
import org.example.SalesService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public class SalesServiceTest {

    private SalesService salesService;

    @Before
    public void setUp() throws Exception {
        salesService = new SalesService();

        // Creating Sample SakesRecord
        List<SalesRecord> testRecords = new ArrayList<SalesRecord>();

        // Record 1: 2003, Customer A
        testRecords.add(new SalesRecord(new String[]{
                "10100", "30", "100.0", "1", "3000.0",
                "2/24/2003 0:00", "Shipped", "1", "2", "2003",
                "Motorcycles", "120.0", "S10_1678", "Alpha", "123456",
                "Addr1", "", "CityA", "StateA", "11111", "USA", "NA",
                "Smith", "John", "Small"
        }));

        // Record 2: 2003, Customer B
        testRecords.add(new SalesRecord(new String[]{
                "10101", "20", "200.0", "2", "4000.0",
                "5/10/2003 0:00", "Shipped", "2", "5", "2003",
                "Classic Cars", "220.0", "S10_1949", "Beta Inc", "654321",
                "Addr2", "", "CityB", "StateB", "22222", "USA", "NA",
                "Doe", "Jane", "Medium"
        }));

        // Record 3: 2004, Customer A again
        testRecords.add(new SalesRecord(new String[]{
                "10102", "10", "150.0", "1", "1500.0",
                "3/15/2004 0:00", "Cancelled", "1", "3", "2004",
                "Trucks and Buses", "180.0", "S10_2016", "Alpha", "999999",
                "Addr1", "", "CityA", "StateA", "11111", "USA", "NA",
                "Smith", "John", "Large"
        }));

        // Inject testRecords into private field 'records' using reflection
        Field recordsField = SalesService.class.getDeclaredField("records");
        recordsField.setAccessible(true);
        recordsField.set(salesService, Collections.unmodifiableList(testRecords));
    }

    @Test
    public void testGetTotalSales() {
        double total = salesService.getTotalSales();
        // 3000.0 + 4000.0 + 1500.0 = 8500.0
        assertEquals(8500.0, total, 0.0001);
    }

    @Test
    public void testGetSalesByYear() {
        Map<Integer, Double> salesByYear = salesService.getSalesByYear();

        // We expect:
        // 2003 -> 3000.0 + 4000.0 = 7000.0
        // 2004 -> 1500.0
        assertEquals(2, salesByYear.size());
        assertEquals(7000.0, salesByYear.get(2003), 0.0001);
        assertEquals(1500.0, salesByYear.get(2004), 0.0001);
    }

    @Test
    public void testGetTopCustomersByRevenueLimit1() {
        List<Map<String, Object>> top1 = salesService.getTopCustomersByRevenue(1);

        assertEquals(1, top1.size());
        Map<String, Object> first = top1.get(0);
        // Alpha  has 3000.0 + 1500.0 = 4500.0
        // Beta Inc has 4000.0
        // So Alpha  should be top
        assertEquals("Alpha", first.get("customerName"));
        assertEquals(4500.0, (Double) first.get("totalSales"), 0.0001);
    }

    @Test
    public void testGetTopCustomersByRevenueLimit2() {
        List<Map<String, Object>> top2 = salesService.getTopCustomersByRevenue(2);

        assertEquals(2, top2.size());

        Map<String, Object> first = top2.get(0);
        Map<String, Object> second = top2.get(1);

        // First should be Alpha  (4500.0), then Beta Inc (4000.0)
        assertEquals("Alpha", first.get("customerName"));
        assertEquals(4500.0, (Double) first.get("totalSales"), 0.0001);

        assertEquals("Beta Inc", second.get("customerName"));
        assertEquals(4000.0, (Double) second.get("totalSales"), 0.0001);
    }

    @Test
    public void testGetAverageSalesByStatus() {
        Map<String, Double> avgByStatus = salesService.getAverageSalesByStatus();

        // Statuses:
        // "Shipped": 3000.0, 4000.0 -> avg = 3500.0
        // "Cancelled": 1500.0 -> avg = 1500.0
        assertEquals(2, avgByStatus.size());
        assertEquals(3500.0, avgByStatus.get("Shipped"), 0.0001);
        assertEquals(1500.0, avgByStatus.get("Cancelled"), 0.0001);
    }

    @Test
    public void testGetAllRecords() {
        List<SalesRecord> all = salesService.getAllRecords();
        assertEquals(3, all.size());

        // Ensure it’s unmodifiable
        try {
            all.add(null);
            fail("modifying getAllRecords result not supported");
        } catch (UnsupportedOperationException ignore) {
        }
    }
}
