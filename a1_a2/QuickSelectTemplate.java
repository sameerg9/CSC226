/**Template created by Zhuoli Xiao, on Sept. 19th, 2016.
 * Only used for Lab 226, 2016 Fall. 
 * All Rights Reserved.
 * 
 * modified by Rich Little on Sept. 23, 2016
 * modified by Rich Little on May 12, 2017
 * modified by Priya Angara on May 17, 2017
 */

import java.util.Random;


public class QuickSelectTemplate{
	//Function to invoke quickSelect
	public static int QS(int[] S, int k){
        if (S.length==1)
        	return S[0];
       
        return quickSelect(0,S.length-1,S,k);


	}
    
    //do quickSelect in a recursive way 
    private static int quickSelect(int left,int right, int[] array, int k) {
    	
      //base case
      if(array[left]<= array[right])
        return array[left];

      //chose pivot
      int pIx = pickRandomPivot(left,right);

      //partition with selected pivot
      int eIx = partition(left,right,array,pIx);



      if(k <= eIx)
        return quickSelect(left,eIx-1,array, k );
      
      else if(k == (eIx+1) )
        return array[eIx];
      
      else
        return quickSelect(eIx+1, right , array, k);



      

    }
    
    //do Partition with a pivot
    private static int partition(int left, int right, int[] array, int pIndex){

      swap(array,pIndex,array.length); //swap pivot with last element 
      int p = array[right] ; 
      int l = left; 
      int r = right-1; 

      while(l <= r){
        while( (l<=r) && (array[l]<= p) ){
          l = l+1; 
        }

        while( (l<= r) && (array[r] => p) ){
          r= r-1; 
        }

        

        if(l<=r){
          swap(array, l , r );
        }
      
      swap(array, l ,right); 

      return l ; 

        

      }
    	





    	return 0;
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


	//Our main function to test the algorithm
	public static void main (String[] args){
		//array one has duplicate elements
  		int [] array1 ={12,13,17,14,21,3,4,9,21,8};
  		int [] array2 ={14,8,22,18,6,2,15,84,13,12};
  		int [] array3 ={6,8,14,18,22,2,12,13,15,84};
   
  		System.out.println(QS(array1,5));
  		System.out.println(QS(array2,7));
  		System.out.println(QS(array3,5));  
	}
}
