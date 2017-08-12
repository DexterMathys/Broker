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

}
