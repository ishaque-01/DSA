import java.util.Queue;
import java.util.Random;
import java.util.LinkedList;

class Node {
    Node left, right;
    int val, height;

    public Node(int val) {
        this.val = val;
        left = null;
        right = null;
        height = 1;
    }
}

class AVLTree {

    public Node insert(Node root, int val) {
        if (root == null) {
            return new Node(val);
        }

        if (root.val > val) {
            root.left = insert(root.left, val);
        }

        if (root.val < val) {
            root.right = insert(root.right, val);
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));

        int balance = checkBF(root);

        if (balance > 1 && val < root.left.val) {
            return llRotation(root);
        }

        if (balance > 1 && val > root.left.val) {
            return lrRotation(root);
        }

        if (balance < -1 && val > root.right.val) {
            return rrRotation(root);
        }

        if (balance < -1 && val < root.right.val) {
            return rlRotation(root);
        }

        return root;
    }

    public int getHeight(Node root) {
        if (root == null)
            return 0;
        return root.height;
    }

    public int checkBF(Node root) {
        if (root == null)
            return 0;

        return getHeight(root.left) - getHeight(root.right);
    }

    public static void levelOrderTraversal(Node root) {

        if (root == null) {
            System.out.println("Tree Empty!");
            return;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        q.add(null);

        while (!q.isEmpty()) {
            Node curr = q.remove();
            if (curr == null) {
                System.out.println();
                if (!q.isEmpty()) {
                    q.add(null);
                }

                if (q.isEmpty()) {
                    break;
                }
            } else {
                System.out.print(curr.val + " ");
                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }
            }
        }
    }

    public Node llRotation(Node a) {
        Node b = a.left;
        Node temp = b.right;

        b.right = a;
        a.left = temp;

        a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
        b.height = Math.max(getHeight(b.left), getHeight(b.right)) + 1;

        return b;
    }

    public Node rrRotation(Node a) {
        Node b = a.right;
        Node temp = b.left;

        b.left = a;
        a.right = temp;

        a.height = Math.max(getHeight(a.left), getHeight(a.right)) + 1;
        b.height = Math.max(getHeight(b.left), getHeight(b.right)) + 1;

        return b;
    }

    public Node lrRotation(Node a) {
        a.left = rrRotation(a.left);
        return llRotation(a);
    }

    public Node rlRotation(Node a) {
        a.right = llRotation(a.right);
        return rrRotation(a);
    }
}

public class AVLTreeImplementation {
    @SuppressWarnings("static-access")
    public static void main(String[] args) {

        AVLTree avl = new AVLTree();

        Node root = null;

        Random rand = new Random();
        for(int i= 0; i<15; i++) {
            root = avl.insert(root, rand.nextInt(1001));
        }

        System.out.println("Level Order Traversal");
        avl.levelOrderTraversal(root);
        
    }
}
