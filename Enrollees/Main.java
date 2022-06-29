package AvailityEnrollments.Enrollees;

public class Main {

  public static void main(String[] args) {

    HandleEnrollees handleEnrollees = new HandleEnrollees();
    handleEnrollees
        .parseCSVData("/Users/nehemiascruz/Availity_Homework/AvailityEnrollments/Enrollees/test_file/enrollee.csv");

  }

}