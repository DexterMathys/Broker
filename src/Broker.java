import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Scanner;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Broker extends UnicastRemoteObject implements IfaceBrokerClass {
	
	private HashMap<String,String> servers = new HashMap<String,String>();

	public Broker() throws RemoteException{
		super();
	}

	public String registerServer(String rname, String operation) throws RemoteException {
		try {
			this.servers.put(operation, rname);	
			System.out.println("Se registro " + rname);
			return rname;
		} catch (Exception e) {
			System.out.println("Error al registrar el servidor.");
			return null;
		}	
			
	}
	
	public String returnServer(String operation) throws RemoteException{
		try {
			return this.servers.get(operation);
		} catch (Exception e) {
			System.out.println("Error al retornar el servidor.");
			return null;
		}
	}

	public static void main(String args[]) {
		boolean seguir = true;
		while(seguir){
			try {
				Scanner sc = new Scanner(System.in);
				IfaceBrokerClass broker;
				System.out.print("Ingrese el host del broker (por defecto es localhost) :");
				String line = sc.nextLine();
				String host = "localhost";
				if (line.split(" ") != null && line.split(" ").length > 0 && line.split(" ")[0] != "" && !(line.split(" ")[0].isEmpty())) {
					host = line.split(" ")[0];
				}
				String rnamebroker = "//"+host+":" + Registry.REGISTRY_PORT + "/broker";
				Naming.rebind(rnamebroker, new Broker());
				System.out.println("Se registro el broker " + rnamebroker);
				seguir = false;
			} catch (Exception e) {
				System.out.println("Error al intentar registrar el Broker.");
			}
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
