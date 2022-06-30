package AvailityEnrollments.Enrollees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// This class simply helps us sort according to last name 
// and first name ascending

public class SortEnrollee {

  public SortEnrollee() {
  }

  public void sortEnrollee(ArrayList<String> enrollee) {

    Collections.sort(enrollee, new Comparator<String>() {

      @Override
      public int compare(String o1, String o2) {
        String[] split1 = o1.split(" ");
        String[] split2 = o2.split(" ");
        String lastName1 = split1[1];
        String lastName2 = split2[1];
        int res = lastName1.compareToIgnoreCase(lastName2);
        if (res != 0) {
          return res;
        }
        return split1[0].compareToIgnoreCase(split2[0]);
      }
    });
    System.out.println(enrollee);

  }

}
