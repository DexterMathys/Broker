import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Interfaz que define el metodo remoto del Servidor
 */

public interface IfaceServerClass extends Remote {

	//Metodo que ejecuta un servicio de manejo de directorios
	public String operation(String servicio, String params) throws RemoteException;
	
}
