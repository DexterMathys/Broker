import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Arrays;

public class ClientClass {
	
	public static String server = null;

	public ClientClass() {
		// TODO Auto-generated constructor stub
	}

	private static IfaceServerClass callRemote(String host)
	{
		try{ 
			String rname = "//" + host + ":" + Registry.REGISTRY_PORT + "/remote";
			return (IfaceServerClass) Naming.lookup(rname);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// public static String requestServer(String operation, IfaceServerClass broker) {
	// 	return broker.returnServer(operation);
	// }
	
	// public static void executeOperation(int a, int b){
	// 	try {
	// 		IfaceServerClass remote = (IfaceServerClass) Naming.lookup(this.server);
	// 		remote.operation(a, b);
	// 		System.out.println("Done");
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 	}
	// }

	public static void main(String[] args)
	{
		// FileInputStream fstream = null;
		
		if (args.length != 1) {
			System.out.println("1 argument needed: (remote) hostname");
			System.exit(1);
		}
		
		Scanner sc = new Scanner(System.in);
		IfaceServerClass broker;
		Boolean seguir = true;
		while (seguir){
			System.out.print("Ingrese el servicio que desea utilizar(list, create, rename, delete): ");
			String line = sc.nextLine();
			String[] service = line.split(" ");
			if (service.length == 0 || service[0].equals("exit")) {
				seguir = false;
			}else {
				try {
					//Parametros
					String params = "";
					for(int i=1; i < service.length ; i++) {
						params += service[i] + " ";
					}
					// Llamado
					broker = callRemote(args[0]);
					String result = broker.operation(service[0],params);
					System.out.println(result);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * El main del cliente cambia un poco, hasta ahora estaba accediendo directamente al "rname" del
	 * servidor, no se lo estaba preguntando al broker.
	 * Entonces ahora lo que debe hacer es saber el "rname" del broker y preguntarle de forma remota a
	 * través de su interfaz y pedirle al broker el "rname" del servidor, y ahi si entonces ejecutar
	 * al servidor.
	 * 
	 * */

}
