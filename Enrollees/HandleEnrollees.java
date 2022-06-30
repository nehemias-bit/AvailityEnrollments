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
import java.util.Set;

public class HandleEnrollees {

  Set<String> insuranceSet = new HashSet<>();

  public HandleEnrollees() {

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

        for (String column : splitEnrollee) {
          enrollee.add(column);
        }

        insuranceSet.add(enrollee.get(3).toLowerCase());

        csvData.add(enrollee);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // create new files according to insurance company

    for (String insurance : insuranceSet) {

      File fileCreated = createCSVFile(insurance);

      List<List<String>> specificDataSet = new ArrayList<>();
      ArrayList<String> onlyNames = new ArrayList<>();

      for (List<String> dataSet : csvData) {

        if (dataSet.get(3).toLowerCase().equals(insurance)) {
          specificDataSet.add(dataSet);
        }
      }

      for (int i = 0; i < specificDataSet.size(); i++) {
        onlyNames.add(specificDataSet.get(i).get(1));
      }

      SortEnrollee sortEnrollee = new SortEnrollee();

      sortEnrollee.sortEnrollee(onlyNames);

      boolean isEmpty = isFileEmpty(fileCreated);

      if (isEmpty) {
        writeCSVData(fileCreated);
      }

      for (String name : onlyNames) {
        for (int i = 0; i < specificDataSet.size(); i++) {
          if (specificDataSet.get(i).contains(name)) {
            writeCSVData(specificDataSet.get(i), fileCreated);
          }
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

      boolean fileEmpty = isFileEmpty(fileCreated);
      if (fileEmpty == false) {
        bw.append("\n");
      }

      bw.append(sb.toString());

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

  // public static void sortEnrollees(ArrayList playerList) {
  // for (int i = 0; i < playerList.size(); i++) {
  // for (int j = 0; j < playerList.size(); j++) {
  // Collections.sort(playerList, new Comparator() {

  // public int compare(Object o1, Object o2) {
  // PlayerStats p1 = (PlayerStats) o1;
  // PlayerStats p2 = (PlayerStats) o2;
  // int res = p1.getPlayerLastName().compareToIgnoreCase(p2.getPlayerLastName());
  // if (res != 0)
  // return res;
  // return p1.getPlayerFirstName().compareToIgnoreCase(p2.getPlayerFirstName())
  // }
  // });
  // }

  // }
  // }

}
