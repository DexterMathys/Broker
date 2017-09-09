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
		// TODO Auto-generated constructor stub
	}

	@Override
	public String operation(String servicio, String params) throws RemoteException {
		// TODO Auto-generated method stub
		// return null;
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
		// TODO Auto-generated method stub
		System.out.println("Listar los archivos");
		String sDirectorio = path.replaceAll("\\s+","");
		if (sDirectorio == null || sDirectorio.isEmpty() ){
			sDirectorio = "./";	
		}
		File f = new File(sDirectorio);
 		File[] ficheros = f.listFiles();
 		String result = "";
 		for (int x=0;x<ficheros.length;x++){
    	  result += ficheros[x].getName() + "\n";
	    }
	    return result;
	}

	// @Override
	public String create(String path) throws RemoteException {
		if (path == null || path.isEmpty() ){
			System.out.println("El cliente envio un parametro vacio");
			return "Error, falta el parametro a crear";
		}
		// Use relative path for Unix systems
		File f = new File(path.replaceAll("\\s+",""));

		f.mkdirs(); 
		// try {
		// 	//f.createNewFile();
		// } catch (IOException e) {
		// 	System.out.println("No se pudo crear el archivo");
		// 	return "No se pudo crear el archivo";
		// 	//e.printStackTrace();
		// }
		return "Se creo el directorio";
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
	            dir.renameTo(newName);
	            return "Se renombro el directorio";
	    } else {
	            dir.mkdir();
	            dir.renameTo(newName);
	            return "El directiorio no existia por lo tanto se creo";
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
		if (new File(path.replaceAll("\\s+","")).delete()) {
			return "Se elimino " + path;
		} else {
			return "Error al intentar eliminar " + path;
		}
	}
	
	private static IfaceBrokerClass getBroker()
	{
		try{ 
			String rname = "//localhost:" + Registry.REGISTRY_PORT + "/broker";
			return (IfaceBrokerClass) Naming.lookup(rname);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		// FileInputStream fstream = null;
		
		Scanner sc = new Scanner(System.in);
		IfaceBrokerClass broker;
		System.out.print("Ingrese el servicio que desea registrar (list, create, rename, delete): ");
		String line = sc.nextLine();
		String[] service = line.split(" ");
		
		try {
			//Parametros
			String params = "";
			for(int i=1; i < service.length ; i++) {
				params += service[i] + " ";
			}
			ServerClass server = new ServerClass();
			server.setOperation(service[0]);
			String host = "localhost";
			if(service.length > 1){
				host = service[1];
			}
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
				System.out.println("Servicio registrado: " + result);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * El main del servidor tiene que conocer el "rname" del broker para que pueda decirle a este 
	 * que servicio ofrece de forma remota.
	 */

}
