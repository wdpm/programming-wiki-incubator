package fileio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessDictionaryTest {

    @Test
    void maxLength() {
        ProcessDictionary pd = new ProcessDictionary();
        assertEquals(24, pd.maxLength());
    }
}