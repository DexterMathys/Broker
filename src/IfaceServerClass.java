import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IfaceServerClass extends Remote {

	public String operation(String servicio, String params) throws RemoteException;

	
}
