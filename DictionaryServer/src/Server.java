
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

/**
 * yajing Huang 896243
 * 
 * @author huangyajing
 *
 */
public class Server {

	private JFrame frame;
	public static String path;
	private static JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		if(args.length>=2)  {
		path = args[1];
		}
		else throw new Exception();}catch(Exception e) {
			System.out.println("the input of port number or file address may wrong,please check!");
		}
		// path = "dic.txt";
		/**
		 * for(int i=0;i<dic.size();i++) { System.out.println(dic.get(i));}
		 */

		ServerSocket listeningSocket = null;
		DictionaryFunction df = new DictionaryFunction();
		try {
			Server window = new Server();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			listeningSocket = new ServerSocket(Integer.parseInt(args[0]));

			textArea.setText(
					Thread.currentThread().getName() + " - Server listening on port " + args[0] + " for a connection");
			// System.out.println(Thread.currentThread().getName() +
			// " - Server listening on port "+ args[0] +" for a connection");

			int clientNum = 0;

			// Listen for incoming connections for ever
			while (true) {

				// Accept an incoming client connection request
				Socket clientSocket = listeningSocket.accept();
				textArea.setText(Thread.currentThread().getName() + " - Client conection accepted");
				// System.out.println(Thread.currentThread().getName()
				// + " - Client conection accepted");
				clientNum++;

				// Create a client connection to listen for and process all the messages
				// sent by the client
				ClientConnection clientConnection = new ClientConnection(clientSocket, clientNum, df);
				clientConnection.setName("Thread" + clientNum);
				clientConnection.start();

				// Update the server state to reflect the new connected client
				ServerState.getInstance().clientConnected(clientConnection);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (listeningSocket != null) {
				try {
					listeningSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			Server window = new Server();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public Server() {
		initialize();
	}

	public static void Screen(String screen) {
		// JTextComponent textArea;
		textArea.append(screen + "\n");

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {

				System.exit(0);
				;

			}
		});
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		textArea = new JTextArea();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addGap(16)
						.addComponent(textArea, GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnNewButton).addGap(18)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addContainerGap(24, Short.MAX_VALUE)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE).addGap(20))
				.addGroup(groupLayout.createSequentialGroup().addGap(115).addComponent(btnNewButton)
						.addContainerGap(134, Short.MAX_VALUE)));
		frame.getContentPane().setLayout(groupLayout);
	}
}
