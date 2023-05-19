import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread{
	private String address;
	private int port;
	Socket socketClient;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback;
	private String playerName;
	private MorraInfo stats;
	
	Client(Consumer<Serializable> call){
		callback = call;
	}
	
	public void setAddress(String str) {
		address = str;
	}
	
	public String getAdress() {
		return address;
	}
	
	public void setPort(int p) {
		port = p;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPlayerName(String name) {
		playerName = name;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public MorraInfo.Player getPlayer() {
		return stats.getPlayer(playerName);
	}
	
	public void sendName() {
		stats.addPlayer(this.playerName);
		try {
			out.writeObject(stats);
		} catch (IOException e) {
			System.out.println("Name transmission failed from Client " + playerName);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error in send name");
			e.printStackTrace();
		}
	}
	
	public boolean sendPlay() {
		MorraInfo.Player player = stats.getPlayer(playerName);
		if (player.isReady()) {
			try {
				out.writeObject(stats);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Error in send play");
			}
			return true;
		}
		return false;
	}
	
	public void run() {
		try {
			socketClient = new Socket("127.0.0.1",5555);
			//socketClient = new Socket(address, port);
		    out = new ObjectOutputStream(socketClient.getOutputStream());
		    in = new ObjectInputStream(socketClient.getInputStream());
		    socketClient.setTcpNoDelay(true);
		    System.out.println("Connection to the server established");
		}
		catch(Exception e) {
			System.out.println("Failed to connect to Server");
			return; // terminate thread
		}
		// read initial game statistics then update client into them
		try {
			while (true) {
				MorraInfo data = (MorraInfo) in.readObject();
				if (data == null) { // client is in queue, send null back and forth until out of queue
					callback.accept(null);
				} else {
					stats = data;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Did not receive initial game stats");
		}
		if (playerName != null) {
			sendName();
			System.out.println("Player Name: " + playerName);
		} else {
			System.out.println("Name is null");
		}
		
		while(true) {
			try {
				stats = (MorraInfo) in.readObject();
				callback.accept(stats);
			}
			catch(Exception e) {}
		}
	
    }
}