import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.function.Consumer;

public class Server{
	int port = -1;
	ClientThread player1;
	ClientThread player2;
	ArrayDeque<ClientThread> clientQueue = new ArrayDeque<>();
	TheServer server;
	private MorraGame game;
	private Consumer<Serializable> callback;
	
	Server(Consumer<Serializable> call){
		callback = call;
		game = new MorraGame();
		server = new TheServer();
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void start() {
		if(port != -1) {server.start();}
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
			try(ServerSocket mysocket = new ServerSocket(port);){
				System.out.println("Server is waiting for a client!");
			    while(true) {
			    	ClientThread c = new ClientThread(mysocket.accept());
			    	if (player1 == null) {
						player1 = c;
			    	} else if (player2 == null) {
			    		player2 = c;
			    	} else {
			    		clientQueue.add(c);
			    	}
			    	c.start();
				}
			}//end of try
			catch(Exception e) {
				callback.accept("Server socket did not launch");
			}
			}//end of run
		}
	

		class ClientThread extends Thread{
			Socket connection;
			ObjectInputStream in;
			ObjectOutputStream out;
			private boolean isAlive;
			
			ClientThread(Socket s){
				this.connection = s;
				isAlive = true;
			}
			
			public void updatePlayers() {
				try {
					if (player1 != null) { player1.out.writeObject(game.stats); }
					if (player2 != null) { player2.out.writeObject(game.stats); }
				}
				catch(Exception e) {}
			}
			
			
			public void getNewPlayerFromQueue() {
				if (clientQueue.size() > 0) {
					ClientThread newPlayer = clientQueue.pollFirst();
					if (player1 == null) {
						player1 = newPlayer;
					} else if (player2 == null) {
						player2 = newPlayer;
					}
				}
			}
			
			public void removeClient() {
				System.out.println("This client is being removed");
				if (this.equals(player1) || this.equals(player2)) {
					if (this.equals(player1)) {
				    	game.stats.removePlayer1();
				    	player1 = null;
					} else if (this.equals(player2)){
				    	game.stats.removePlayer2();
				    	player2 = null;
				    }
				    game.resetGame();
				    updatePlayers();
				    callback.accept(game.stats);
				    getNewPlayerFromQueue();
			    } else if (clientQueue.contains(this)) {
			    	clientQueue.remove(this);
				}
				this.isAlive = false;
			}
			
			public void run(){
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);
				} catch(Exception e) {
					System.out.println("Streams not open");
				}
				// TODO: put thread to sleep or interrupt or wait when in queue and wake up when out of queue
				if (clientQueue.contains(this)) { // just send and receive null to and from client
					try {
						out.writeObject(null);
						while (clientQueue.contains(this)){}
					} catch (Exception e) {
						e.printStackTrace();
						removeClient();
					}
				}
				// Send current game stats when connection is first opened and out of queue.
				if (isAlive) {
					try {
						out.writeObject(game.stats);
					} catch (Exception e){
						removeClient(); // find better solution
					}
				}
				
				while(isAlive) {
				    try {
				    	game.stats = (MorraInfo) in.readObject();
				    	
				    	callback.accept(game.stats);
				    	if (game.stats.have2Players() && !game.stats.arePlayersReady()) {
				    		updatePlayers();
				    	}
				    	if (game.stats.arePlayersReady()) {
				    		MorraInfo.Player p = game.playRound();
				    		callback.accept(game.stats);
				    		updatePlayers();
				    		if (p.isWinner()) {
				    			game.resetGame();
				    			callback.accept(game.stats);
				    			updatePlayers();
				    		}
				    	}
				    } catch(Exception e) {
				    	removeClient();
				    }
				}
			}//end of run	
		}//end of client thread
}