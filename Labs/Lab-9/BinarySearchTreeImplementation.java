public class BinarySearchTreeImplementation {

    // Node Class
    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
            left = null;
            right = null;
        }
    }

    static class BinarySearchTree {

        public Node addNode(Node root, int val) {
            if (root == null) {
                root = new Node(val);
                return root;
            }

            if (root.val > val) {
                root.left = addNode(root.left, val);
            } else {
                root.right = addNode(root.right, val);
            }
            return root;
        }

        public boolean searchNode(Node root, int data) {
            if (root == null) {
                return false;
            } else if (root.val == data) {
                return true;
            } else if (root.val > data) {
                return searchNode(root.left, data);
            } else {
                return searchNode(root.right, data);
            }

        }

        public void preorderTraversal(Node root) {
            if (root == null) {
                return;
            }

            System.out.print(root.val + " ");
            preorderTraversal(root.left);
            preorderTraversal(root.right);
        }

        public int countOfNodes(Node root) {
            if (root == null) {
                return 0;
            }

            int leftNodes = countOfNodes(root.left);
            int rightNodes = countOfNodes(root.right);

            return leftNodes + rightNodes + 1;
        }

        public int heightofTree(Node root) {
            if (root == null) {
                return 0;
            }

            int leftHeight = heightofTree(root.left);
            int rightHeight = heightofTree(root.right);

            return Math.max(leftHeight, rightHeight) + 1;
        }

        public int minNumber(Node root) {
            if (root.left == null) {
                return root.val;
            } else {
                return minNumber(root.left);
            }
        }

        public int maxNumber(Node root) {
            if (root.right == null) {
                return root.val;
            } else {
                return maxNumber(root.right);
            }
        }

        public boolean isBalanced(Node root) {

            if(root == null) {
                return true;
            }

            // heightLeftChild == heightofRightChild
            int leftHeight = heightofTree(root.left);
            int rightHeight = heightofTree(root.right);
            int subtraction = leftHeight - rightHeight;
            return subtraction == -1 || subtraction == 0 || subtraction == 1;
        }

    }

    public static void main(String[] args) {

        BinarySearchTree tree = new BinarySearchTree();
        Node root = null;

        root = tree.addNode(root, 14);
        root = tree.addNode(root, 20);
        root = tree.addNode(root, 15);
        root = tree.addNode(root, 0);
        root = tree.addNode(root, 1);
        root = tree.addNode(root, 21);
        root = tree.addNode(root, 7);
        root = tree.addNode(root, 4);

        // root = tree.addNode(root, 5);
        // root =  tree.addNode(root, 4);
        // root =  tree.addNode(root, 6);
        // root =  tree.addNode(root, 7);
        // root =  tree.addNode(root, 6);
        // root =  tree.addNode(root, 4);
        // root =  tree.addNode(root, 3);


        tree.preorderTraversal(root);
        System.out.println();

        System.out.println("search for 15: " + tree.searchNode(root, 15));

        System.out.println("Total Nodes are: " + tree.countOfNodes(root));

        System.out.println("Height of tree: " + tree.heightofTree(root));

        System.out.println("Minimum Number in Tree: " + tree.minNumber(root));
        System.out.println("Maximum Number in Tree: " + tree.maxNumber(root));

        System.out.println("Tree is balanced? " + tree.isBalanced(root));

    }

}