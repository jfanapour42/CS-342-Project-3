

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ServerController implements Initializable {
	
	@FXML
	private VBox root;
	
	@FXML
	private BorderPane root2;
	
	@FXML
	private ListView<BorderPane> listItems;
    
    @FXML
    private Text portPromptText;
    
    @FXML
    private TextField portText;
	
	@FXML
    private Text topText;
	
	@FXML
	private Text clientQueueText;
    
    //@FXML
    //private TextField t2;
    
    @FXML
    private Button startButton;
    
    @FXML
    private Button enterButton;
    
	private static Server server;
	static int port = -1;
	
	static final int picHeight = 150;
	static final int picWidth = 125;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	private String getPicFile(int num) {
		switch(num) {
			case 1: return "/Images/number_1.jpg";
			case 2: return "/Images/number_2.jpg";
			case 3: return "/Images/number_3.jpg";
			case 4: return "/Images/number_4.jpg";
			case 5: return "/Images/number_5.jpg";
			default: return "/Images/question_mark.png";
		}
	}
	
	private BorderPane createPlayerInfoPane(MorraInfo.Player p, int playerNum) {
		BorderPane pane = new BorderPane();
		
		ImageView guessPic = new ImageView(new Image(getPicFile(p.getGuess())));
		guessPic.setFitHeight(picHeight);
		guessPic.setFitWidth(picWidth);
		guessPic.setPreserveRatio(true);
		
		ImageView playPic = new ImageView(new Image(getPicFile(p.getPlay())));
		playPic.setFitHeight(picHeight);
		playPic.setFitWidth(picWidth);
		playPic.setPreserveRatio(true);
		
		pane.setTop(new Text("Player " + playerNum + ": " +p.getName()));
		pane.setRight(new Text("Score: " + p.getScore()));
		pane.setCenter(new VBox(20, new Text("Current Guess"), guessPic, new Text("Current Play"), playPic));
		return pane;
	}
	
	private ArrayList<BorderPane> createPlayerInfoPanes(MorraInfo stats) {
		ArrayList<BorderPane> playerPanes = new ArrayList<>();
		if (stats.getPlayer1() != null) {
			playerPanes.add(createPlayerInfoPane(stats.getPlayer1(), 1));
		}
		if (stats.getPlayer2() != null) {
			playerPanes.add(createPlayerInfoPane(stats.getPlayer2(), 2));
		}
		return playerPanes;
	}
	
	public void setUpServer() {
		server = new Server(data -> {
			Platform.runLater(()->{
				PauseTransition p = new PauseTransition(Duration.seconds(2.00));
				p.setOnFinished(e -> {
					MorraInfo stats = (MorraInfo) data;
					if (stats.have2Players()) {
						topText.setText("Game is ongoing (score to reach: " + stats.getScoreLimit() + ")");
					}
					else if (stats.getPlayer1() != null || stats.getPlayer2() != null) {
						topText.setText("One player has joined. Waiting for one more.");
					} else {
						topText.setText("No current players. :(");
					}
					listItems.getItems().clear();
					listItems.getItems().addAll(createPlayerInfoPanes(stats));
					if(!server.clientQueue.isEmpty()) {
						clientQueueText.setText("There are " + server.clientQueue.size() + " clients waiting in the queue.");
					} else {
						clientQueueText.setText("There are no clients in the queue");
					}
				});
				p.play();
			});
		});
		server.setPort(port);
		server.start();
	}
	
	public void startServer(ActionEvent e) throws IOException {
		listItems = new ListView<>();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/InfoScene.fxml"));
        Parent root2 = loader.load(); //load view into parent
        ServerController myctr = loader.getController();//get controller created by FXMLLoader
        myctr.setUpServer();
        
        root2.getStylesheets().add("/Styles/InfoScene.css");//set style
        root.getScene().setRoot(root2);//update scene graph
	}
	
	public void processPortInput() {
		String portNum = portText.getText();
		try {
			port = Integer.parseInt(portNum);
			startButton.setDisable(false);
			portPromptText.setText("Thank you. You can now start the server");
		} catch(NumberFormatException ex) {
			portPromptText.setText("Invalid port number. Please Try Again.");
			startButton.setDisable(true);
		}
	}
	
	public void getPortPressEnter(KeyEvent e) throws IOException{
		if(e.getCode() == KeyCode.ENTER) {
			processPortInput();
		}
	}
	
	public void getPort(ActionEvent e) throws IOException{
		processPortInput();
	}
}
