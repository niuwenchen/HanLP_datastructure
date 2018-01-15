package com.jackniu.datastructure.graph.undirected_graph;

/**
 * Created by JackNiu on 2018/1/9.
 */

import com.jackniu.datastructure.common.In;
import com.jackniu.datastructure.common.StdOut;

import java.awt.print.PrinterGraphics;
import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac DepthFirstPaths.java
 *  Execution:    java DepthFirstPaths G s
 *  Dependencies: Graph.java Stack.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run depth first search on an undirected graph.
 *  Runs in O(E + V) time.
 *
 *  %  java Graph tinyCG.txt
 *  6 8
 *  0: 2 1 5
 *  1: 0 2
 *  2: 0 1 3 4
 *  3: 5 4 2
 *  4: 3 2
 *  5: 3 0
 *
 *  % java DepthFirstPaths tinyCG.txt 0
 *  0 to 0:  0
 *  0 to 1:  0-2-1
 *  0 to 2:  0-2
 *  0 to 3:  0-2-3
 *  0 to 4:  0-2-3-4
 *  0 to 5:  0-2-3-5
 *
 ******************************************************************************/
/*
* 发现最深的路径, 深度搜索路径，只需要将深度搜索的路径保存下来即可。
* */
public class DepthFirstPaths {
    private boolean[] marked;
    private int[]  edgeTo;
    private final int s;

    public DepthFirstPaths(Graph G, int s){
        this.s = s;
        edgeTo = new int [G.V()];   // 13
        marked = new boolean[G.V()];  // 13
        validateVertex(s);
        dfs(G,s);
    }
    public void dfs(Graph G,int v)
    {
        marked[v]=true;
        for (int w: G.adj(v))
        {
            if (!marked[w]){
                edgeTo[w]=v;
                /*
                * 0开始搜索，6，2，1，5
                * edgeTo[6]=0,
                * 开始搜索6: 0,4, edgeTo[4]=6
                * 开始搜索4: 5,6,3
                *
                * */
                dfs(G,w);
            }
        }
    }
    public boolean hasPathTo(int v)
    {
        validateVertex(v);
        return marked[v];
    }

    //EdgeTo 保存的就是路径的联系

    public Iterable<Integer> pathTo(int v)
    {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        /*
        * edgeTo[6]=0
        * edgeTo[4]=6
        * edgeTo[5]=4
        * edgeTo[3]=5
        * 0--3 就是 0,6,5,4,3
        * */
        for(int x=v;x != s;x=edgeTo[x])
            path.push(x);
        path.push(s);
        return path;

    }



    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }


    public static void main(String[] args) {
        String path ="E:\\kafkamanager\\HanLP_datastructure\\src\\com\\jackniu\\datastructure\\graph\\undirected_graph\\tinyG.txt";
        String s1="0";
        In in = new In(path);
        Graph G = new Graph(in);
        int s = Integer.parseInt(s1);
        DepthFirstPaths dfs = new DepthFirstPaths(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (dfs.hasPathTo(v)) {
                StdOut.printf("%d to %d:  ", s, v);
                for (int x : dfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("-" + x+'-');
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d:  not connected\n", s, v);
            }

        }
    }
}
