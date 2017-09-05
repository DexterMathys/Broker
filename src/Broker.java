import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class Broker extends UnicastRemoteObject implements IfaceBrokerClass {
	
	private HashMap<String,ListOfServers> servers = new HashMap<String,ListOfServers>();

	public Broker() throws RemoteException{
		super();
	}

	public String registerServer(String rname, String operation) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			/* Register the object using Naming.rebind(...) */
			System.out.println("Me llego " + rname);
			System.out.println(" y " + operation);
			ListOfServers listServers = this.servers.get(operation);
			if(listServers == null) {
				// values.add(rname);
				listServers = new ListOfServers();
			}
			listServers.addServer(rname);
			this.servers.put(operation, listServers);
			return rname;
		} catch (Exception e) {
			System.out.println("Hey, an error occurred at Naming.rebind");
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public String returnServer(String operation) throws RemoteException{
		ListOfServers listServers = this.servers.get(operation);
		String result = null;
		if(listServers != null){
			result = listServers.getServer();
		}
		return result;

	}

	public static void main(String args[]) {
		try {
			String rnamebroker = "//localhost:" + Registry.REGISTRY_PORT + "/broker";
			Naming.rebind(rnamebroker, new Broker());
			//ServerClass robject = new ServerClass();
			//String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
			//Naming.rebind(rname, robject);
		} catch (Exception e) {
			System.out.println("Hey, an error occurred at Naming.rebind");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * El main del broker deberia de registrarse a si mismo 
	  	String rname = "//localhost:" + Registry.REGISTRY_PORT + "/broker";
		Naming.rebind(rname, this);
	 * Y tener su interfaz para que los clientes y los servidores le puedan hacer llamados remotas
	 * 
	 * El broker debe definir un método para guardar los "rname" de los servidores junto a los 
	 * servicios que brindan.
	 * 
	 * Y también tiene que definir un método para buscar en su lista de servidores y devolver el
	 * "rname" que sea solicitado por el cliente
	 * 
	 * */

}
