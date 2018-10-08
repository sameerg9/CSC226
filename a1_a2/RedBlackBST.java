//
//
//
//
//
//
//
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;
import java.util.Random;





    public class RedBlackBST<Key extends Comparable<Key>, Value> {

    static int reds = 0 ;



    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;     // root of the BST

    // BST helper node data type
    private class Node {
        private Key key;           // key
        private Value val;         // associated data
        private Node left, right;  // links to left and right subtrees
        private boolean color;     // color of parent link
        private int size;
        private int subRedCount;
        private int subBlackCount ;        // subtree count

        public Node(Key key, Value val, boolean color, int size, int subRedCount, int subBlackCount) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
            this.subRedCount = subRedCount;

        }
    }

    public RedBlackBST() {

      root = null;

      reds = -1;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of node in subtree rooted at x; 0 if x is null
    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }

    public int size() {
        return size(root);
    }
   /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }


   /***************************************************************************
    *  Standard BST search.
    ***************************************************************************/

    /**
     * Returns the value associated with the given key.
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");

        //System.out.print("key= "+key +" bool="+ isRed(key) );
        return get(root, key);
    }

    // value associated with the given key in subtree rooted at x; null if no such key
    private Value get(Node x, Key key) {
       // System.out.print(" key= "+key +" bool="+ isRed(x));
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return x.val;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given key?
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {

        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            System.out.println("null value ");
            //delete(key);
            return;
        }

        root = put(root, key, val);
        root.color = BLACK;
        // assert check();
    }

    // insert the key-value pair in the subtree rooted at h
    private Node put(Node h, Key key, Value val) {

        if (h == null)
          {
          //System.out.println("0 abse case hit : node == null ");
          reds = reds + 1 ;
          return new Node(key, val, RED, 1,0, 0 );
          }
        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else              h.val   = val;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    // make a left-leaning link lean to the right
    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        x.subRedCount = h.subRedCount;

        h.size = size(h.left) + size(h.right) + 1;
        //h.subRedCount = RedSz(h.left) + RedSz(h.right) + 1;

        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        //h.subRedCount = RedSz(h.left) + RedSz(h.right) + 1;

        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {

        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;

        if(h.key == root.key){
           reds= reds-2;

        }else{
          reds = reds-1 ;
        }


    }


    private void inOrderTraversal(Node root){


        if(root !=  null) {
            inOrderTraversal(root.left);
                            //Visit the node by Printing the node data
            System.out.println("~ ~" + root.color);

            inOrderTraversal(root.right);
        }

    }
/*
    public  int countRed(Node node) {
         //reds = 0;
        if (node == null) {
            return 0;
         }
        reds += countRed(node.left);
        reds += countRed(node.right);

        if(isRed(node)){
          //System.out.println("Found red node at:" + node.val + "  reds@: "+reds + "  node.subRedCount :" + node.subRedCount);
          reds++;
        }
        return reds;
    }*/

    public float percentRed(){
        
        float r = ((float)reds / (float)size() )*100;
        //System.out.println("ratio at = "+r);
        return(r);
    }
    /**
     * Unit tests the {@code RedBlackBST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {


        RedBlackBST st = new RedBlackBST();


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

        int[] array = new int[inputVector.size()];
        //insertion loop
        for (int i = 0; i < array.length; i++){
            String ss = Integer.toString(i);
            st.put( ss, inputVector.get(i) );



        }
       //System.out.pintln("size "+st.size(root) + " "  );


        System.out.println("\n\n");



        long startTime = System.nanoTime();
        float percent = st.percentRed();
        System.out.println("percent:   "+percent);



       // inOrderTraversal(st.root);

        System.out.printf("Read %d values.\n",array.length);



        
        // calling test on insert ...
        //int kthsmallestindex = LinearSelect(array,k);

        //int kthsmallest = array[kthsmallestindex];

        long endTime = System.nanoTime();

        long totalTime = (endTime-startTime);


        System.out.println("    Reds = " +st.reds +"       ");
       // System.out.printf("The %d-th smallest element in the input list of size %d is %d.\n",k,array.length,kthsmallest);
        System.out.printf("Total Time (nanoseconds): %d\n",totalTime);
        System.out.println("\n" +st.root+"\n");
    }







}