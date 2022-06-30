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

  // this will hold the unique Insurance company names and we'll use
  // these names to separate our enrollee's according to company
  Set<String> insuranceSet = new HashSet<>();

  public HandleEnrollees() {

  }

  // This method takes in the file, sorts the file, and writes the new file
  // with several helper methods throughout.
  public void parseCSVData(String file) {

    List<List<String>> csvData = new ArrayList<>();

    String line = "";
    String splitBy = ",";

    // Read the contents of the .csv we need to handle
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {

      br.readLine();

      while ((line = br.readLine()) != null) {

        // create a new list for each row in the file
        List<String> enrollee = new ArrayList<>();

        // this array will hold the elements of the current line
        // so that we can add each element to the enrolle list above
        String[] splitEnrollee = new String[1];

        splitEnrollee = line.split(splitBy);

        for (String column : splitEnrollee) {

          // each element being added is essentially a column value
          // as per the standard structure of .csv files
          enrollee.add(column);
        }

        // if the current enrollee dataset just created has an insurance company
        // that doesn't exist in our insurance hashset, we had that insurance in
        insuranceSet.add(enrollee.get(3).toLowerCase());

        // finally, add the new enrollee dataSet to the list of dataSets.
        csvData.add(enrollee);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // create new files according to insurance company
    for (String insurance : insuranceSet) {

      // create a new file according to the insurance company
      File fileCreated = createCSVFile(insurance);

      // Initialize the list of dataSets that will contain all dataSets with the same
      // insurance company
      List<List<String>> specificDataSet = new ArrayList<>();

      // Initialize a hashset that will contain the id's of all the enrollee's. This
      // will allow us to check for duplicates, and arrange the final data to be
      // written to a file according to greatest version number
      Set<String> uniqueIds = new HashSet<>();

      // Alot of lists, I know. THIS list will only contain the names of the all
      // enrollee's
      // already sorted according to insurance companies. This will help us sort
      // enrollee's
      // by last and first name ascending
      ArrayList<String> onlyNames = new ArrayList<>();

      // traverse our unordered/unfiltered list of dataSets
      for (List<String> dataSet : csvData) {

        // check the dataSet's insurance equals the current insurance we're
        // looking way up on line 68
        if (dataSet.get(3).toLowerCase().equals(insurance)) {
          // after sorting by insurance, check the id's, and if the dataset id already
          // exists in our id hashmap, we remove that dataSet from the list later on
          if (!uniqueIds.contains(dataSet.get(0))) {
            // if all checks out, add dataset id to our unique list,
            // and add dataset to our sorted dataset list (sorted according to insurance)
            uniqueIds.add(dataSet.get(0));
            specificDataSet.add(dataSet);
          } else if (uniqueIds.contains(dataSet.get(0))) {

            // the id already exists in the ordered list of datasets
            // so remove that dataset

            for (int i = 0; i < specificDataSet.size(); i++) {
              if (specificDataSet.get(i).get(0).equals(dataSet.get(0))) {
                int one = Integer.valueOf(specificDataSet.get(i).get(2).substring(1, 2));
                int two = Integer.valueOf(dataSet.get(2).substring(1, 2));
                if (one < two) {
                  specificDataSet.remove(i);
                  specificDataSet.add(dataSet);
                }
              }
            }
          }
        }
      }

      // here we begin the process of sorting by last, first name ascending
      // add all sorted and filtered (according to insurance and id) names to
      // this list
      for (int i = 0; i < specificDataSet.size(); i++) {
        onlyNames.add(specificDataSet.get(i).get(1));
      }

      // sort the names with the helper of this helper class/method
      SortEnrollee sortEnrollee = new SortEnrollee();
      sortEnrollee.sortEnrollee(onlyNames);

      // getting ready to populate our new file!
      // check if the file is empty. If it is, add the column names as the
      // first line
      boolean isEmpty = isFileEmpty(fileCreated);
      if (isEmpty) {
        writeCSVData(fileCreated);
      }

      // finally, write the datasets into our new file according to their
      // names
      for (String name : onlyNames) {
        for (int i = 0; i < specificDataSet.size(); i++) {
          if (specificDataSet.get(i).contains(name)) {
            writeCSVData(specificDataSet.get(i), fileCreated);
          }
        }
      }

    }

  }

  // this method checks if our file is empty
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

  // this method writes data to our new files
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

  // this method writes the first line to our new file. The first line will be the
  // column
  // names
  private void writeCSVData(File fileCreated) {

    try (PrintWriter bw = new PrintWriter(new FileWriter(fileCreated, true))) {
      bw.append("\"UserId\",\"FirstAndLastName\",\"Version\",\"InsuranceCompany\"");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // this method creates our files and names them
  // according to the insurance company
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
