package com.orangehrm.data;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class CsvDataProvider {

    private CsvDataProvider() {
    }

    @DataProvider(name = "loginScenarios")
    public static Object[][] loginScenarios() throws IOException, CsvException {
        List<LoginTestData> rows = new ArrayList<>();

        try (InputStream input = CsvDataProvider.class.getClassLoader()
                .getResourceAsStream("login-test-data.csv")) {
            if (input == null) {
                throw new IllegalStateException("login-test-data.csv not found on classpath");
            }

            try (CSVReader reader = new CSVReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                List<String[]> records = reader.readAll();
                for (int i = 1; i < records.size(); i++) {
                    String[] row = records.get(i);
                    rows.add(new LoginTestData(
                            row[0],
                            row[1],
                            blankToNull(row[2]),
                            blankToNull(row[3]),
                            row[4],
                            row[5],
                            row[6],
                            row[7]
                    ));
                }
            }
        }

        Object[][] data = new Object[rows.size()][1];
        for (int i = 0; i < rows.size(); i++) {
            data[i][0] = rows.get(i);
        }
        return data;
    }

    private static String blankToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value;
    }
}
