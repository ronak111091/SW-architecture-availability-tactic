package sw.arch.selfdrivingcar;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PublisherImpl extends UnicastRemoteObject implements Publisher  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8312114790230889802L;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	private Subscriber subscriber;
	
	public PublisherImpl(Subscriber subscriber) throws RemoteException  {
		super();
		// TODO Auto-generated constructor stub
		this.subscriber=subscriber;
	}

	@Override
	public void sendHeartBeat() throws RemoteException {
		// TODO Auto-generated method stub
		if(!this.subscriber.isConnected()){
			this.subscriber.setConnected(true);
		}
		this.subscriber.setReceived(true);
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	@Override
	public void sendData(double x, double y, double z, Date timestamp) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("coordinates [x=("+x+"), y=("+y+"), z=("+z+")], timestamp ["+sdf.format(timestamp)+"]" );
	}
}
