
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JTextArea;
/**
 * yajing Huang
 * 896243
 * @author huangyajing
 *
 */
@SuppressWarnings("serial")
public class ClientGUI extends JFrame implements ActionListener {

	private JFrame frame;
	private static JTextField textField_1;
	private static JTextArea textArea;
	// private BufferedReader reader;
	private String value;
	static Socket socket;
	Communication cm = new Communication();
	//private BufferedWriter writer;
	//private BufferedReader reader;
	//static Communication cm = new Communication();

	public static void main(String[] args) {
		
		
		try {
			// Create a stream socket and connect it to the server as specified in the
			// config file
			try {
			if(args.length>=2)  {
			Config config = new Config(args[0], Integer.parseInt(args[1]));
			socket = new Socket(config.getServerAddress(), config.getServerPort());
			System.out.println("Connection with server established");
			// ClientGUI(socket);
			// Get the input/output streams for reading/writing data from/to the socket
			Communication cm = new Communication();
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			//BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
			//Launch a new thread in charge of listening for any messages
			//that arrive through the socket's input stream (any data sent by the server)
			MessageListener ml = new MessageListener(reader);
			ml.start();
			}
			else throw new Exception();}catch(Exception e) {
				System.out.println("the input of server address and port may wrong,please check!");
			}
			// cm.ReaderFuc(socket);
			// cm.reader();
		} catch (Exception e) {
			System.out.println("the input of server address and port may wrong,please check!");
		}
		try {
			//ClientGUI cg = new ClientGUI();
			
			ClientGUI window = new ClientGUI();
			//window.Client();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Create the application.
	 */
	public ClientGUI() {

		initialize();
	}

	public static void BackValue(String backValue) {
		// JTextComponent textArea;
		textArea.setText(backValue);

	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//ClientGUI cg1 = new ClientGUI();
		
		cm.WriterFuc(socket);
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {

				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton btnNewButton = new JButton("Search");
		btnNewButton.setBounds(336, 34, 85, 29);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				value = textField_1.getText().trim();
				value = "Search@" + value;
				cm.Write(value);
				//
			}

		});
		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.setBounds(95, 69, 84, 29);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				value = textField_1.getText().trim();
				value = "Delete@" + value;
				cm.Write(value);
				// cm.reader();
			}
		});

		JButton btnNewButton_2 = new JButton("Add");
		btnNewButton_2.setBounds(255, 69, 75, 29);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				value = textField_1.getText().trim();
				value = "Add@" + value;
				cm.Write(value);
				// cm.reader();
			}
		});
		JLabel word = new JLabel("Word");
		word.setBounds(14, 39, 46, 16);

		textField_1 = new JTextField();
		textField_1.setBounds(89, 34, 241, 26);

		textField_1.setColumns(10);

		JTextPane txtpnNoticeFormatOf = new JTextPane();
		txtpnNoticeFormatOf.setBounds(56, 110, 326, 38);
		txtpnNoticeFormatOf.setBackground(Color.LIGHT_GRAY);
		txtpnNoticeFormatOf
				.setText("Notice: format of add is :         word-explanation1;explanation2;explanation3.......");

		textArea = new JTextArea();
		textArea.setBounds(45, 166, 359, 97);
		frame.getContentPane().add(textArea);
		frame.getContentPane().add(textField_1);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(textArea);
		frame.getContentPane().add(textField_1);
		frame.getContentPane().add(word);
		frame.getContentPane().add(btnNewButton_1);
		frame.getContentPane().add(btnNewButton_2);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(txtpnNoticeFormatOf);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
