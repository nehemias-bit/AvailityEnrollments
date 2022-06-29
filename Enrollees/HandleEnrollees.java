package AvailityEnrollments.Enrollees;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HandleEnrollees {

  // create treeset and add insurances to hashset
  HashSet<String> insuranceTree = new HashSet<>();
  // check list for insurances and create new file with corresponding list
  // according to insurance

  public HandleEnrollees() {

    // List<String> csvData =

  }

  public void parseCSVData(String file) {

    List<List<String>> csvData = new ArrayList<>();

    String line = "";
    String splitBy = ",";

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

      br.readLine();

      while ((line = br.readLine()) != null) {

        List<String> enrollee = new ArrayList<>();
        String[] splitEnrollee = new String[1];

        splitEnrollee = line.split(splitBy);

        // System.out.println("splitEnrollee: " + Arrays.asList(splitEnrollee));
        for (String column : splitEnrollee) {
          enrollee.add(column);
        }

        insuranceTree.add(enrollee.get(3).toLowerCase());

        System.out.println("insuranceTree: " + insuranceTree);
        csvData.add(enrollee);
        // System.out.println(csvData);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (List<String> dataSet : csvData) {

      // writeCSVData(dataSet);
    }
  }

}
