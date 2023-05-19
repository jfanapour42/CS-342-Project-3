

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

public class ClientController implements Initializable {
	
	@FXML
	private VBox root;
	
	@FXML
	private static BorderPane root2;
	
	@FXML
	private VBox root3;
	
	@FXML
	//private ListView<String> listItems;
	private ListView<BorderPane> listItems;
    
	@FXML
	private Text addressPromptText;
	
    @FXML
    private TextField addressText;
    
    @FXML
    private Text portPromptText;
    
    @FXML
    private TextField portText;
    
    @FXML
    private Text namePromptText;
    
    @FXML
    private TextField nameText;
	
	@FXML
    private Text topText;
	
	@FXML
    private Text connectText;
    
    @FXML
    private Button connectButton;
    
    @FXML
    private Button enterAddressBtn;
    
    @FXML
    private Button enterPortBtn;
    
    @FXML
    private Button enterNameBtn;
    
    @FXML
    private Button selectionSceneBtn;
    
    @FXML
    private Button sendPlayBtn;
    
    @FXML
    private Button play1Btn, play2Btn, play3Btn, play4Btn, play5Btn, guess1Btn, guess2Btn, guess3Btn, guess4Btn, guess5Btn;
    
	static Client client;
	
	static String address = "127.0.0.1";
	static String name = "";
	static int port = 5555;
	
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
			default: return "/Images/question_mark.jpg";
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
	
	public void setUpClient() {
		topText.setText("Welcome " + name);
		client = new Client(data -> {
			Platform.runLater(()->{
				PauseTransition p = new PauseTransition(Duration.seconds(2.00));
				p.setOnFinished(e -> {
					if (data == null) {
						System.out.println(name + " is in queue");
						topText.setText(topText.getText() + ". The game is currently full. Please sit tight.");
					}
					if (data instanceof MorraInfo) {
						MorraInfo stats = (MorraInfo) data;
						if (stats.have2Players()) {
							selectionSceneBtn.setDisable(false);
							topText.setText("Game is ongoing (score to reach: " + stats.getScoreLimit() + ")");
						} else {
							topText.setText("You are currently the only player. Waiting for one more.");
							selectionSceneBtn.setDisable(true);
						}
						listItems.getItems().clear();
						listItems.getItems().addAll(createPlayerInfoPanes(stats));
					} 
				});
				p.play();
			});
		});
		client.setAddress(address);
		client.setPort(port);
		client.setPlayerName(name);
		client.start();
	}
	
	public void connectClient(ActionEvent e) throws IOException {
		listItems = new ListView<>();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/InfoScene.fxml"));
		root2 = loader.load(); //load view into parent
        ClientController myctr = loader.getController();//get controller created by FXMLLoader
        
        myctr.setUpClient();
        root2.getStylesheets().add("/Styles/InfoScene.css");//set style
        root.getScene().setRoot(root2);//update scene graph
	}
	
	public void goToSelectionScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/SelectionScene.fxml"));
		root3 = loader.load(); //load view into parent
        
        root3.getStylesheets().add("/Styles/SelectionScene.css");//set style
        root2.getScene().setRoot(root3);//update scene graph
	}
	
	public void sendPlay() {
		client.sendPlay();
		root3.getScene().setRoot(root2);
	}
	
	public void selectPlay(ActionEvent e) throws IOException{
		int play = 0;
		int prevPlay = 0;
		Button b = (Button) e.getSource();
		MorraInfo.Player player = client.getPlayer();
		prevPlay = player.getPlay();
		
		if (b.equals(play1Btn)) { play = 1; }
		else if (b.equals(play2Btn)) { play = 2; }
		else if (b.equals(play3Btn)) { play = 3; }
		else if (b.equals(play4Btn)) { play = 4; }
		else if (b.equals(play5Btn)) { play = 5; }
		
		player.setPlay(play);
		b.setStyle("-fx-effect: innershadow( gaussian , red , 7 , 1 , 1 , 1 );");
		
		if (prevPlay != 0 && prevPlay != play) {
			Button b2 = play1Btn;
			if (prevPlay == 2) {b2 = play2Btn;}
			else if (prevPlay == 3) {b2 = play3Btn;}
			else if (prevPlay == 4) {b2 = play4Btn;}
			else if (prevPlay == 5) {b2 = play5Btn;}
			b2.setStyle("");
		}
		
		if (player.isReady()) {
			sendPlayBtn.setDisable(false);
		}
	}
	
	public void selectGuess(ActionEvent e) throws IOException{
		int guess = 0;
		int prevGuess = 0;
		Button b = (Button) e.getSource();
		MorraInfo.Player player = client.getPlayer();
		prevGuess = player.getGuess();
		
		if (b.equals(guess1Btn)) { guess = 1; }
		else if (b.equals(guess2Btn)) { guess = 2; }
		else if (b.equals(guess3Btn)) { guess = 3; }
		else if (b.equals(guess4Btn)) { guess = 4; }
		else if (b.equals(guess5Btn)) { guess = 5; }
		
		player.setGuess(guess);
		b.setStyle("-fx-effect: innershadow( gaussian , red , 7 , 1 , 1 , 1 );");
		
		if (prevGuess != 0 && prevGuess != guess) {
			Button b2 = guess1Btn;
			if (prevGuess == 2) {b2 = guess2Btn;}
			else if (prevGuess == 3) {b2 = guess3Btn;}
			else if (prevGuess == 4) {b2 = guess4Btn;}
			else if (prevGuess == 5) {b2 = guess5Btn;}
			b2.setStyle("");
		}
		
		if (player.isReady()) {
			sendPlayBtn.setDisable(false);
		}
	}
	
	public boolean areValidEntries() {
		if(!address.equals("") && !name.equals("") && port != -1) {
			connectText.setText("Thank you, you can now connect to the server");
			return true;
		} else {
			connectText.setText("");
			return false;
		}
	}
	
	public void processAddressInput() {
		String n = addressText.getText();
		if (!n.trim().isEmpty()) {
			address = n;
			if(areValidEntries()) { connectButton.setDisable(false); }
			addressPromptText.setText("Thank you");
		} else {
			address = "";
			addressPromptText.setText("Invalid IP address. Please try again.");
			connectButton.setDisable(true);
		}
	}
	
	public void getAddressPressEnter(KeyEvent e) throws IOException{
		if(e.getCode() == KeyCode.ENTER) {
			processAddressInput();
		}
	}
	
	public void getAddress(ActionEvent e) throws IOException{
		processAddressInput();
	}
	
	public void processPortInput() {
		String portNum = portText.getText();
		try {
			port = Integer.parseInt(portNum);
			if(areValidEntries()) { connectButton.setDisable(false); }
			portPromptText.setText("Thank you.");
		} catch(NumberFormatException ex) {
			portPromptText.setText("Invalid port number. Please try again.");
			connectButton.setDisable(true);
			port = -1;
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
	
	public void processNameInput() {
		String n = nameText.getText();
		if (!n.trim().isEmpty()) {
			name = n;
			if(areValidEntries()) { connectButton.setDisable(false); }
			namePromptText.setText("Thank you.");
		} else {
			name = "";
			namePromptText.setText("Invalid name. Please try again");
			connectButton.setDisable(true);
		}
	}
	
	public void getNamePressEnter(KeyEvent e) throws IOException{
		if(e.getCode() == KeyCode.ENTER) {
			processNameInput();
		}
	}
	
	public void getName(ActionEvent e) throws IOException{
		processNameInput();
	}
}
