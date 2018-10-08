//Template is created by Zhuoli Xiao, on Sept. 19st, 2016.
//Only used for Lab 226, 2016 Fall. 
//All Rights Reserved. 

//modified by Rich Little on May 18, 2017

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import java.util.Random;


public class quiksel{
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

    	int pp = pickCleverPivot(left, right , array);
    	System.out.println("clever pivot select as: " +pp );

    	int p=pickRandomPivot(left,right);
    	
    	int eIndex=partition(left,right,array,pp);
    	
    	System.out.println("Clever Pivot selected as:"+pp +"_. Random pivot selected as:"+p );
    	
    	


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
        
    	return l;
    }
    private static int getMedianValue(int start, int end, int[] array){
		

		//sort subarray with insertion sort
		// for(int i = start; i <= end; i++){
		// 	int j = i;
		// 	while (j > start && array[j-1] > array[j]){
		// 		swap(array, j, j-1);
		// 		j -= 1;
		// 	}
		// }
    	// int temp[] = new int[array.length]; 
    	// int tempIx = 0 ; 
    	// for(int i = start; i <= end; i++){

    	// 	temp[tempIx] = array[i];
    	// 	tempIx++; 
    	// }

		int med = 0;
		//check if length of subarray is even 
		if ((end - start) % 2 == 0){
			med = ((end - start) / 2) - 1;
		} else {
			med = (end - start) / 2;
		}

		return start + med;
	}

	//pick random clever pivot for the linearSelect
	private static int pickCleverPivot(int left, int right, int[] A){
		
		int n = A.length;


		//base case - if array length less than 5, median
		if ((right - left) < 5){
			return getMedianValue(left, right, A);
		}

		int count = left;

		//divide array into 5 subgroups
		for (int i = left ; i <=  right; i += 5){
			int tempRight = i + 4;
			if (tempRight > right){
				tempRight = right;
			}

			int medSubgroup; 
			
			if((tempRight - i) <= 2){
				continue;
			} else {
				medSubgroup = getMedianValue(i, tRight, A);
			}

			swap(A, medSubgroup, count);
			count++;
		}
		return pickCleverPivot(left, count, A);
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
/////////////
/////////////////////////



//to be linear select java , pick clever pivot 

//Template is created by Zhuoli Xiao, on Sept. 19st, 2016.
//Only used for Lab 226, 2016 Fall. 
//All Rights Reserved. 

//modified by Rich Little on May 18, 2017

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import java.util.Random;


public class LinearSelect{
	//Function to invoke quickSelect
	public static int LinearSelect(int[] S, int k){
        if (S.length==1)
        	return 0;
       
        return LinearSelect(0,S.length-1,S,k);


	}
    
    //do quickSelect in a recursive way 
    private static int LinearSelect(int left,int right, int[] array, int k){
    	//if there is only one element now, just record.
    	if (left>=right){
    		return left;
    	}
    	//do the partition 

    	//int p=pickRandomPivot(left,right);
    	//System.out.println("random pivot selected as : "+p);

    	int pp = pickCleverPivot(left, right, array); 


    	//System.out.println("Clecever pivot selected as :" + pp);

    	int eIndex=partition(left,right,array,pp); //what e
    	//after the partition, do following ops
    	if (k<=eIndex){
    		return LinearSelect(left,eIndex-1,array,k);
    	}else if(k==eIndex+1){
    		return eIndex;
    	}else{
    		return LinearSelect(eIndex+1,right,array,k);
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
    	return l;
    }

    private static int medianFinder(int start, int end , int[] arr){
    	int med = 0 ; 
    	if ( (end-start)%2 == 0 ){ //if the subarray is even
    		med = ((end-start)/2  )-1 ; 
    	}else{
    		med = (end - start)/2; 
    	}

    	return start+med; 
    	

    }

    private static int pickCleverPivot(int left,int right,int[] A){
    	
    	int n  = A.length; 
    	
    	

    	if ( (right - left)  <= 5 ){    //base case, when dealing with problems of 5 
 
    		return medianFinder(left,right,A);
		}


		int tempIx = left ; 

		for(int i = left ; i <= right ; i+=5){ 
			int tempRight = i+4; 
			
			if(tempRight > right)
				tempRight = right; 

		
			int medOfSubArray = medianFinder(i, tempRight ,A); 
			

			swap(A,medOfSubArray,tempIx); 
			tempIx++;

		
		}
		return pickCleverPivot(left, tempIx, A); 


    }

    //pick special pivot 
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

		int kthsmallestindex = LinearSelect(array,k);
		
		int kthsmallest = array[kthsmallestindex];

		long endTime = System.nanoTime();

		long totalTime = (endTime-startTime);

		System.out.printf("The %d-th smallest element in the input list of size %d is %d.\n",k,array.length,kthsmallest);
		System.out.printf("Total Time (nanoseconds): %d\n",totalTime);
	}
}
 





