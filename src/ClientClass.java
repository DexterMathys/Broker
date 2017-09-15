import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Arrays;

public class ClientClass {
	
	public static String server = null;

	public ClientClass() {
	}

	private static IfaceServerClass getServer(String server)
	{
		try{ 
			return (IfaceServerClass) Naming.lookup(server);
		}catch (Exception e) {
			System.out.println("Error al obtener el servidor.");
		}
		return null;
	}
	
	private static IfaceBrokerClass getBroker()
	{
		try{
			Scanner sc = new Scanner(System.in);
			IfaceBrokerClass broker;
			System.out.print("Ingrese el host del broker (por defecto es localhost) :");
			String line = sc.nextLine();
			String host = "localhost";
			if (line.split(" ") != null && line.split(" ").length > 0 && line.split(" ")[0] != "" && !(line.split(" ")[0].isEmpty())) {
				host = line.split(" ")[0];
			}
			String rname = "//"+host+":" + Registry.REGISTRY_PORT + "/broker";
			return (IfaceBrokerClass) Naming.lookup(rname);
		}catch (Exception e) {
			System.out.println("Error al conectar con el Broker.");
		}
		return null;
	}

	public static void main(String[] args)
	{
		
		Scanner sc = new Scanner(System.in);
		IfaceBrokerClass broker;
		Boolean seguir = true;
		while (seguir){
			System.out.print("Ingrese el servicio que desea utilizar(list, create, rename, delete) o no ingrese nada para salir: ");
			String line = sc.nextLine();
			String[] service = line.split(" ");
			if (service.length == 0 || service[0].equals("")) {
				seguir = false;
			}else {
				
				//Parametros
				String params = "";
				for(int i=1; i < service.length ; i++) {
					params += service[i] + " ";
				}
				broker = getBroker();
				if (broker != null) {
					try {
						String rnameserver = broker.returnServer(service[0]);
						if(rnameserver == null){
							System.out.println("El servicio "+ service[0] + " no fue encontrado.");	
						}else{
							IfaceServerClass server = getServer(rnameserver);
							if (server != null) {
								try {
									System.out.println(server.operation(service[0], params));
								} catch (Exception e) {
									System.out.println("Error de ejecucion del servicio.");
								}
							}
						}
					} catch (Exception e) {
						System.out.println("Error al solicitar el servidor al Broker.");
					}
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
