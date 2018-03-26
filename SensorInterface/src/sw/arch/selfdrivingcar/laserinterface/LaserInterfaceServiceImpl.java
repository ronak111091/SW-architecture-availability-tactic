package sw.arch.selfdrivingcar.laserinterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.opencsv.CSVReader;

import sw.arch.selfdrivingcar.Publisher;

public class LaserInterfaceServiceImpl extends Thread implements LaserInterfaceService {
	
	private static boolean interupted;
	
	private String id;
	
	private static final String URL = "rmi://localhost/heartbeat_publisher";
	
	public LaserInterfaceServiceImpl(String id) {
		super();
		this.id = id;
	}

	@Override
	public void getLaserSensorData() {
		// TODO Auto-generated method stub
		System.out.println("Called getLaserSensorData");
	}

	public void initiateHeartBeat() {
		try {
			Context namingContext = new InitialContext();
			Publisher publisher = (Publisher) namingContext.lookup(URL);
			while (!interupted) {
				System.out.println("Sending heartbeat....");
				publisher.sendHeartBeat();
				sleep(5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		if(this.id.equals("heartbeat_thread"))
			this.initiateHeartBeat();
		else{
			this.formatAndSendData();
		}
	}
	
	@Override
	public void formatAndSendData(){
		CSVReader reader = null;
		try {
			Context namingContext = new InitialContext();
			Publisher publisher = (Publisher) namingContext.lookup(URL);
			InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("aceleracaoLinear_terra.csv");
			reader = new CSVReader(new InputStreamReader(in));
			reader.readNext();
			String[] row = reader.readNext();
			while(row!=null){
				double x = Double.parseDouble(row[2]);
				double y = Double.parseDouble(row[3]);
				double z = Double.parseDouble(row[4]);
				System.out.println("coordinates [x=("+x+"), y=("+y+"), z=("+z+")]");
				//send data to perception module
				row = reader.readNext();
				publisher.sendData(x, y, z, new Date());
				sleep(1000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
			interupted=true;
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		LaserInterfaceServiceImpl heartBeatThread = new LaserInterfaceServiceImpl("heartbeat_thread");
		LaserInterfaceServiceImpl dataThread = new LaserInterfaceServiceImpl("data_thread");
		heartBeatThread.start();
		dataThread.start();
	}
}
