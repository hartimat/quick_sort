package hartiganhw2sort;


import java.util.Scanner;
import java.io.*;

/**
 * Implementation of Quick Sort algorithm for CS 324 Week 3 homework 
 * assignment Part 2.
 * 
 * @author Matthew Hartigan
 * @version Week 3
 */
public class HartiganHW2sort {

    
    /**
     *  Main method for CS 324 Week 3 Homework Assignment Part 2
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        final int MIN_SIZE = 12;    // minimum allowable input list size
        final int MAX_SIZE = 99;    // maximum allowable input list size
        final String INPUT_FILENAME = "input/unsorted.txt";  
        
        Scanner userInput  = new Scanner(System.in);
        int inputSize = 0;    // holds user input array size
        boolean inputIsValid = false;
        int [] inputArray = null;
        
        // Delcare which sort will be implemented
        System.out.println("This program will implement a Quicksort");
        System.out.println();
        
        // Get user input (check for validity, repeat request as needed)
        do {
            System.out.print("Enter the list size (" + MIN_SIZE + " to " + MAX_SIZE + "): ");
            
            // Collect user input
            inputSize = userInput.nextInt();
            
            // Validate user input
            if (inputSize < MIN_SIZE) {
                System.out.println("Input list size of " + inputSize + " is too small.  Please re-enter.");
            }
           
            else if (inputSize > MAX_SIZE) {
                System.out.println("Input list size of " + inputSize + " is too large.  Please re-enter.");
            } 
            else {
                inputIsValid = true;
            }
          
        } while (! inputIsValid);
        
        // Initialize array and populate with values from input file
        inputArray = new int[inputSize];
        populateArray(INPUT_FILENAME, inputArray);
        
        // Sort the array
        quickSort(inputArray, 0, inputArray.length - 1);
        
        // Output the final result
        System.out.println("Final Sorted List:");
        displayArray(inputArray, 0, inputArray.length - 1);
       
    }
    
    
    /**
     * populateArray
     * Fill array with contents from input file.
     * 
     * @param inputFilename
     * @param inputArray
     */
    public static void populateArray(String inputFilename, int [] inputArray) {
        FileInputStream inputFileObject = null;
        Scanner inputFileScanner  = null;
        
        try {
            // Open input file
            inputFileObject = new FileInputStream(inputFilename);
            inputFileScanner = new Scanner(inputFileObject);
            
            for (int i = 0; i < inputArray.length; ++i) {
                inputArray[i] = inputFileScanner.nextInt();
            }
            
            inputFileObject.close();
        }
        
        catch (FileNotFoundException excpt) {
            System.out.println("Error: " + inputFilename + " could not be found.");
            System.out.println("Exiting program");
            System.exit(1);
        }       
        
        catch (IOException excpt) {
            System.out.println("Error: IOException thrown while attemptint to close " + inputFilename);
            System.out.println("Exiting program");
            System.exit(1);
        }
    }
    
    
    /**
     * quickSort
     * Implements the quick sort algorithm with the following improvements:
     *    1) Switch to selection sort when input array is less than 7 elements long
     *    2) Implement  the median of 3 method for choosing pivot points
     * 
     * @param inputArray
     * @param lowIndex
     * @param highIndex
     */
    public static void quickSort(int [] inputArray, int lowIndex, int highIndex) {
        
        int pivotIndex = 0;
        
        
        // Display call message
        System.out.println("CALL Quicksort for indexes " + lowIndex + " to " + highIndex);
        System.out.println();
        
        // Display list to be sorted
        System.out.print("     ");
        System.out.println("List to sort is:");
        System.out.print("     ");
        displayArray(inputArray, lowIndex, highIndex);
        System.out.println();
        
        // Check if fewer than 7 items remain in list to sort
        // If so, switch to selection sort
        if ( (highIndex - lowIndex) < 1) {
            return;
        }
        
        if ((highIndex - lowIndex + 1) < 7) {
            
            System.out.print("     ");
            System.out.print("     ");
            System.out.println("Less than 7 items, so use selectionSort");
            selectionSort(inputArray, lowIndex, highIndex);
            System.out.print("     ");
            System.out.print("     ");
            System.out.print("Result: ");
            displayArray(inputArray, lowIndex, highIndex);
            System.out.println();
        }
        else {
            // Select and correctly position a pivot point
            medianLeft(inputArray, lowIndex, highIndex);
            
            // Partition the value and display results
            pivotIndex = partition(inputArray, lowIndex, highIndex);
            System.out.print("     ");
            System.out.println("After partitioning:");
            System.out.print("     ");
            System.out.print("     ");
            System.out.println("Pivot in correct position");
            System.out.print("     ");
            System.out.print("     ");
            System.out.print("     ");
           System.out.println("[" + pivotIndex + "]" + inputArray[pivotIndex]);
            System.out.print("     ");
            System.out.print("     ");
            System.out.println("Left Values");
            System.out.print("     ");
            System.out.print("     ");
            System.out.print("     ");
            displayArray(inputArray, lowIndex, pivotIndex - 1);
            System.out.print("     ");
            System.out.print("     ");
            System.out.println("Right Values");
            System.out.print("     ");
            System.out.print("     ");
            System.out.print("     ");
            displayArray(inputArray, pivotIndex + 1, highIndex);
            System.out.print("     ");
            System.out.print("     ");
            System.out.println();
            
            // Recursive call to quick sort left values
            quickSort(inputArray, lowIndex, pivotIndex -1);
            
            // Recursive call to quick sort right values
            quickSort(inputArray, pivotIndex + 1, highIndex);
        }
    }
    
    
    /**
     * selectionSort
     * Implements a standard selection sort algorithm.
     * 
     * @param inputArray
     */
    public static void selectionSort(int [] inputArray, int lowIndex, int highIndex) {
        int remainingListTop = lowIndex;
        int lastItemIndex = highIndex;
        int smallestIndex = 0;
        int compareIndex = 0;
        int swapValue = 0;
        
        // Loop 1
        while(remainingListTop < lastItemIndex) {
            smallestIndex = remainingListTop;
            compareIndex = remainingListTop + 1;
            
            // Loop 2
            while (compareIndex <= lastItemIndex) {
                if (inputArray[compareIndex] < inputArray[smallestIndex]) {
                    smallestIndex = compareIndex;
                }
                ++compareIndex;
            }
                
            // Move smallest value to top of list
            if (smallestIndex != remainingListTop) {
                swapValue = inputArray[smallestIndex];
                inputArray[smallestIndex] = inputArray[remainingListTop];
                inputArray[remainingListTop] = swapValue;
            }
            
            ++remainingListTop;
        }
    }
    
    
    /**
     * medianLeft
     * As part of the quick sort algorithm, medianLeft takes the first, middle, and 
     * last index alues from the input array, finds the median, and places it in the zero
     * index position, with the lowest and highest values following in the middle and
     * last index positions.
     *
     * @param inputArray
     * @param lowIndex
     * @param highIndex
     */
    public static void medianLeft(int [] inputArray, int lowIndex, int highIndex) {
        
        int swapValue = 0;
        int middleIndex = (lowIndex + highIndex) / 2;
        
        // Display message 
        System.out.print("     ");
        System.out.println("Median of 3 (medianLeft):");
        
        // Display message before sort
        System.out.print("     ");
        System.out.print("     ");
        System.out.print("Before sort: ");
        System.out.print("low is [" + lowIndex + "]" + inputArray[lowIndex] + ", ");
        System.out.print("mid is [" + middleIndex + "]" + inputArray[middleIndex] + ", ");
        System.out.print("high is [" + highIndex + "]" + inputArray[highIndex] + ", ");
        System.out.println();        
        
        // Compare low to middle, swap if needed
        if (inputArray[middleIndex] < inputArray[lowIndex]) {
            swapValue = inputArray[middleIndex];
            inputArray[middleIndex] = inputArray[lowIndex];
            inputArray[lowIndex] = swapValue;
        }
        
        // Compare low to high, swap if needed
        if (inputArray[highIndex] < inputArray[lowIndex]) {
            swapValue = inputArray[highIndex];
            inputArray[highIndex] = inputArray[lowIndex];
            inputArray[lowIndex] = swapValue;
        }
        
        // Compare middle to high, swap if needed
        if (inputArray[highIndex] < inputArray[middleIndex]) {
            swapValue = inputArray[highIndex];
            inputArray[highIndex] = inputArray[middleIndex];
            inputArray[middleIndex] = swapValue;
        }
        
        // Display message after sort
        System.out.print("     ");
        System.out.print("     ");
        System.out.print("Before sort: ");
        System.out.print("low is [" + lowIndex + "]" + inputArray[lowIndex] + ", ");
        System.out.print("mid is [" + middleIndex + "]" + inputArray[middleIndex] + ", ");
        System.out.print("high is [" + highIndex + "]" + inputArray[highIndex] + ", ");
        System.out.println();      
        
        // Move median value to first index
        swapValue = inputArray[middleIndex];
        System.out.print("     ");
        System.out.print("     ");
        System.out.print("Swap low and mid to make pivot ");
        System.out.print("[" + lowIndex + "]" + inputArray[middleIndex]);
        System.out.println();
        System.out.println();
        inputArray[middleIndex] = inputArray[lowIndex];
        inputArray[lowIndex] = swapValue;
    }
    
    
    /**
     * partition
     * Executes the partition portion of quick sort algorithm.
     * 
     * @param inputArray
     */
    public static int partition(int [] inputArray, int lowIndex, int highIndex) {
        
        int pivotIndex = lowIndex;
        int leftIndex = lowIndex + 1;
        int rightIndex = highIndex;
        int swapValue = 0;
        
        System.out.print("     ");
        System.out.println("During partitioning:");
        
        while (leftIndex <= rightIndex) {
            
            // Move left pointer
            while ( (inputArray[leftIndex] < inputArray[pivotIndex]) && (leftIndex <= rightIndex)) {
                ++leftIndex;
            }
            
            // Move right pointer
            while ( (inputArray[rightIndex] > inputArray[pivotIndex]) && (rightIndex >= leftIndex)) {
                --rightIndex;
            }
            
            // Swap values if needed
            if (leftIndex < rightIndex) {
                System.out.print("     ");
                System.out.print("     ");
                System.out.print("Swap [" + leftIndex + "]" + inputArray[leftIndex]);
                System.out.print(" and [" + rightIndex + "]" + inputArray[rightIndex]);
                System.out.println();
                swapValue = inputArray[leftIndex];
                inputArray[leftIndex] = inputArray[rightIndex];
                inputArray[rightIndex] = swapValue;
            }

        }
        
        // Move pivot to correct position
        System.out.print("     ");
        System.out.print("     ");
        System.out.print("Swap [" + rightIndex + "]" + inputArray[rightIndex]);
        System.out.print(" and pivot value [" + pivotIndex + "]" + inputArray[pivotIndex]);
        System.out.println();
        System.out.println();
        swapValue = inputArray[rightIndex];
        inputArray[rightIndex] = inputArray[pivotIndex];
        inputArray[pivotIndex] = swapValue;  
        
        return rightIndex;    
        
    }
    
    
    /**
     * displayArray
     * Outputs contents of input array to screen, including pre-appended indices
     * 
     * @param inputArray
     * @param lowIndex
     */
    public static void displayArray(int[] inputArray, int lowIndex, int highIndex) {
        
        // Display entire contents to screen
        for (int i = 0; i < (highIndex - lowIndex) + 1; ++i) {
            System.out.print("[" + (lowIndex + i) + "]" + inputArray[lowIndex + i] + " ");
        }
        System.out.println();
    }
}
