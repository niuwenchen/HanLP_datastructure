package com.jackniu.datastructure.graph.undirected_graph;

/**
 * Created by JackNiu on 2018/1/9.
 */

import com.jackniu.datastructure.common.In;
import com.jackniu.datastructure.common.StdOut;

/******************************************************************************
 *  Compilation:  javac DepthFirstSearch.java
 *  Execution:    java DepthFirstSearch filename.txt s
 *  Dependencies: Graph.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  % java DepthFirstSearch tinyG.txt 0
 *  0 1 2 3 4 5 6
 *  NOT connected
 *
 *  % java DepthFirstSearch tinyG.txt 9
 *  9 10 11 12
 *  NOT connected
 *
 ******************************************************************************/
public class DepthFirstSearch {
    private boolean[] marked;    // marked[v] = is there an s-v path?
    private int count;           // number of vertices connected to s
    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private void dfs(Graph G,int v)
    {
        count++;
        marked[v]=true;
        for(int w:G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }
    public boolean marked(int v) {
        validateVertex(v);
        return marked[v];
    }
    public int count() {
        return count;
    }
    public static void main(String[] args) {
        String path ="E:\\kafkamanager\\HanLP_datastructure\\src\\com\\jackniu\\datastructure\\graph\\undirected_graph\\tinyG.txt";
        String s1="0";
        In in = new In(path);
        Graph G = new Graph(in);
        int s = Integer.parseInt(s1);
        DepthFirstSearch search = new DepthFirstSearch(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (search.marked(v))
                StdOut.print(v + " ");
        }

        StdOut.println();
        if (search.count() != G.V()) StdOut.println("NOT connected");
        else                         StdOut.println("connected");
    }

}
