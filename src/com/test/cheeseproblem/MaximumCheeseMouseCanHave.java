package com.test.cheeseproblem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.Stream;

public class MaximumCheeseMouseCanHave {

    private static int maxSum = Integer.MIN_VALUE;
    private static final int NUMBER_OF_TEST_CASES = 200;
    private static final int NUMBER_OF_MAX_INPUTS_IN_TEST_CASE = 200;
    private static final int MAX_VALUE_OF_ELEMENT_IN_TEST_CASE = 10000;

    public static void main(String[] args) {
        // for taking count of test-cases
        System.out.println("Please enter the number of test cases");
        int T = getAndValidateInputParameter(NUMBER_OF_TEST_CASES, "T value should be between 1 and 200 both inclusive");

        // for storing the input array for each test-case
        List<int[]> a = new ArrayList<>();
        for (int i = 0; i < T; i++) {
            System.out.println("Please enter the number of elements to include in the test case");
            int n = getAndValidateInputParameter(NUMBER_OF_MAX_INPUTS_IN_TEST_CASE, "Value of n is expected between 1 and 1000 both inclusive");
            a.add(getTestCaseArrayFromInput(n));
        }
        // calculating the max cheese that mouse can have for each test-case one by one
        for (int i = 0; i < T; i++) {
            maxSum = Integer.MIN_VALUE;
            maxCheeseMouseCanHave(a.get(i), 0);
            System.out.println(maxSum);
        }
    }

    private static int getAndValidateInputParameter(int upperBoundary, String exceptionMessage) {
        int T = getIntInput();
        if (!(T >= 1 && T <= upperBoundary)) {
            throw new CheeseProblemInputDataMismatchException(exceptionMessage);
        }
        return T;
    }

    private static int getIntInput() {
        //Assuming user will input only integer when this method is called else will throw number format exception
        return Integer.parseInt(getStringInput());
    }

    private static String getStringInput() {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (true) {
            try {
                if ((line = r.readLine()) != null) return line;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static int[] getTestCaseArrayFromInput(int size) {
        System.out.println("Enter test array elements ");
        String[] inputArrStr = getStringInput().split("\\s");
        //Assuming user will not input items other than integer
        // else will result in NumberFormatException
        int[] inputArr = Stream.of(inputArrStr)
                .mapToInt(Integer::parseInt)
                .filter(maxAllowedElementInTestCase)
                .toArray();

        validateIfAllInputDataForTestCaseIsValid(size, inputArrStr, inputArr);
        return inputArr;

    }

    private static IntPredicate maxAllowedElementInTestCase =
            element -> 1 <= element && element <= MAX_VALUE_OF_ELEMENT_IN_TEST_CASE;


    private static void validateIfAllInputDataForTestCaseIsValid(int size, String[] inputArrStr, int[] inputArr) {
        if (inputArrStr.length != size) {
            throw new CheeseProblemInputDataMismatchException("Size of array does not match the expected size for this input.");
        } else if (inputArr.length != inputArrStr.length) {
            throw new CheeseProblemInputDataMismatchException("Some inputs have been filtered out as they could not meet filter conditions.");
        }
    }

    private static void maxCheeseMouseCanHave(int[] subList, int count) {
        // if arr is null or empty , then updating the current max weight count if it is
        // less than maxSum i.e already calculated sum considering different options
        if (Objects.isNull(subList) || subList.length == 0) {
            if (maxSum < count)
                maxSum = count;
        } else {
            // if sub-array size becomes 2 , choosing the max from available 2 options
            if (subList.length == 2) {
                count += Math.max(subList[0], subList[1]);
                maxCheeseMouseCanHave(null, count);
            } else if (subList.length == 1) { // if sub-array size becomes 1, choose
                count += subList[0];
                maxCheeseMouseCanHave(null, count);
            } else {
                // dividing arr to sub-array considering 0th index as 1st element in the path and recursively
                // calling it till there is 0 element left in the sub-array
                int[] subSubList = Arrays.copyOfRange(subList, 2, subList.length);
                maxCheeseMouseCanHave(subSubList, count + subList[0]);

                // dividing arr to sub-array considering 1st index as 1st element in the path and recursively
                // calling it till there is 0 element left in the sub-array
                int[] subList2 = Arrays.copyOfRange(subList, 3, subList.length);
                maxCheeseMouseCanHave(subList2, count + subList[1]);
            }
        }
    }
}
