
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
/**
 * yajing Huang
 * 896243
 * @author huangyajing
 *
 */
public class ClientConnection extends Thread {
	// public static String Server.path;
	public static String[][] dicWordList = null;
	File file = new File(Server.path);


	private Socket clientSocket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private int clientNum;
	private String word;
	private DictionaryFunction df;

	// public static List<String> dic = readTxt(Server.path);
	public ClientConnection(Socket clientSocket, int clientNum, DictionaryFunction df) {
		try {
			this.clientSocket = clientSocket;
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
			this.clientNum = clientNum;
			this.df = df;
		} catch (Exception e) {
			// System.out.println(Thread.currentThread().getName() + clientNum + "off
			// line");
			Server.Screen(Thread.currentThread().getName() + clientNum + "off line");
		}
	}

	@Override
	public void run() {

		try {
			Server.Screen(Thread.currentThread().getName() + " - Reading messages from client's " + clientNum
					+ " connection");

			// System.out.println(Thread.currentThread().getName() + " - Reading messages
			// from client's " + clientNum
			// + " connection");

			String clientMsg;
			String meaning;
			//int i = 0;
			// List<ClientConnection> clients =
			// ServerState.getInstance().getConnectedClients();
			while (true) {
				// List<ClientConnection> clients =
				// ServerState.getInstance().getConnectedClients();

				// for (ClientConnection client : clients) {
				// if (client.clientNum == clientNum) {
				clientMsg = reader.readLine();
				Server.Screen(Thread.currentThread().getName() + " - Message from client " + clientNum + " received: "
						+ clientMsg);
				// System.out.println(Thread.currentThread().getName() + " - Message from client
				// " + clientNum
				// + " received: " + clientMsg);
				String[] info = clientMsg.split("@");
				String action = info[0];

				if (info.length > 1) {
					word = info[1];
				} else
					try {
						throw new Exception();
					} catch (Exception e) {
						meaning = "Please enter an English word!";
						// for (ClientConnection client : clients) {
						// if (client.clientNum == clientNum) {
						// client.write(meaning);}}
					}

				if (action.equals("Search")) {

					meaning = df.Search(word);
					// for (ClientConnection client : clients) {
					// if (client.clientNum == clientNum) {
					// client.write(meaning);}}
				} else if (action.equals("Delete")) {

					meaning = df.Delete(word);
					// for (ClientConnection client : clients) {
					// if (client.clientNum == clientNum) {
					// client.write(meaning);}}
				} else if (action.equals("Add")) {

					meaning = df.Add(word);
					// for (ClientConnection client : clients) {
					// if (client.clientNum == clientNum) {
					// client.write(meaning);}}
				}

				else
					try {
						throw new Exception();
					} catch (Exception e) {
						meaning = "Parsing fail, Please try again~";
						// for (ClientConnection client : clients) {
						// if (client.clientNum == clientNum) {
						// client.write(meaning);}}
					}
				List<ClientConnection> clients = ServerState.getInstance().getConnectedClients();

				for (ClientConnection client : clients) {
					if (client.clientNum == clientNum) {

						client.write(meaning + "\n");

					}
				}

			}
			

		} catch (

		Exception e) {
			Server.Screen("client" + clientNum + " off line");
			// System.out.println("client"+clientNum+" off line");
		}
	}

	// Needs to be synchronized because multiple threads can me invoking this method
	// at the same
	// time
	public synchronized void write(String msg) {
		try {
			writer.write(msg);
			writer.flush();
			System.out.println(Thread.currentThread().getName() + " - Message sent to client " + clientNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
