import java.util.ArrayList;
import java.util.List;

public class CustomTree {
    private Node root;

    public void insert(int value){
        if(root == null) {
            root = new Node(Color.BLACK, value);
        }
        else{
           insert(root, value);
           root = balanced(root);
        }

        root.color = Color.BLACK;
    }

    public Node find(int value){
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if(node == null)
            return null;

        if(node.value == value)
            return node;
        else if(value > node.value)
            return find(node.right, value);
        else
            return find(node.left, value);
    }

    private void insert(Node node, int value){
        if(node.value != value){
            if(node.value < value){
                if(node.right == null){
                    node.right = new Node(Color.RED, value);
                }else {
                    insert(node.right, value);
                    node.right = balanced(node.right);
                }

            }else{
                if(node.left == null) {
                    node.left = new Node(Color.RED, value);
                }
                else {
                    insert(node.left, value);
                    node.left = balanced(node.left);
                }
            }
        }
    }

    private Node balanced(Node node) {
        Node current = node;
        Node left = current.left;
        Node right = current.right;
        boolean needBalanced;

        do {
            needBalanced = false;

            if((left == null || !left.isRed()) && right != null && right.isRed()){
                current = rotateLeft(current);
                needBalanced = true;
            }

            if(left != null && left.isRed() && left.left != null && left.left.isRed()){
                current = rotateRight(current);
                needBalanced = true;
            }

            if(left != null && left.isRed() && right != null && right.isRed()) {
                swapColors(current);
                needBalanced = true;
            }

        }while(needBalanced);

        return current;
    }

    private Node rotateRight(Node node){
        Node current = node.left;
        node.left = current.right;
        current.right = node;
        
        current.color = node.color;
        node.color = Color.RED;

        return current;
    }

    private Node rotateLeft(Node node){
        Node current = node.right;
        if(current.left != null)
            node.right = current.left;
        current.left = node;

        current.color = node.color;
        node.color = Color.RED;

        return current;
    }

    private void swapColors(Node node){
        node.color = node.isRed() ? Color.BLACK: Color.RED;
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
    }


    public static class Node{
        Color color;
        int value;
        Node left, right;

        public Node(Color color, int value, Node right, Node left) {
            this.value = value;
            this.right = right;
            this.left = left;
            this.color = color;
        }

        public Node(Color color, int value) {
            this(color, value, null, null);
        }

        public boolean isRed(){
            return this.color == Color.RED;
        }

        @Override
        public String toString() {
            return color + " " + value;
        }
    }

    public enum Color{
        BLACK, RED
    }

    public static void main(String[] args) {
        CustomTree customTree = new CustomTree();
        int n = 7;
        for (int i = 0; i < n; i++) {
            customTree.insert(i);
        }
    }
}