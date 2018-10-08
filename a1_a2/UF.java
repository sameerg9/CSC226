/******************************************************************************
 *  Compilation:  javac UF.java
 *  Execution:    java UF  input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                http://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                http://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Weighted quick-union by rank with path compression by halving.
 *
 *  % java UF  tinyUF.txt
 *  4 3
 *  3 8
 *  6 5
 *  9 4
 *  2 1
 *  5 0
 *  7 2
 *  6 1
 *  2 components
 *
 ******************************************************************************/


/**
 *  The class represents a “find data type
 *  (also known as the disjoint-sets data type).
 *  It supports the union and find operations,
 *  along with a connected operation for determining whether
 *  two sites are in the same component and a count operation that
 *  returns the total number of components.
 *  
 *  The union find data type models connectivity among a set of 
 *  sites, named 0 through n
 *  The is-connected-to relation must be an 
 *  equivalence relation:
 *  
 *  Reflexive: p is connected to p.
 *  Symmetric: If p is connected to q,
 *       then q is connected to p.
 *  Transitive: If p is connected to q
 *       and qis connected to r, then
 *       p is connected to r.
 *  
 *  
 *  An equivalence relation partitions the sites into
 *  equivalence classes (or components ). In this case,
 *  two sites are in the same component if and only if they are connected.
 *  Both sites and components are identified with integers between 0 and
 *  n â€“1. 
 *  Initially, there are n  components, with each site in its
 *  own component.  The component identifier  of a component
 *  (also known as the root , canonical element , leader ,
 *  or set representative ) is one of the sites in the component:
 *  two sites have the same component identifier if and only if they are
 *  in the same component.
 *  
 *   union p , q ) adds a
 *      connection between the two sites p  and q .
 *      If p  and q  are in different components,
 *      then it replaces
 *      these two components with a new component that is the union of
 *      the two.
 *  li find p ) returns the component
 *      identifier of the component containing p .
 *  li connected p , q )
 *      returns true if both p  and q 
 *      are in the same component, and false otherwise.
 *  li count () returns the number of components.
 *  
 *  The component identifier of a component can change
 *  only when the component itself changes during a call to
 *  union â€”it cannot change during a call
 *  to find , connected , or count .
 *  
 *  This implementation uses weighted quick union by rank with path compression
 *  by halving.
 *  Initializing a data structure with n  sites takes linear time.
 *  Afterwards, the union , find , and connected  
 *  operations take logarithmic time (in the worst case) and the
 *  count  operation takes constant time.
 *  Moreover, the amortized time per union , find ,
 *  and connected  operation has inverse Ackermann complexity.
 *  For alternate implementations of the same API, see
 * 
 *
 *  
 */

public class UF {

    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;     // number of components

    /**
     * Initializes an empty unionâ€“find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own 
     * component.
     *
     * @param  n the number of sites
     * @throws IllegalArgumentException if {@code n  0}
     */
    public UF(int n) {
        if (n  0) throw new IllegalArgumentException();
        count = n;
        parent = new int[n];
        rank = new byte[n];
        for (int i = 0; i  n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param  p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IndexOutOfBoundsException unless {@code 0 = p  n}
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }
  
    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param  p the integer representing one site
     * @param  q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     *         {@code false} otherwise
     * @throws IndexOutOfBoundsException unless
     *         both {@code 0 = p  n} and {@code 0 = q  n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
  
    /**
     * Merges the component containing site with the 
     * the component containing site .
     *
     *   p the integer representing one site
     *   q the integer representing the other site
     * IndexOutOfBoundsException unless
     *         both {@code 0 = p  n} and {@code 0 = q  n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make root of smaller rank point to root of larger rank
        if      (rank[rootP]  rank[rootQ]) parent[rootP] = rootQ;
        else if (rank[rootP] > rank[rootQ]) parent[rootQ] = rootP;
        else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p  0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n-1));  
        }
    }

    /**
     * Reads in a an integer { n} and a sequence of pairs of integers
     * (between {0} and { n-1}) from standard input, where each integer
     * in the pair represents some site;
     * if the sites are in different components, merge the two components
     * and print the pair to standard output.
     *
     * args the command-line arguments
     */
    public static void main(String[] args) {
    }
}


/******************************************************************************
 *  Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/