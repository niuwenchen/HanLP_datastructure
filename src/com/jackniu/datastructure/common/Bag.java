package com.jackniu.datastructure.common;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by JackNiu on 2018/1/9.
 */
// 遍历实现Iterable, Bag 类似于Stack
public class Bag<Item>  implements Iterable<Item>
{
    private Node<Item> first;    // beginning of bag
    private int n;               // number of elements in bag

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    public Bag()
    {
        first = null;
        n =0;
    }
    public boolean isEmpty()
    {return first == null;}
    public int  size(){return n;}

    public void add(Item item)
    {
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        n++;
    }

    public Iterator<Item> iterator()  {
        return new ListIterator<Item>(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
//        while (!StdIn.isEmpty()) {
//            String item = StdIn.readString();
//            bag.add(item);
//        }
        bag.add("Java");
        bag.add("niu");

        StdOut.println("size of bag = " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
    }
}
