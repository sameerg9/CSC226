//Template is created by Zhuoli Xiao, on Sept. 19st, 2016.
//Only used for Lab 226, 2016 Fall. 
//All Rights Reserved. 

//modified by Rich Little on May 18, 2017

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import java.util.Random;


public class QuickSelect {
	//Function to invoke quickSelect
	public static int QS(int[] S, int k){
        if (S.length==1)
        	return 0;
       
        return quickSelect(0,S.length-1,S,k);


	}
    
    //do quickSelect in a recursive way 
    private static int quickSelect(int left,int right, int[] array, int k){
    	//if there is only one element now, just record.
    	if (left>=right){
    		return left;
    	}
    	//do the partition 
    	int p=pickRandomPivot(left,right);
    	int eIndex=partition(left,right,array,p);
    	
    	//System.out.println("_.     Random pivot selected as:"+p + " _ _ _ _     eIndex selected as :"+eIndex );
    	//after the partition, do following ops
    	if (k<=eIndex){
    		return quickSelect(left,eIndex-1,array,k);
    	}else if(k==eIndex+1){
    		return eIndex;
    	}else{
    		return quickSelect(eIndex+1,right,array,k);
    	}

    }
    //do Partition with a pivot
    private static int partition(int left, int right, int[] array, int pIndex){
    	//move pivot to last index of the array
    	swap(array,pIndex,right);

    	int p=array[right];
    	int l=left;
    	int r=right-1;
  
    	while(l<=r){
    		while(l<=r && array[l]<=p){
    			l++;
    		}
    		while(l<=r && array[r]>=p){
    			r--;
    		}
    		if (l<r){
    			swap(array,l,r);
    		}
    	}

        swap(array,l,right);
       // System.out.println("L="+ l);
    	return l;
    }

    //Pick a random pivot to do the QuickSelect
	private static int pickRandomPivot(int left, int right){
		int index=0;
		Random rand= new Random();
		index = left+rand.nextInt(right-left+1);
		return index;  
	}
	//swap two elements in the array
	private static void swap(int[]array, int a, int b){
 		int tmp = array[a];
		array[a] = array[b];
		array[b] = tmp;
	}


	/* main()
	   Contains code to test the QuickSelect. Nothing in this function
	   will be marked. You are free to change the provided code to test your
	   implementation, but only the contents of the QuickSelect class above
	   will be considered during marking.
	*/
	public static void main(String[] args){
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);
		
		int k = inputVector.get(0);

		int[] array = new int[inputVector.size()-1];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i+1);

		System.out.printf("Read %d values.\n",array.length);


		long startTime = System.nanoTime();

		int kthsmallestindex = QS(array,k);
		
		int kthsmallest = array[kthsmallestindex];

		long endTime = System.nanoTime();

		long totalTime = (endTime-startTime);

		System.out.printf("The %d-th smallest element in the input list of size %d is %d.\n",k,array.length,kthsmallest);
		System.out.printf("Total Time (nanoseconds): %d\n",totalTime);
	}
}
