import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IfaceBrokerClass extends Remote {

	public String registerServer(String rname, String operation) throws RemoteException;
	public String returnServer(String operation) throws RemoteException;
	
}
