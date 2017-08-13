import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IfaceServerClass extends Remote {

	public String operation(String servicio, String params) throws RemoteException;
  // public String list(String path) throws RemoteException;
  // public String create(String params) throws RemoteException;
  // public String rename(String params) throws RemoteException;
  // public String delete(String params) throws RemoteException;

	
}
