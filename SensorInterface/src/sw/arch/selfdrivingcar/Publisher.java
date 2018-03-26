package sw.arch.selfdrivingcar;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface Publisher extends Remote {

	public void sendHeartBeat() throws RemoteException;
	
	public void sendData(double x, double y, double z, Date timestamp) throws RemoteException;
	
}
