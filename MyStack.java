package cmsc256;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EmptyStackException;
import java.util.Scanner;

import bridges.base.SLelement;

public class MyStack<E> implements StackInterface<E> {
    private SLelement<E> top;
    private int size;

    /**
     * stack default constructor
     */
    public MyStack() {
        top = null;
        size = 0;

    }

    /**
     * stack parameterized constructor
     * @param size
     */
    public MyStack(int size) {
        top = null;
        size = 0;
    }

    /**
     * adds an element to the stack
     * @param newEntry An object to be added to the stack.
     */
    public void push(E newEntry) {
        if (newEntry == null) {
            throw new IllegalArgumentException("cannot push null element");
        }
        top = new SLelement<E>(newEntry, top);
        size++;
    }


    /**
     * removes an element of the stack
     * @return the element removed from the stack
     */
    @Override
    public E pop() {
        if (top == null) {
            throw new EmptyStackException();
        }
        E it = top.getValue();
        top = top.getNext();
        size--;
        return it;
    }


    /**
     * shows the element at top of the stack
     * @return top element of the stack
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return top.getValue();
    }


    /**
     * returns true if and only if stack length is 0, false otherwise
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * removes every element from the stack.
     */
    @Override
    public void clear() {
        top = null;
        size = 0;
    }


    /**
     * checks to see wether or not the delimiters within the stack are balanced
     * @param webpage
     * @return true if file is balanced, false otherwise.
     * @throws FileNotFoundException
     */
    public static boolean isBalanced(File webpage) throws FileNotFoundException {
        MyStack stack = new MyStack();

        Scanner scan = new Scanner(webpage);

        while (scan.hasNext()) {
            String next = scan.nextLine();
            if (next.contains("<html>")) {
                stack.push(1);
            } else if (next.contains("<body>")) {
                stack.push(2);
            } else if (next.contains("<p>")) {
                stack.push(3);
            } else if (next.contains("<h1>")) {
                stack.push(4);
            } else if (next.contains("</html>")) {
                stack.pop();
            } else if (next.contains("</body>")) {
                stack.pop();
            } else if (next.contains("</p>")) {
                stack.pop();
            } else if (next.contains("</h1>")) {
                stack.pop();
            }
        }
       return stack.isEmpty();
    }


}
