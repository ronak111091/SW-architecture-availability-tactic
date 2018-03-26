package sw.arch.selfdrivingcar.lasermapper;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import sw.arch.selfdrivingcar.Publisher;
import sw.arch.selfdrivingcar.PublisherImpl;
import sw.arch.selfdrivingcar.Subscriber;

public class LaserMapperServiceImpl extends Subscriber implements LaserMapperService{

	private Publisher publisher;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public LaserMapperServiceImpl() throws RemoteException {
		publisher = new PublisherImpl(this);
	}

	private void checkIfAlive() throws InterruptedException {
		int retryAttempts = 0;
		while (true) {
			if (isConnected()) {
				if (isReceived()) {
					System.out.println("Sensor interface is alive");
					setReceived(false);
					Thread.sleep(5000);
				} else {
					System.out.println("No heartbeat received!");
					setConnected(false);
					System.out.println("Restart Sensor Interface...");
				}
			}else{
				Thread.sleep(1000);
			}
		}
	}

	private void start() {
		try {
			bindObjectWithRMIRegistry();
		} catch (NamingException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			checkIfAlive();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void bindObjectWithRMIRegistry() throws NamingException, RemoteException {
		Context namingContext = new InitialContext();
		namingContext.bind("rmi:heartbeat_publisher", publisher);
		System.out.println("Listening to heartbeats.....");
	}
	
	@Override
	public void deriveEnvironmentMap(String name, double x, double y, double z, Date timestamp){
//		System.out.println("creates a map of the surrounding environment.");
		System.out.println(name+"--> coordinates [x=("+x+"), y=("+y+"), z=("+z+")], timestamp ["+sdf.format(timestamp)+"]" );
	}

	public static void main(String[] args) throws RemoteException, NamingException {
		LaserMapperServiceImpl laserMapperServiceImpl = new LaserMapperServiceImpl();
		laserMapperServiceImpl.start();
	}

}
