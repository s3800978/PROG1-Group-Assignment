import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProcessCSV extends Input {
    public static List<List<Object>> process() {
        // Create new 2D list to hold CSV data
        //
        List<List<Object>> data = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        // Try to read file
        try (BufferedReader br = new BufferedReader(new FileReader("src/covid-data.csv"))) {
            String line;

            // While there is still a next line
            while (null != (line = br.readLine())) {

                // Split the line by the "," and add each value to an array
                String[] preValues = line.split(",", 8);

                // Copy values from String array to Object array to store more type of value
                Object[] values = new Object[8];
                System.arraycopy(preValues, 0, values, 0, preValues.length);

                // Fill in 0 for empty values
                for (int i = 0; i < 8; i++) {
                    if (values[i].equals("")) {
                        values[i] = 0;
                    }
                }

                // Convert date into LocalDate format to make easier to calculate and use later
                // Cast numbers into integers
                // Skip the first line
                if (!values[0].toString().equals("iso_code")) {
                    LocalDate formattedDate = LocalDate.parse(values[3].toString(), dateFormatter);
                    values[3] = formattedDate;
                    values[4] = Integer.parseInt(values[4].toString());
                    values[5] = Integer.parseInt(values[5].toString());
                    values[6] = Integer.parseInt(values[6].toString());
                    values[7] = new BigInteger(values[7].toString());
                }


                // Add this array into the List
                data.add(Arrays.asList(values));
            }

            // Throw exception if file not found
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print out formatted content of 2D list
        int lineNo = 1;
        for (List<Object> line : data) {
            System.out.println("\nLine " + lineNo);
            int columnNo = 0;
            for (Object value : line) {
                System.out.println(data.get(0).get(columnNo) + ": " + value);
                columnNo++;
            }
            lineNo++;
        }
        System.out.println("\nCSV file processed successfully");


        return data;
    }

    public static boolean validate(List<List<Object>> data, String to_validate, int column) {
        for (List<Object> line : data) {
            if (to_validate.equals(line.get(column))) {
                System.out.println("Country or continent found.");
                return true;
            }
        }
        return false;
    }

    public List<List<Object>> find_data(List<List<Object>> data, String find_location, LocalDate find_date, int location_column, int date_column) {
        List<List<Object>> data_for_calculation = new ArrayList<>();//put data needed in here and return it to use. Reset after every loop in Display()
        List<Object> temp_metricType;
        for (List<Object> line : data) {
            if (find_location.equals(line.get(location_column))) {
                if (find_date.equals(line.get(date_column))) {
                    if (metricType.equals("positive")) {
                        temp_metricType = Collections.singletonList((int) line.get(4)); //convert java.util.integer to java.util.list to put in an list<object>
                        data_for_calculation.add(temp_metricType);
                    } else if ((metricType.equals("death"))) {
                        temp_metricType = Collections.singletonList((int) line.get(5));
                        data_for_calculation.add(temp_metricType);
                    } else {
                        temp_metricType = Collections.singletonList((int) line.get(6));
                        data_for_calculation.add(temp_metricType);
                    }
                }
            }
        }
        return data_for_calculation;
    }

}
