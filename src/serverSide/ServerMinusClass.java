package serverSide;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServerMinusClass extends ServerClass {
	
	private String operation = "MINUS";
	
	public ServerMinusClass() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Number operation(int a, int b) throws RemoteException {
		System.out.println(a - b);
		return a - b;
	}

}
