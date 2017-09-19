import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.Arrays;

/*
 * Clase que ejecuta el lado del cliente del proyecto
 */
public class ClientClass {
	
	public static String server = null;

	public ClientClass() {
	}

	//Metodo que recibe una referencia remota y obtiene un objeto de la clase de la interfaz del Servidor
	private static IfaceServerClass getServer(String server)
	{
		try{ 
			return (IfaceServerClass) Naming.lookup(server);
		}catch (Exception e) {
			System.out.println("Error al obtener el servidor.");
		}
		return null;
	}
	
	//Metodo para obtener un objeto referencia al Broker remoto
	private static IfaceBrokerClass getBroker()
	{
		try{
			Scanner sc = new Scanner(System.in);
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

	//Metodo main para consultar por un servidor remoto y mandarlo a ejecutar un servicio de manejo de directorio
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

}
