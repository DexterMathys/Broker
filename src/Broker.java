import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Scanner;
import java.rmi.Naming;
import java.rmi.RemoteException;

/*
 * Clase que implementa la interfaz IfaceBrokerClass.java
 * Esta clase implementa los 2 metodos definidos en la interfaz.
 * Extiende de UnicasRemoteObject para poder ser referenciado de forma remota
 */

public class Broker extends UnicastRemoteObject implements IfaceBrokerClass {
	
	//Variable que tendra un listado de Servidores y servicios
	private HashMap<String,String> servers = new HashMap<String,String>();

	public Broker() throws RemoteException{
		super();
	}

	// Metodo remoto para registrar un servidor y un servicio
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
	
	// Metodo remoto para devolver una referencia a un servidor
	public String returnServer(String operation) throws RemoteException{
		try {
			return this.servers.get(operation);
		} catch (Exception e) {
			System.out.println("Error al retornar el servidor.");
			return null;
		}
	}

	//Metodo main que solicita el host del Broker y lo registra
	public static void main(String args[]) {
		boolean seguir = true;
		while(seguir){
			try {
				Scanner sc = new Scanner(System.in);
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

}
