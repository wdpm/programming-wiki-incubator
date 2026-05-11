package defaults;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyEmployeeTest {

    @Test
    void getName() {
        CompanyEmployee peterGibbons = new CompanyEmployee("Peter", "Gibbons");

        assertEquals("Peter Gibbons works for Initech",
                peterGibbons.getName());
    }
}