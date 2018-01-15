package com.jackniu.datastructure.graph.undirected_graph;

/**
 * Created by JackNiu on 2018/1/10.
 */


import com.jackniu.datastructure.common.In;
import com.jackniu.datastructure.common.Queue;
import com.jackniu.datastructure.common.StdOut;

import java.util.Stack;

/******************************************************************************
 *  Compilation:  javac BreadthFirstPaths.java
 *  Execution:    java BreadthFirstPaths G s
 *  Dependencies: Graph.java Queue.java Stack.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/41graph/tinyCG.txt
 *                https://algs4.cs.princeton.edu/41graph/tinyG.txt
 *                https://algs4.cs.princeton.edu/41graph/mediumG.txt
 *                https://algs4.cs.princeton.edu/41graph/largeG.txt
 *
 *  Run breadth first search on an undirected graph.
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
 *  %  java BreadthFirstPaths tinyCG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (1):  0-1
 *  0 to 2 (1):  0-2
 *  0 to 3 (2):  0-2-3
 *  0 to 4 (2):  0-2-4
 *  0 to 5 (1):  0-5
 *
 *  %  java BreadthFirstPaths largeG.txt 0
 *  0 to 0 (0):  0
 *  0 to 1 (418):  0-932942-474885-82707-879889-971961-...
 *  0 to 2 (323):  0-460790-53370-594358-780059-287921-...
 *  0 to 3 (168):  0-713461-75230-953125-568284-350405-...
 *  0 to 4 (144):  0-460790-53370-310931-440226-380102-...
 *  0 to 5 (566):  0-932942-474885-82707-879889-971961-...
 *  0 to 6 (349):  0-932942-474885-82707-879889-971961-...
 *
 ******************************************************************************/

// 宽度优先搜索算法
public class BreadthFirstPaths {
    private static  final int INFINITY=0;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public BreadthFirstPaths(Graph G,int s){
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo =  new int[G.V()];
        bfs(G,s);
    }

    public BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = INFINITY;
        validateVertices(sources);
        bfs(G, sources);
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    private void validateVertices(Iterable<Integer> vertices) {
        if (vertices == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int V = marked.length;
        for (int v : vertices) {
            if (v < 0 || v >= V) {
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
            }
        }
    }
    private void  bfs(Graph G,int s)
    {
        Queue<Integer> q = new Queue<Integer>() ;
        for (int v=0;v<G.V();v++)
            distTo[v]=INFINITY;
        distTo[s]=0;
        marked[s]=true;
        q.enqueue(s); // 入队

        while(!q.isEmpty()){
            int v = q.dequeue();  // 出队
            for(int w:G.adj(v)){
                if (!marked[w]) {
                    edgeTo[w] = v;     // 0, 6,2,1,5 ,[6,2,1,5]的距离都是1
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }

        }
    }
    private  void bfs(Graph G,Iterable<Integer> sources){
        Queue<Integer> q = new Queue<>();
        for(int s:sources){
            marked[s]= true;
            distTo[s]= 0;
            q.enqueue(s);
        }
        while(!q.isEmpty()){
            int v = q.dequeue();
            for(int w:G.adj(v)){
                if(!marked[w]){
                    edgeTo[w] =v;
                    distTo[w] =distTo[v]+1;
                    marked[w]= true;
                    q.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return marked[v];
    }

    public int distTo(int v){
        validateVertex(v);
        return distTo[v];
    }
    public Iterable<Integer> pathTo(int v)
    {
        validateVertex(v);
        if(!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for(x=v; distTo[x] !=0; x= edgeTo[x]){
            System.out.println(x);
            path.push(x);
        } // 0, x=3, x= edgeTo[9] = 上一层
        //
        path.push(x);
        return path;
    }
    public static void main(String[] args) {
        String path ="E:\\kafkamanager\\HanLP_datastructure\\src\\com\\jackniu\\datastructure\\graph\\undirected_graph\\tinyG.txt";
        String s1="0";
        In in = new In(path);
        Graph G = new Graph(in);
        // StdOut.println(G);

        int s = Integer.parseInt(s1);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        System.out.println(bfs.hasPathTo(11));
        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("- " + x+' ');
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }
//
        }
    }


}
