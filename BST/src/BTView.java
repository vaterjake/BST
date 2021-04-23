import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BTView extends Pane {
    private BST<Integer> tree = new BST<>();
    private double radius = 15; // Tree node radius
    private double vGap = 50; // Gap between successive levels in the tree

    BTView(BST<Integer> tree) {
        this.tree = tree;
        setStatus("Tree is empty");
    }

    public void setShadedNodes(ArrayList<BST.TreeNode<Integer>> shadeNode){
        this.getChildren().clear();
        if(tree.getRoot() != null) setShadeNode(tree.getRoot(),getWidth()/2, getWidth()/4, vGap, shadeNode);
    }

    private void setShadeNode(BST.TreeNode<Integer> root, double x, double hGap, double y, ArrayList<BST.TreeNode<Integer>> shadeNode){

        if(root.left!=null){
            getChildren().add(new Line(x - hGap, y+ vGap, x, y));
            setShadeNode(root.left, x - hGap, hGap / 2,y + vGap,  shadeNode);
        }

        if (root.right != null) {
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
            setShadeNode(root.right, x + hGap, hGap / 2, y + vGap,  shadeNode);
        }
        Circle circle = new Circle(x, y, radius);

        if(shadeNode.contains(root)){
            circle.setFill(Color.ORANGE);
        }
        else{
            circle.setFill(Color.WHITE);
        }

        circle.setStroke(Color.BLACK);
        this.getChildren().addAll(circle, new Text(x - 4, y + 4, root.element.toString()));

    }

    public void setStatus(String msg) {
        this.getChildren().add(new Text(20, 20, msg));
    }

    public void displayTree() {
        this.getChildren().clear(); // Clear the pane
        if (tree.getRoot() != null) {
            // Display tree recursively
            displayTree(tree.getRoot(), getWidth() / 2, vGap,
                    getWidth() / 4);
        }
    }

    /** Display a subtree rooted at position (x, y) */
    private void displayTree(BST.TreeNode<Integer> root, double x, double y, double hGap) {
        if (root.left != null) {
            // Draw a line to the left node
            getChildren().add(new Line(x - hGap, y + vGap, x, y));
            // Draw the left subtree recursively
            displayTree(root.left, x - hGap, y + vGap, hGap / 2);
        }

        if (root.right != null) {
            // Draw a line to the right node
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
            // Draw the right subtree recursively
            displayTree(root.right, x + hGap, y + vGap, hGap / 2);
        }

        // Display a node
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        this.getChildren().addAll(circle,
                new Text(x - 4, y + 4, root.element.toString()));

    }

}
