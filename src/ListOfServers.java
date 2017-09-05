import java.util.ArrayList;

public class ListOfServers  {
  private int index = 0;
  private ArrayList<String> servers = new ArrayList<String>(); 

  public ListOfServers() {
    super();
  }

  public ArrayList<String> getServers(){
    return this.servers;
  }

  public String getServer(){
    String result = this.servers.get(index);
    if(index < this.servers.size() -1 ){
      this.index = index + 1;
    }else {
      this.index = 0;
    }
    return result;
  }

  public void addServer(String rname){
    this.servers.add(rname);
  }

}