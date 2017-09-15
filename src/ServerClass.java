import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.io.*;

public class ServerClass extends UnicastRemoteObject implements IfaceServerClass {
	
	private static final long serialVersionUID = 1758625231493571910L;
	private String operation = "NOTHING";
	
	public String getOperation() {
		return operation;
	}
	
	private void setOperation(String operation) {
		this.operation = operation;
	}

	public ServerClass() throws RemoteException {
		super();
	}

	@Override
	public String operation(String servicio, String params) throws RemoteException {
		switch (servicio) {
	    	case "list": return list(params);
	    	case "create": return create(params);
	    	case "rename": return rename(params);
	    	case "delete": return delete(params);
	    	default: return "Servicio invalido ";
	    }
	}

	// @Override
	public String list(String path) throws RemoteException {
		try {
			String sDirectorio = path.replaceAll("\\s+","");
			if (sDirectorio == null || sDirectorio.isEmpty() ){
				sDirectorio = "./";	
			}
			System.out.println("Listar los archivos de " + sDirectorio);
			File f = new File(sDirectorio);
	 		File[] ficheros = f.listFiles();
	 		String result = "";
	 		for (int x=0;x<ficheros.length;x++){
	    	  result += ficheros[x].getName() + "\n";
		    }
		    return result;
		} catch (Exception e) {
			System.out.println("Error al listar los archivos.");
			return "Error al listar los archivos";
		}
	}

	// @Override
	public String create(String path) throws RemoteException {
		if (path == null || path.isEmpty() ){
			System.out.println("El cliente envio un parametro vacio");
			return "Error, falta el parametro a crear";
		}
		try {
			// Use relative path for Unix systems
			File f = new File(path.replaceAll("\\s+",""));
			if (f.mkdirs()) {
				System.out.println("Se creo el directorio " + path);
				return "Se creo el directorio";
			}
			System.out.println("Error al crear el directorio.");
			return "Error al crear el directorio";
		} catch (Exception e) {
			System.out.println("Error al crear el directorio.");
			return "Error al crear el directorio";
		}
	}

	// @Override
	public String rename(String params) throws RemoteException {
		if (params == null || params.isEmpty() || params.split(" ").length != 2 ){
			System.out.println("El cliente envio un parametro vacio");
			return "Error, falta el parametro para renombrar";
		}
		String[] paths = params.split(" ");
		File dir = new File(paths[0]);
	    File newName = new File(paths[1]);
	    if ( dir.isDirectory() ) {
	            try {
	            	dir.renameTo(newName);
		            System.out.println("Se renombro el directorio");
		            return "Se renombro el directorio";
				} catch (Exception e) {
					System.out.println("Error al renombrar el directorio.");
					return "Error al renombrar el directorio";
				}
	    } else {
	    	try {
	    		dir.mkdir();
	            dir.renameTo(newName);
	            System.out.println("El directorio no existia, por lo tanto se creo");
	            return "El directiorio no existia, por lo tanto se creo";
			} catch (Exception e) {
				System.out.println("El directorio no existia, se intento crear pero fallo.");
				return "El directorio no existia, se intento crear pero fallo.";
			}
	            
	    }

	}
	
	// @Override
	public String delete(String path) throws RemoteException {
		// TODO Auto-generated method stub
		if (path == null || path.isEmpty() ){
			System.out.println("El cliente envio un parametro vacio");
			return "Error, falta el parametro a eliminar";
		}
		System.out.println("Eliminar " + path);
		try {
			if (new File(path.replaceAll("\\s+","")).delete()) {
				System.out.println("Se elimino " + path);
				return "Se elimino " + path;
			} else {
				System.out.println("Error al intentar eliminar " + path);
				return "Error al intentar eliminar " + path;
			}
		} catch (Exception e) {
			System.out.println("Error al intentar eliminar " + path);
			return "Error al intentar eliminar " + path;
		}
	}
	
	private static IfaceBrokerClass getBroker()
	{
		boolean seguir = true;
		while(seguir){
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
				System.out.println("Error al intentar conectar con el Broker.");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{		
		Scanner sc = new Scanner(System.in);
		IfaceBrokerClass broker;
		System.out.print("Ingrese el host del servidor (por defecto es localhost) :");
		String line = sc.nextLine();
		String host = "localhost";
		if (line.split(" ") != null && line.split(" ").length > 0 && line.split(" ")[0] != "" && !(line.split(" ")[0].isEmpty())) {
			host = line.split(" ")[0];
		}

		boolean seguir = true;
		while(seguir){
			try {
				System.out.print("Ingrese el servicio que desea registrar (list, create, rename, delete) o no ingrese nada para salir: ");
				line = sc.nextLine();
				String[] service = line.split(" ");
				//Parametros
				String params = "";
				for(int i=1; i < service.length ; i++) {
					params += service[i] + " ";
				}
				if (service[0].isEmpty() || service[0].equals("")) {
					seguir = false;
				} else if (!service[0].equals("list") && !service[0].equals("create") && !service[0].equals("rename") && !service[0].equals("delete")) {
					System.out.println("Servicio incorrecto.");
				}else {
					ServerClass server = new ServerClass();
					server.setOperation(service[0]);
		
					// Llamado
					broker = getBroker();
					String operationIsRegisterd = broker.returnServer(service[0]);
					if (operationIsRegisterd != null) {
						System.out.print("El servicio ya se encuentra registrado");
						System.exit(0);
					}else {
						String rname = "//" + host +":" + Registry.REGISTRY_PORT + "/remote/" + service[0];
						Naming.rebind(rname, server);
						String result = broker.registerServer(rname,service[0]);
						if (result != null) {
							System.out.println("Servicio registrado: " + result);
							seguir = false;
						}else{
							System.out.println("No se pudo registrar el servicio en el Broker.");
						}						
						
					}
				}
					
			} catch (Exception e) {
				System.out.println("Error al intentar registrar al servidor.");
				System.out.print("Ingrese el host del servidor (por defecto es localhost) :");
				line = sc.nextLine();
				host = "localhost";
				if (line.split(" ") != null && line.split(" ").length > 0 && line.split(" ")[0] != "" && !(line.split(" ")[0].isEmpty())) {
					host = line.split(" ")[0];
				}
			}
		}
	}
	
	/**
	 * El main del servidor tiene que conocer el "rname" del broker para que pueda decirle a este 
	 * que servicio ofrece de forma remota.
	 */

}
