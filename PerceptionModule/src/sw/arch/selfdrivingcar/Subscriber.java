package sw.arch.selfdrivingcar;

public abstract class Subscriber {

	private boolean connected;
	private boolean received;
	
	public boolean isConnected() {
		return connected;
	}
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	public boolean isReceived() {
		return received;
	}
	public void setReceived(boolean received) {
		this.received = received;
	}
	
}
