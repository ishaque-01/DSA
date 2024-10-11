import java.util.LinkedList;
import java.util.Queue;

public class Trees {

    static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }

    static class BinaryTree {
        public static int idx = -1;

        public Node createTree(int[] nodes) {
            idx++;
            if (nodes[idx] == -1)
                return null;

            Node newNode = new Node(nodes[idx]);

            newNode.left = createTree(nodes);
            newNode.right = createTree(nodes);
            return newNode;
        }
    }

    public static void preorderTraversal(Node root) {
        if (root == null)
            return;

        System.out.print(root.data + " ");
        inorderTraversal(root.left);
        inorderTraversal(root.right);
    }

    public static void inorderTraversal(Node root) {
        if (root == null)
            return;

        inorderTraversal(root.left);
        System.out.print(root.data + " ");
        inorderTraversal(root.right);
    }

    public static void postorderTraversal(Node root) {
        if (root == null)
            return;

        inorderTraversal(root.left);
        inorderTraversal(root.right);
        System.out.print(root.data + " ");
    }

    public static void levelOrderTraversal(Node root) {
        if (root == null)
            return;

        Queue<Node> q = new LinkedList<>();
        q.add(root);
        q.add(null);

        while (!q.isEmpty()) {
            Node curr = q.remove();
            if (curr == null) {
                System.out.println();
                if (q.isEmpty()) {
                    break;
                } else {
                    q.add(null);
                }
            } else {
                System.out.print(curr.data + " ");
                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }
            }
        }
    }

    public static int countNodes(Node root) {
        if (root == null)
            return 0;

        int leftNodes = countNodes(root.left);
        int rightNodes = countNodes(root.right);

        return leftNodes + rightNodes + 1;
    }

    public static int sumOfNodes(Node root) {
        if (root == null)
            return 0;

        int leftSum = sumOfNodes(root.left);
        int rightSum = sumOfNodes(root.right);

        return leftSum + rightSum + root.data;
    }

    public static int height(Node root) {
        if (root == null)
            return 0;

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        int maxHeight = Math.max(leftHeight, rightHeight) + 1;

        return maxHeight;
    }

    public static int sumOfKthLevel(int k, Node root) {
        if (root == null)
            return 0;

        int sum = 0, level = 1;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        q.add(null);

        while (!q.isEmpty()) {
            Node curr = q.remove();
            if (curr == null) {
                level++;
                if (q.isEmpty()) {
                    break;
                } else {
                    q.add(null);
                }
            } else {
                if (level == k) {
                    sum += curr.data;
                }
                if (curr.left != null) {
                    q.add(curr.left);
                }
                if (curr.right != null) {
                    q.add(curr.right);
                }
            }
        }
        return sum;
    }

    public static boolean isSubTree(Node root, Node subTree) {
        if (subTree == null)
            return true;
        if (root == null)
            return false;

        if (isIdentical(root, subTree))
            return true;

        return isSubTree(root.left, subTree) || isSubTree(root.right, subTree);
    }

    public static boolean isIdentical(Node root1, Node root2) {
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null || root2 == null)
            return false;

        if (root1.data == root2.data)
            return isIdentical(root1.left, root2.left) && isIdentical(root1.right, root2.right);

        return false;
    }
 
        public static void main(String[] args) {

        int[] nodes = { 1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1 };

        BinaryTree bt = new BinaryTree();

        Node root = bt.createTree(nodes);

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

        System.out.println("Total nodes: " + countNodes(root));

        System.out.println("Total sum of nodes: " + sumOfNodes(root));

        System.out.println("Total height: " + height(root));

        System.out.println("Sum of 1st level is: " + sumOfKthLevel(1, root));


        int[] tree = {2, 4, -1, -1, 6, -1, -1};
        // Make idx = -1 before creating any new Tree
        BinaryTree.idx = -1;
        Node subTree = bt.createTree(tree);

        System.out.println("Level:");
        levelOrderTraversal(subTree);
        System.out.println("Is subTree is really SubTree of root? " + isSubTree(root, subTree));
    }
}