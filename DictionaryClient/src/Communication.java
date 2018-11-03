import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * yajing Huang 896243
 * 
 * @author huangyajing
 *
 */
public class Communication {
	private BufferedWriter writer;
	// private BufferedReader reader;

	public void WriterFuc(Socket socket) {
		// ClientGUI cg = new ClientGUI();
		// BufferedReader br = new BufferedReader(new FileReader(file));
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void Write(String content) {
		try {
			writer.write(content + "\n");
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
