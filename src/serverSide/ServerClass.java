package serverSide;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerClass extends UnicastRemoteObject implements IfaceServerClass {
	
	private String operation = "NOTHING";
	
	public String getOperation() {
		return operation;
	}

	public ServerClass() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Number operation(int a, int b) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
