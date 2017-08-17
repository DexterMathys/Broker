import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServerClass extends UnicastRemoteObject implements IfaceServerClass {
	
	private static final long serialVersionUID = 1758625231493571910L;
	private String operation = "NOTHING";
	
	public String getOperation() {
		return operation;
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
		String sDirectorio = path;
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
	public String create(String params) throws RemoteException {
		return "";
	}

	// @Override
	public String rename(String params) throws RemoteException {
		return "";
	}
	
	// @Override
	public String delete(String path) throws RemoteException {
		// TODO Auto-generated method stub
		if (path == null || path.isEmpty() ){
			System.out.println("El cliente envio un parametro vacio");
			return "Error, falta el parametro a eliminar";
		}
		System.out.println("Eliminar " + path);
		if (new File(path).delete()) {
			return "Se elimino " + path;
		} else {
			return "Error al intentar eliminar " + path;
		}
	}

}
