/**
 * yajing Huang 896243
 * 
 * @author huangyajing
 *
 */

public class Config {

	private int serverPort;
	private String serverAddress;

	public Config(String serverAddress, int serverPort) {
		super();
		this.serverPort = serverPort;
		this.serverAddress = serverAddress;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getServerAddress() {
		return serverAddress;
	}

}
