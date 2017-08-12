import java.rmi.Naming;
import java.rmi.registry.Registry;

public class ClientClass {
	
	public String server = null;

	public ClientClass() {
		// TODO Auto-generated constructor stub
	}
	
	public void requestServer(String operation, Broker broker) {
			server = broker.returnServer(operation);
	}
	
	public void executeOperation(int a, int b){
		try {
			IfaceServerClass remote = (IfaceServerClass) Naming.lookup(server);
			remote.operation(a, b);
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
