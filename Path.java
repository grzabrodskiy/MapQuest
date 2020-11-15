import java.util.Map;
import java.util.LinkedList;
import java.util.List;

public class Path implements Comparable<Path> {

	private List<Connection> connections = new LinkedList<Connection>();
	private double cost;
	private double estimate;
	
	// cache
	private Map<String, City> cities;
	private String source;
	private String destination;
	
	// initiate the path
	public Path(String source,  String destination, Map<String, City> cities){
		
		// init cache
		this.source = source;
		this.destination = destination;
		this.cities = cities;
		
		this.cost = 0.0; // actual cost so far
		this.estimate = estimateDistance(source, destination);
		
		
	};
	
	//only available for calling in extend
	private Path(Path other){
		
		this.source = other.source;
		this.destination = other.destination;
		this.cities = other.cities;
		
		this.cost = other.cost;
		this.destination = other.destination;
		
		this.connections = new LinkedList<Connection>();
		
		for (Connection c : other.connections){
			this.connections.add((Connection) c.clone());
		}
		
	}
	
	
	public double getFullCost(){
		return cost + estimate;
	}
	
	public boolean isComplete(){
		if (connections.size() == 0)
			return false;
		return this.getCity().equalsIgnoreCase(destination);
	}
	
	
	public String getCity(){
		
		if (connections.size() == 0)
			return source;
		return connections.get(connections.size()-1).getCity();
		
	}
	
	public Path extend(Connection c){
		
		Path e = new Path(this);
		
		e.connections.add(c);
		e.cost += c.getDistance();
		e.estimate = estimateDistance(c.getCity(), source);
		
		return e;
		
	
	}
	
	public void print(){
		
		System.out.println("Route: " + ((int) this.cost));

		
		String s = source;
		
		for (Connection c: connections){
			System.out.println(s + " "+ c.getCity() + " " + c.getRoad());
			s = c.getCity();
			
		}
		
	}
	
	@Override
	public int compareTo(Path other){
		
		double diff = this.getFullCost() - other.getFullCost();
	
		return (diff==0?0:(diff>0?1:-1));
	
	}
	
	private double estimateDistance(String city1, String city2){
		try{
			return cities.get(city1).distance(cities.get(city2));
		}
		catch(Exception e){
			return Double.MAX_VALUE;
		}
	}
	
	
	
	
}
