import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Interfaz que define los metodos remotos del Broker
 */

public interface IfaceBrokerClass extends Remote {

	// Metodo remoto para registrar un servidor y un servicio
	public String registerServer(String rname, String operation) throws RemoteException;
	
	// Metodo remoto para devolver una referencia a un servidor
	public String returnServer(String operation) throws RemoteException;
	
}
