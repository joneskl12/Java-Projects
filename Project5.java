/*
* Kyle Jones
* 3-31-2023
* Project 5 – Arithmetic Expression Parse Tree Implementation
* Implementing an arithmetic expression tree using the BRIDGES API.
* Project5.java
*/

package cmsc256;

import bridges.base.BinTreeElement;

import java.util.*;
import java.util.Arrays;

public class Project5<E> {

    /**
     * passes in a mathmematical expression to build binary tree.
     * @param expression mathmatical expression
     * @return root element
     * @throws IllegalArgumentException if there is a malformed expression passed in
     */
    public static BinTreeElement<String> buildParseTree(String expression) throws IllegalArgumentException {
        BinTreeElement<String> root = new BinTreeElement<>();
        BinTreeElement<String> current = root;
        HashSet<String> opSet = new HashSet<String>(Arrays.<String>asList("+", "-", "*", "/"));
        HashSet<String> digitSet = new HashSet<String>(Arrays.<String>asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        Stack<BinTreeElement<String>> stack = new Stack<>(); // initializing stack to traverse through tree when needed.
        String[] s = expression.split(" "); // split string into array of string expressions seperated by spaces
        int inc = 0;

        for (String input : s) { // iterate through each split string expression
            ++inc;
            if (input.equals("(")) { // building tree/subtree corresponding to left parentheses
                if (current.getLeft() == null) {
                    current.setLeft(new BinTreeElement<>());
                    stack.push(current); // pushing stack for refrence to parent node
                    current = current.getLeft(); //descending through left child node
                } else {
                    return null;
                }
            } else if (opSet.contains(input)) { // sets operators as root values of the current node within the tree/subtree
                current.setValue(input);
                current.setRight(new BinTreeElement<>());
                stack.push(current);
                current = current.getRight(); //descending through right child node
            } else if (isIntOrDouble(input)) {
                current.setValue(input);
                current = stack.pop(); //popping stack to return to parent node
            } else if (input.equals(")")) { // returning to parent node
                if (inc == s.length) {
                    getEquation(root);
                    return root;
                }
                current = stack.pop();
            } else {
                throw new IllegalArgumentException();
            }
        }
        getEquation(root);
        return root;
    }

    /**
     * evaluates mathmatical expression recursively
     * @param tree node being evaluated upon
     * @return a double value of the result of the expression
     */
    public static double evaluate(BinTreeElement<String> tree) {

        if (tree == null) {
            return 0;
        }
        if (isOperand(tree)) { // checks to see if value is a leaf
            return Double.parseDouble(tree.getValue());
        }
        double left = evaluate(tree.getLeft()); // moves function towards base case recursively
        double right = evaluate(tree.getRight());

        return expression(tree.getValue(), left, right); // calls method to put results of right and left child together in order to evaluate
    }

    /**
     * formats and prints the mathmatical expression, including proper parentheses implementation
     * @param tree a binary node
     * @return a String of the mathmatical expression being evaluated
     */
    public static String getEquation(bridges.base.BinTreeElement<String> tree) {
        String s = "";
        if (tree == null) {
            return "";
        }

        if (!isOperand(tree)){ // checks to see if value is not a leaf node
            s += "( " + getEquation(tree.getLeft()); // moves towards base case by combining expression until at root.
            s += " " + tree.getValue() + " "; // won't print operand but will avoid extra parentheses
            s += getEquation(tree.getRight()) + " )";
        } else {
            s += tree.getValue(); //prints operands
        }

        return s;
    }

    /**
     * checks for leaf nodes within the binary tree.
     * @param tree a binary node
     * @return boolean whether the binary node is a leaf, true if yes, false otherwise.
     */
    public static boolean isOperand(bridges.base.BinTreeElement<String> tree) {
        return tree.getLeft() == null && tree.getRight() == null;
    }

    /**
     * converts String operator to real operator to evaluate the algebra of the expression
     * @param op String operator within the expression
     * @param left value of left operand
     * @param right value of right operand
     * @return respective algebraic solution to the mathematical expression
     * @throws ArithmeticException if dividing by 0.
     */
    public static double expression(String op, double left, double right) throws ArithmeticException {

        switch (op) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case "/":
                if (right == 0.0) {
                    throw new ArithmeticException();
                }
                return left / right;
        }

        return 0;
    }

    /**
     * checks whether the String value within the expression is a double or an integer in the given expression
     * @param s String value of operand being passed in
     * @return boolean wether String value is a double or an integer.
     */
    private static boolean isIntOrDouble(String s) {
        boolean isInt = true;
        boolean isDouble = true;
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            isDouble = false;
        }

        try {
            int d = Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            isInt = false;
        }

        return isInt || isDouble;
    }

}
