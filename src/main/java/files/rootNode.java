package files;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public abstract class rootNode extends BorderPane{
    public static Background backGround=new Background(new BackgroundFill(Color.web("#f1f5f9"),new CornerRadii(0),new Insets(0)));
    public static Background background2=new Background(new BackgroundFill(Color.web("#ffffff"),new CornerRadii(8),new Insets(0)));
    private HBox     top=new HBox(5);
    private VBox    left=new VBox(5);
    private VBox   right=new VBox(5);
    private VBox  bottom=new VBox(5);
    private VBox content=new VBox(5);
    public rootNode(){
        this.setBackground(backGround);
        this.setTop(top);
        this.setBottom(bottom);
        this.setLeft(left);
        this.setRight(right);
        this.setCenter(content);

        content.setSpacing(10);
        content.setAlignment(Pos.CENTER);
        top.setAlignment(Pos.CENTER);
        bottom.setAlignment(Pos.CENTER);
        left.setAlignment(Pos.CENTER_LEFT);
        right.setAlignment(Pos.CENTER);
        content.setFillWidth(false);

    }

    public void addContent(Node... node){
        content.getChildren().addAll(node);
    }
    public void addTop(Node... node){
        top.getChildren().addAll(node);
    }
    public void addLeftSide(Node... node){
       left.getChildren().addAll(node);
    }
    public void addRightSide(Node... node){
        right.getChildren().addAll(node);
    }
    public void addBottom(Node... node){
        bottom.getChildren().addAll(node);
    }

    public void setOnScene(Scene scene){
        scene.setRoot(this);
    }

    
}
