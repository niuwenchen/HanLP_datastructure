package com.jackniu.datastructure.graph.undirected_graph;

/**
 * Created by JackNiu on 2018/1/8.
 */

import com.jackniu.datastructure.common.Bag;
import com.jackniu.datastructure.common.In;

import java.awt.print.PrinterGraphics;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac Graph.java
 *  Execution:    java Graph input.txt
 *  Dependencies: Bag.java Stack.java In.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  A graph, implemented using an array of sets.
 *  Parallel edges and self-loops allowed.
 *
 *  % java Graph tinyG.txt
 *  13 vertices, 13 edges
 *  0: 6 2 1 5
 *  1: 0
 *  2: 0
 *  3: 5 4
 *  4: 5 6 3
 *  5: 3 4 0
 *  6: 0 4
 *  7: 8
 *  8: 7
 *  9: 11 10 12
 *  10: 9
 *  11: 9 12
 *  12: 11 9
 *
 *  % java Graph mediumG.txt
 *  250 vertices, 1273 edges
 *  0: 225 222 211 209 204 202 191 176 163 160 149 114 97 80 68 59 58 49 44 24 15
 *  1: 220 203 200 194 189 164 150 130 107 72
 *  2: 141 110 108 86 79 51 42 18 14
 *  ...
 *
 ******************************************************************************/

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private Bag<Integer>[]  adj;

    public Graph(int V)
    {
        if (V<0) throw new IllegalArgumentException("Number");
        this.V=V;
        this.E =0;
        adj = (Bag<Integer>[])  new Bag[V];
        for (int v =0;v<V;v++)
        {
            adj[v] = new Bag<Integer>();
        }
    }
    // 初始化一个图从 特定的文件
    public Graph(In in)
    {
        try{
            this.V = in.readInt();
            if(V<0 ) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");;
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v =0;v<V;v++)
            {
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if(E<0) throw new IllegalArgumentException("number of edges in a Graph must be nonnegative");
            for (int i=0;i<E;i++)
            {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        }catch (NoSuchElementException e)
        {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }

    /**
     * Initializes a new graph that is a deep copy of {@code G}.
     *
     * @param  G the graph to copy
     */
    public Graph(Graph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Integer> reverse = new Stack<Integer>();
            for (int w : G.adj[v]) {
                reverse.push(w);
            }
            for (int w : reverse) {
                adj[v].add(w);
            }
        }
    }
    public int V(){
        return V;
    }
    public int E(){
        return  E;
    }
    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    public void addEdge(int v,int w)
    {
//        System.out.println("v:-->"+v+"  w--->"+w);
        validateVertex(v);
        validateVertex(w);
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> adj(int v)
    {
        validateVertex(v);
        return adj[v];
    }
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
    public static void main(String[] args) {
        String path ="E:\\kafkamanager\\HanLP_datastructure\\src\\com\\jackniu\\datastructure\\graph\\undirected_graph\\tinyG.txt";
        In in = new In(path);
        Graph G = new Graph(in);
        System.out.println(G);
        for (int w : G.adj[0]) {
            System.out.println(w);
        }
    }


}
