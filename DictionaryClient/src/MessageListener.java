
import java.io.BufferedReader;
import java.net.SocketException;

/**
 * yajing Huang 896243
 * 
 * @author huangyajing
 *
 */
public class MessageListener extends Thread {

	private BufferedReader reader;

	// ClientGUI cg = new ClientGUI();
	public MessageListener(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public void run() {
		String msg = null;
		try {

			
			// Read messages from the server while the end of the stream is not reached
			while (true) {
				msg = reader.readLine();
				// Print the messages to the console
				ClientGUI.BackValue(msg + "\n");
				// System.out.println(msg);
			}
		} catch (SocketException e) {
			msg = "Socket closed because the server loses connection";
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
