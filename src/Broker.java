import java.rmi.registry.Registry;
import java.util.HashMap;

import java.rmi.Naming;

public class Broker {
	
	private HashMap<String,String> servers = null;

	public Broker() {
		// TODO Auto-generated constructor stub
	}

	public String registerServer(ServerClass server) {
		// TODO Auto-generated method stub
		try{
			/* Register the object using Naming.rebind(...) */
			String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
			Naming.rebind(rname, server);
			this.servers.put(server.getOperation(), rname);
			return rname;
		} catch (Exception e) {
			System.out.println("Hey, an error occurred at Naming.rebind");
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public String returnServer(String operation){
		return this.servers.get(operation);
	}

	public static void main(String args[]) {
		try {
			ServerClass robject = new ServerClass();
			String rname = "//localhost:" + Registry.REGISTRY_PORT + "/remote";
			Naming.rebind(rname, robject);
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
