import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BST<E extends Comparable<E>> implements Tree<E> {
    protected TreeNode<E> root;
    protected int size = 0;

    /** Create a default binary tree */
    public BST() {
    }

    /** Create a binary tree from an array of objects */
    public BST(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            add(objects[i]);
    }

    @Override /** Returns true if the element is in the tree */
    public boolean search(E e) {
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                return true; // e is found
        }

        return false;
    }

    @Override /** Insert element o into the binary tree
     * Return true if the element is inserted successfully */
    public boolean insert(E e) {
        if (root == null)
            root = createNewNode(e); // Create a new root
            //root = new TreeNode<>(e);	// Question:  Why not do it this way?  It would work.
        else {
            // Locate the parent node
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null)
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                }
                else
                    return false; // Duplicate node not inserted

            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
                //parent.left = new TreeNode<>(e);
            else
                parent.right = createNewNode(e);
        }

        size++;
        return true; // Element inserted successfully
    }

    protected TreeNode<E> createNewNode(E e) {  // Factory method design pattern
        return new TreeNode<>(e);
    }

    @Override /** Inorder traversal from the root */
    public ArrayList<E> inorder() {
        ArrayList<E> lst = new ArrayList<>();
        inorder(root, lst);
        return lst;
    }

    /** Inorder traversal from a subtree */
    protected ArrayList<E> inorder(TreeNode<E> root, ArrayList<E> lst) {
        if (root == null) return null;
        inorder(root.left, lst);
        lst.add(root.element);
        inorder(root.right, lst);
        return lst;
    }

    @Override /** Postorder traversal from the root */
    public ArrayList<E> postorder() {
        ArrayList<E> lst = new ArrayList<>();
        postorder(root, lst);
        return lst;
    }

    /** Postorder traversal from a subtree */
    protected ArrayList<E> postorder(TreeNode<E> root, ArrayList<E> lst) {
        if (root == null) return null;
        postorder(root.left, lst);
        postorder(root.right, lst);
        lst.add(root.element);
        return lst;
    }

    @Override /** Preorder traversal from the root */
    public ArrayList<E> preorder() {
        ArrayList<E> lst = new ArrayList<>();
        preorder(root, lst);
        return lst;
    }

    /** Preorder traversal from a subtree */
    protected ArrayList<E> preorder(TreeNode<E> root, ArrayList<E> lst) {
        if (root == null) return null;
        lst.add(root.element);
        preorder(root.left, lst);
        preorder(root.right, lst);
        return lst;
    }

    @Override
    public int height(){
        if(root == null) return -1;
        else {
            int i = 0;
            int j = 0;
            i = height(root,i,j);
            return i;
        }
    }

    protected int height(TreeNode<E> root,int l,int r){
        if(root == null){
            if(l > r)return l;
            else if(l < r) return r;
            else return r;
        }

        if(root.left != null) l++;
        if(root.right != null) r++;


        int w = height(root.left,l,r);
        int x = height(root.right,l,r);

        if(w > x)return w;
        else if(w < x) return x;
        else return x;
    }

    public List<E> breadthFirstOrderList(){
        Queue<TreeNode<E>> queue = new LinkedList<TreeNode<E>>();
        queue.add(root);
        List<E> lst = new ArrayList<E>();

        while (queue.size() != 0){
            TreeNode<E> temp = queue.poll();
            lst.add(temp.element);

            if (temp.left != null) queue.add(temp.left);

            if (temp.right != null) queue.add(temp.right);
        }
        return lst;
    }

    public static class TreeNode<E> {
        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            element = e;
        }
    }

    @Override /** Get the number of nodes in the tree */
    public int getSize() {return size;}

    /** Returns the root of the tree */
    public TreeNode<E> getRoot() {return root;}

    /** Returns a path from the root leading to the specified element */
    public ArrayList<TreeNode<E>> path(E e) {
        ArrayList<TreeNode<E>> list =
                new ArrayList<>();
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                current = current.right;
            }
            else
                break;
        }

        return list; // Return an array list of nodes
    }

    @Override /** Delete an element from the binary tree.
     * Return true if the element is deleted successfully
     * Return false if the element is not in the tree */
    public boolean delete(E e) {
        // Locate the node to be deleted and also locate its parent node
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed at by current
        }

        if (current == null)
            return false; // Element is not in the tree

        // Case 1: current has no left child
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            }
            else {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        }
        else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
                parentOfRightMost.right = rightMost.left;
            else
                // Special case: parentOfRightMost.left == rightMost, happens if parentOfRightMost is current
                parentOfRightMost.left = rightMost.left;
        }

        size--;
        return true; // Element deleted successfully
    }

    @Override /** Obtain an iterator. Use inorder. */
    public java.util.Iterator<E> iterator() {return new InorderIterator();}

    // Inner class InorderIterator
    private class InorderIterator implements java.util.Iterator<E> {
        // Store the elements in a list
        private ArrayList<E> list =
                new ArrayList<>();
        private int current = 0; // Index of next element in the iteration
        private boolean canRemove = false;

        public InorderIterator() {
            inorder(); // Traverse binary tree and store elements in list
        }

        /** Inorder traversal from the root*/
        private void inorder() {
            inorder(root);
        }

        /** Inorder traversal from a subtree */
        private void inorder(TreeNode<E> root) {
            if (root == null) return;
            inorder(root.left);
            this.list.add(root.element);
            inorder(root.right);
        }

        @Override /** More elements for traversing? */
        public boolean hasNext() {
            return current < list.size();
        }

        @Override /** Get the current element and move to the next */
        public E next() {
            if (hasNext())
                canRemove = true;
            else
                throw new java.util.NoSuchElementException();
            return list.get(current++);
        }

        @Override /** Remove the element most recently returned */
        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();
            BST.this.delete(list.get(--current));
            InorderIterator.this.list.remove(current);
            canRemove = false;
        }
    }

    @Override /** Remove all elements from the tree */
    public void clear() {
        root = null;
        size = 0;
    }
}