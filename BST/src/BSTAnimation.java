import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BSTAnimation extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        BST<Integer> tree = new BST<>(); // Create a tree

        BorderPane pane = new BorderPane();
        BTView view = new BTView(tree); // Create a View
        pane.setCenter(view);

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(3);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        //buttons created
        Button btInsert = new Button("Insert");
        Button btDelete = new Button("Delete");
        Button btSearch = new Button("Search");
        Button btInorder = new Button("Inorder");
        Button btPreorder = new Button("Preorder");
        Button btPostorder = new Button("Postorder");
        Button btBreadth = new Button("Breadth-first");
        Button btHeight = new Button("Height");

        HBox hBox = new HBox(5);

        hBox.getChildren().addAll(new Label("Enter a key: "),
                tfKey, btInsert, btDelete,btSearch,btInorder,btPreorder,
                btPostorder,btBreadth,btHeight);

        hBox.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);

        btInsert.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (tree.search(key)){ // key is in the tree already
                view.displayTree();   // Clears the old status message
                view.setStatus(key + " is already in the tree");
            }
            else{
                tree.insert(key); // Insert a new key
                view.displayTree();
                view.setStatus(key + " is inserted in the tree");
            }
        });

        btDelete.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (!tree.search(key)){ // key is not in the tree
                view.displayTree();    // Clears the old status message
                view.setStatus(key + " is not in the tree");
            }
            else{
                tree.delete(key); // Delete a key
                view.displayTree();
                view.setStatus(key + " is deleted from the tree");
            }
        });

        btSearch.setOnAction(e->{
            int key = Integer.parseInt(tfKey.getText());
            if (tree.search(key)){
                view.setShadedNodes(tree.path(key));
                view.setStatus("Found " + key + " in the tree");
            }
            else {
                tree.search(key);
                view.setShadedNodes(tree.path(key));
                view.setStatus(key + " is not in the tree");
            }
        });

        btInorder.setOnAction(e->{
            ArrayList<Integer> lst = tree.inorder();
            String order ="";
            for(int i=0; i<lst.size();i++){
                if(i==0) order = order + lst.get(i);
                else order = order + ", "  + lst.get(i);
            }
            view.displayTree();    // Clears the old status message
            view.setStatus("Inorder traversal:[" + order + "]");
        });

        btPostorder.setOnAction(e->{
            ArrayList<Integer> lst = tree.postorder();
            String order ="";
            for(int i=0; i<lst.size();i++){
                if(i==0) order = order + lst.get(i);
                else order = order + ", "  + lst.get(i);
            }
            view.displayTree();    // Clears the old status message
            view.setStatus("Inorder traversal:[" + order + "]");
        });

        btPreorder.setOnAction(e->{
            ArrayList<Integer> lst = tree.preorder();
            String order ="";
            for(int i=0; i<lst.size();i++){
                if(i==0) order = order + lst.get(i);
                else order = order + ", "  + lst.get(i);
            }
            view.displayTree();    // Clears the old status message
            view.setStatus("Inorder traversal:[" + order + "]");
        });

        btHeight.setOnAction(e->{
            int i = tree.height();
            view.displayTree();
            view.setStatus("Tree height is " + i);
        });

        btBreadth.setOnAction(e->{
            List<Integer> queue = tree.breadthFirstOrderList();
            String breadthFirstList = queue.toString();
            view.displayTree();
            view.setStatus("Breadth-first traversal: "+breadthFirstList);
        });

        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 750, 400);
        primaryStage.setTitle("BSTAnimation"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

