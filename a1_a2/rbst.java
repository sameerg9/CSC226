/* Grace Kang
   CSC 226
   Assignment 2 - Programming
*/


import java.util.*;
import java.io.File;

public class rbst<Key extends Comparable<Key>, Value>{

    private Node root;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private int reds;
    private static int count;

    rbst(){
      root = null;
      reds = -1; //root node cannot be red
    }

    private class Node{
      int key;
      Node left, right;
      int n;
      boolean color;

      Node(int key, int n, boolean color){
        this.key = key;
        this.n = n;
        this.color = color;
      }
    }

    private int size(){
      return size(root);
    }

    private int size(Node x){
      if (x == null) return 0;
      else return x.n;
    }

    private boolean isRed(Node x){
      if (x == null) return false;
      return x.color == RED;
    }

    public void put(int key){
      root = put(root, key);
      root.color = BLACK;
    }

    private Node put(Node h, int key){
      if (h == null){
        reds++;
        return new Node(key, 1, RED);
      }
      if (key < h.key) h.left = put(h.left, key);
      else if (key > h.key) h.right = put(h.right, key);
      else h.key = key;

      if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
      if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
      if (isRed(h.left) && isRed(h.right)) flipColors(h);

      h.n = size(h.left) + size(h.right) +1;
      return h;
    }

    Node rotateLeft(Node h){
      Node x = h.right;
      h.right = x.left;
      x.left = h;
      x.color = h.color;
      h.color = RED;
      x.n = h.n;
      h.n = 1 + size(h.left) + size(h.right);
      return x;
    }
    Node rotateRight(Node h){
      Node x = h.left;
      h.left = x.right;
      x.right = h;
      x.color = h.color;
      h.color = RED;
      x.n = h.n;
      h.n = 1 + size(h.left) + size(h.right);
      return x;
    }

    void flipColors(Node h){
      h.color = RED;
      h.left.color = BLACK;
      h.right.color = BLACK;
      if(root.key == h.key){
        reds-=2;
      } else {
        reds--;
      }
    }

    public double percentRed(){
      return ((double)reds/size())*100;
    }

    public static void main(String[] args){
      Scanner s;
      Random r;
      int v;
      Vector<Integer> inputVector = new Vector<Integer>();

  		if (args.length > 0){
  			try{
  			  s = new Scanner(new File(args[0]));

          System.out.printf("Reading input values from %s.\n",args[0]);

      		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
      			inputVector.add(v);

          rbst rbtree = new rbst();

          for (int j = 0; j < inputVector.size(); j++){
      			rbtree.put(inputVector.get(j));
          }

          System.out.println("Percentage of red nodes: " + rbtree.percentRed() + "%");

  			} catch(java.io.FileNotFoundException e){
  				System.out.printf("Unable to open %s\n",args[0]);
  				return;
  			}
  		}else{

        int n;
        s = new Scanner(System.in);
        System.out.println("Enter the number of random integers to be inserted into a red black tree");
        n = s.nextInt();
        r = new Random();
        rbst rbtree = new rbst();

        double sum = 0;
        for (int i = 0; i < 100; i++){
          for (int k = 0; k < n; k++){
            inputVector.add(r.nextInt(n+1));
          }
          for (int j = 0; j < inputVector.size(); j++){
      			rbtree.put(inputVector.get(j));
          }
          sum += rbtree.percentRed();
          System.out.println("Percentage of red nodes: " + rbtree.percentRed() + "%");

        }
        System.out.println("Average percentage: " + sum/10 + "%");
        System.out.println(rbtree.reds);
  		}
    }
}
