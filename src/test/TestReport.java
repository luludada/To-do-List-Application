import model.Report;
import model.UrgentItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestReport {
    Report report;

    @BeforeEach
    void setUp() {
        report = new Report();
    }

    @Test
    void testUpdate() {
        UrgentItem testItem = new UrgentItem("Event", "08/30/19");
        report.update(testItem);
    }

//    @Test
//    void testFiveItemUpdate() {
//        UrgentItem testItem = new UrgentItem("Event", "July 20, 2020");
//        for (int i = 0; i < 5; i++) {
//            report.update(testItem);
//        }
//        report.update(testItem);
//    }
}
