import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class fileReader {
    // create a master list of the times we are going to use.
    // we are going to assume the time periods are free periods.
    ArrayList<int[]> times_1;
    ArrayList<int[]> times_2;
    ArrayList<String[]> naturalList;
    int meetingDuration;



    // create the constructor and read in the times
    // using system input redirection
    fileReader(String fileName) {
        times_1 = new ArrayList<>();
        times_2 = new ArrayList<>();
        naturalList = new ArrayList<>();
        boolean isTimes1 = true;
        try {
            Scanner in = new Scanner(new File(fileName));
            meetingDuration = in.nextInt();
            while(in.hasNextLine()) {
                String currLine = in.nextLine();

                if (currLine.length() > 2 && currLine.charAt(0) != '#') { // check for a blank line/comment

                        // if it is in military time and it isn't the case where we are switching
                        if(currLine.charAt(2) != ':' && !currLine.startsWith("====")) {
                            System.err.println("The time format must be in military time.");
                            System.exit(0);
                        }
                        if(currLine.startsWith("====")) {
                            isTimes1 = false;
                            naturalList.add(new String[]{"===="});
                        }
                        else {
                            // for string pairs
                            naturalList.add(new String[] {currLine.substring(0, 5),  currLine.substring(6)});

                            // add the int version of the times to the arraylist
                            timeToInt(currLine, isTimes1);
                        }

                }



            }
        } catch (FileNotFoundException error) {
            System.err.println("There was an issue with the input file");
            System.exit(1);
        }
        sortLists();
    }

    private void timeToInt(String time, boolean time1) {
        // add the pair to times.
        int t1 = Integer.parseInt(time.substring(0, 2) + time.substring(3, 5));
        int t2 = Integer.parseInt(time.substring(6, 8) + time.substring(9, 11));
        if (time1) {
            times_1.add(new int[]{
                    t1,
                    t2});
        }
        else {
            times_2.add(new int[]{
                    t1,
                    t2 });
        }

    }
    public void getLists() {
        System.out.println("First given time set: ");
        for (int[] ints : times_1) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println("Second time set: ");

        for (int[] ints : times_2) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println("String time set: ");
        for(String[] strings : naturalList) {
            System.out.println(Arrays.toString(strings));
        }
    }

    public void sortLists() {
        // todo:
        //  1. check for empty lists on both sides
        //  2. consider meetings to be free time which both parties have at least 30 minutes of space
        //  strategy: two pointer method? O(n) solution
        ArrayList<int[]> res = new ArrayList<>();
        // initialize two pointers.
        int i = 0;
        int j = 0;



        while(i < times_1.size() && j < times_2.size()) {
            // for readability; points to an array in the arraylist.
            int[] currTupleOne = times_1.get(i);
            int[] currTupleTwo = times_2.get(j);
            // ex) 13:00-15:00 -- 14:00-18:00
            // currStartTime = 14:00
            // currEndTime = 15:00
            // therefore so long as the end - start time is longer than a given duration
            // then we say that is a possible meeting duration.

            int currStartTime = Math.max(currTupleOne[0], currTupleTwo[0]);
            int currEndTime = Math.min(currTupleOne[1], currTupleTwo[1]);

            // in this case there is enough room for a meeting (and longer)
            // using the previous example the added interval would [14:00, 15:00]
            if(currEndTime - currStartTime >= meetingDuration) {
                // add it to the results array.
                res.add(new int[] { currStartTime, currEndTime });
                i++;
                j++;
            }

            // otherwise:
            // move to the next tuple of the times list where the curr tuple's end time is less than the other tuple's
            // end time.
            // ex.) tuple 1: 9:00-10:00 | tuple 2: 10:00-11:00 --> no overlapping free time. SO:
            // 10:00 is less than 11:00 --> we need to check the next free slot of times_1.
            // so, now we are working with times_1.get(1) and times_2.get(0).
            // --> currTupleOne = 11:00-12:00 | currTuple2 = 10:00-11:00
            // now we are still without free time overlap, except we increment the pointer for times_2(0)
            // so now we are working with times_1.get(1) and times_2.get(1).

            else if(currTupleOne[1] < currTupleTwo[1]) { // currTuple2's end time is greater than tuple1's end time.
                i++;
            }
            else if (currTupleOne[1] > currTupleTwo[1]) { // currTupleOne's end time is greater than tuple2's end time.
                j++;
            }
            else { // they are equal so lets move over both of them.
                i++;
                j++;
            }
        } // end of while loop

        returnResult(res);



    }
    public void returnResult(ArrayList<int[]> results) {
        System.out.println("Given meeting duration: " + meetingDuration);
        if(!results.isEmpty()) {
            System.out.println("Here are the possible meeting times: ");
            for(int[] tuple : results) {

                String startTimeToString = Integer.toString(tuple[0]);
                String endTimeToString = Integer.toString(tuple[1]);
                String finalStartOutput = "";
                String finalEndOutput = "";

                if(startTimeToString.length() == 4) {
                    finalStartOutput = startTimeToString.substring(0, 2) +":"
                            + startTimeToString.substring(2);
                }
                if(startTimeToString.length() == 3 ) {
                    finalStartOutput = "0" + startTimeToString.charAt(0) +":"
                            + startTimeToString.substring(1);
                }
                if(endTimeToString.length() == 4) {
                    finalEndOutput = endTimeToString.substring(0, 2) + ":" + endTimeToString.substring(2);
                }
                if(endTimeToString.length() == 3) {
                    finalEndOutput = "0" + endTimeToString.charAt(0) + ":" + endTimeToString.substring(1);
                }
                System.out.println("[" + finalStartOutput + ", "
                        + finalEndOutput + "]");

            }
        }
        else {
            System.out.println("There are no possible overlapping free times for the given duration.");
        }
    }



}
