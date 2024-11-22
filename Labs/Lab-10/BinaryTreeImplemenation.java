import java.util.Queue;
import java.util.LinkedList;

public class BinaryTreeImplemenation {

    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
            left = null;
            right = null;
        }
    }

    static class BinaryTree {
        static int idx = -1;

        public Node createTree(int[] nodes) {
            idx++;
            if (idx >= nodes.length || nodes[idx] == -1) {
                return null;
            }

            Node node = new Node(nodes[idx]);
            node.left = createTree(nodes);
            node.right = createTree(nodes);

            return node;
        }
    }

    public static void inorderTraversal(Node root) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left);
        System.out.print(root.val + " ");
        inorderTraversal(root.right);
    }

    public static void preorderTraversal(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public static void postorderTraversal(Node root) {
        if (root == null) {
            return;
        }
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.print(root.val + " ");
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
                } else if (q.isEmpty()) {
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

    public static boolean isCompleteBinaryTree(Node root) {

        if (root == null) {
            return false;
        }

        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node curr = q.remove();
            if (curr.left != null && curr.right != null) {
                q.add(curr.left);
                q.add(curr.right);
            } else if (curr.left != null && curr.right == null) {
                q.add(curr.left);
            } else if (curr.left == null && curr.right != null) {
                return false;
            }
        }

        return true;
    }

    public static Node addCompleteBinaryTree(Node root, int val) {
        Node newNode = new Node(val);

        if (root == null) {
            root = newNode;
            return root;
        } else {

            Queue<Node> q = new LinkedList<>();
            q.add(root);

            while (!q.isEmpty()) {
                Node curr = q.remove();
                if (curr.left == null) {
                    curr.left = newNode;
                    return root;
                } else if (curr.right == null) {
                    curr.right = newNode;
                    return root;
                } else if (curr.left != null && curr.right != null) {
                    q.add(curr.left);
                    q.add(curr.right);
                }
            }
            return root;
        }

    }

    public static boolean isIdentical(Node x, Node y) {
        if (x == null && y == null) {
            return true;
        } else if (x == null || y == null) {
            return false;
        }

        if (x.val == y.val) {
            return isIdentical(x.left, y.left) && isIdentical(x.right, y.right);
        }
        return false;
    }

    public static boolean isSubTree(Node x, Node y) {
        if (x == null) {
            return false;
        }
        if (isIdentical(x, y)) {
            return true;
        }
        return isSubTree(x.left, y) || isSubTree(x.right, y);

    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) {

        int[] nodes = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6 };

        BinaryTree tree = new BinaryTree();
        Node root = tree.createTree(nodes);

        System.out.println("Inorder Traversal");
        inorderTraversal(root);
        System.out.println();

        System.out.println("Preorder Traversal");
        preorderTraversal(root);
        System.out.println();

        System.out.println("Postorder Traversal");
        postorderTraversal(root);
        System.out.println();

        System.out.println("Level Order Traversal");
        levelOrderTraversal(root);

        System.out.println("isComplete? " + isCompleteBinaryTree(root));

        int[] xNodes = { 1, 2, 4, -1, -1, 5, -1, -1, 3, 6, -1, -1, 7, -1, -1 };
        int[] yNodes = { 1, 2, 4, -1, -1, 5, -1, -1, 3, 6, -1, -1, 7, -1, -1 };

        tree.idx = -1;
        Node x = tree.createTree(xNodes);
        tree.idx = -1;
        Node y = tree.createTree(yNodes);

        System.out.println("Is Identical? " + isIdentical(x, y));

        Node root1 = null;
        for (int i = 1; i < 11; i++) {
        root1 = addCompleteBinaryTree(root1, i);
        }

        System.out.println("LevelOrder");
        levelOrderTraversal(root1);
    
    
        int[] nodeX = { 1, 2, 4, -1, -1, 5, -1, -1, 3, 6, -1, -1, 8, -1, -1 };
        int[] nodeY = {  3, 6, -1, -1, 9, -1, -1 };

        tree.idx = -1;
        Node rootX = tree.createTree(nodeX);
        tree.idx = -1;
        Node rootY = tree.createTree(nodeY);

        System.out.println("IsSubTree: " + isSubTree(rootX, rootY));
    }
}