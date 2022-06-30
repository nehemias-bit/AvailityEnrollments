package AvailityEnrollments.Enrollees;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class HandleEnrollees {

  // create treeset and add insurances to hashset
  HashSet<String> insuranceSet = new HashSet<>();
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

        insuranceSet.add(enrollee.get(3).toLowerCase());

        // System.out.println("insuranceTree: " + insuranceSet);
        csvData.add(enrollee);
        // System.out.println(csvData);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // create new files according to insurance company

    for (String insurance : insuranceSet) {

      File fileCreated = createCSVFile(insurance);
      // System.out.println(fileCreated);

      for (List<String> dataSet : csvData) {
        // System.out.println("dataSet.get(3) " + dataSet.get(3) + " insurance " +
        // insurance);
        if (dataSet.get(3).toLowerCase().equals(insurance)) {

          // System.out.println("calling writeCSVData with " + dataSet);
          boolean isEmpty = isFileEmpty(fileCreated);
          // System.out.println("is empty before write is called: " + isEmpty);
          if (isEmpty) {
            writeCSVData(fileCreated);
          }

          writeCSVData(dataSet, fileCreated);
        }
      }

    }
  }

  private boolean isFileEmpty(File fileCreated) {
    try {

      BufferedReader br = new BufferedReader(new FileReader(fileCreated.getAbsolutePath()));
      if (br.readLine() == null) {
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  private void writeCSVData(List<String> dataSet, File fileCreated) {

    try (PrintWriter bw = new PrintWriter(new FileWriter(fileCreated, true))) {

      String delim = ",";
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < dataSet.size(); i++) {

        sb.append(dataSet.get(i));
        if (i != dataSet.size() - 1) {
          sb.append(delim);
        }
      }

      // System.out.println(sb.toString());
      System.out.println("calling writeCSVData with " + dataSet);

      boolean fileEmpty = isFileEmpty(fileCreated);
      System.out.println("fileEmpty: " + fileEmpty);
      if (fileEmpty == false) {
        bw.append("\n");
        System.out.println("new line");
      }
      bw.append(sb.toString());

      // System.out.println("done");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void writeCSVData(File fileCreated) {

    try (PrintWriter bw = new PrintWriter(new FileWriter(fileCreated, true))) {
      bw.append("\"UserId\",\"FirstAndLastName\",\"Version\",\"InsuranceCompany\"");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private File createCSVFile(String insurance) {

    StringBuffer csvFileName = new StringBuffer();
    csvFileName.append(insurance.toUpperCase());
    csvFileName.append("_Enrollees.csv");

    File csvFile = new File(csvFileName.toString().replace("\"", ""));

    try {
      csvFile.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return csvFile;

  }

}
