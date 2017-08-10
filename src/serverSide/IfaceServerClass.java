package serverSide;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IfaceServerClass extends Remote {

	public Number operation(int a, int b) throws RemoteException;
	
}
