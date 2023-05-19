
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{
	static final int sceneWidth = 1000;
	static final int sceneHeight = 600;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("The Networked Client/Server GUI Example");
		try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/WelcomeScene.fxml"));
            Scene s1 = new Scene(root, sceneWidth,sceneHeight);
            s1.getStylesheets().add("/Styles/WelcomeScene.css");
            primaryStage.setScene(s1);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
		/*
		this.serverChoice = new Button("Server");
		this.serverChoice.setStyle("-fx-pref-width: 300px");
		this.serverChoice.setStyle("-fx-pref-height: 300px");
		
		this.serverChoice.setOnAction(e->{ primaryStage.setScene(sceneMap.get("server"));
											primaryStage.setTitle("This is the Server");
				serverConnection = new Server(data -> {
					Platform.runLater(()->{
						listItems.getItems().add(data.toString());
					});

				});
											
		});
		
		this.buttonBox = new HBox(400, serverChoice);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setCenter(buttonBox);
		
		startScene = new Scene(startPane, 800,800);
		
		listItems = new ListView<String>();
		
		c1 = new TextField();
		
		sceneMap = new HashMap<String, Scene>();
		
		sceneMap.put("server",  createServerGui());*/
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		 
		/*
		primaryStage.setScene(startScene);
		primaryStage.show();*/
		
	}
	
	/*public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		
		pane.setCenter(listItems);
	
		return new Scene(pane, sceneWidth, sceneHeight);
		
		
	}*/
}
