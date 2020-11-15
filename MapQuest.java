import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


public class MapQuest {

	private static boolean DEBUG = true;
	
	//priority queue of the paths
	private PriorityQ<Path> q = new PriorityQ<Path>();
	// cities
	private Map<String, City> cities;
	
	// connections from each city
	private Map<String, Connection[]> map;
	
	private List<String> visited = new LinkedList<String>();
	
	//connections from source to each destination
	//private Map<String, Connection> prev = new HashMap<String, Connection>();
	// cost of each destination	(already traversed)	
	private Map<String, Double> distances = new HashMap<String, Double>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		if (args.length != 3){
			echo("Usage: MapQuest input_file source_city destination_city");
			return;
		}
		
		MapQuest m = new MapQuest(args[0]);
		
		echo("");

		Path p = m.pathFinder(args[1], args[2]);
		
		if(p != null)
			p.print();
		else
			echo("No Route Found");
		
		
		echo(" ");
		
		// printResults(m, args[1], args[2]);
		// echo(" ");
		
	}

	
	public MapQuest(String file){
		cities = new HashMap<String, City>();
		map = new HashMap<String, Connection[]>();
		parseInput(file);
		
	}
	
	private void parseInput(String file){
		BufferedReader br = null;
		String line;
		int numCon;
		City city;
		
		try{
			br = new BufferedReader(new FileReader(file)); 
		    //first line - number of cities
		    line = br.readLine();
		    echo(line);
		    int numCities = Integer.parseInt(line);
		    
		    for (int i = 0; i < numCities; i++){
		    	line = br.readLine();
		    	echo(line);
		    	city = new City(line);
		    	cities.put(city.getName(), city);
		    	
		    }
		    // look at connections per city
		    while((line = br.readLine()) != null){  
		    	echo(line);
			    if (line.trim().length() == 0)
			    	continue;
			    
		    	String[] split = line.split("\\s+");
		    		
		    	numCon = Integer.parseInt(split[1]);
		    	Connection[] cons = new Connection[numCon];
		    		
		    	for (int j = 0; j < numCon; j++){
		    		line = br.readLine();
		    		echo(line);
		    		cons[j] = new Connection(line);
		    	}
		    	map.put(split[0], cons);
		    		
		    }
		}
		catch(Exception e){
			System.out.println("Cannot parse input file: " + e.getMessage());
		}
		finally{
			try{br.close();}catch(Exception e){}
		}
		    	
	}
		
	Path pathFinder(String source,String target){
		
		// initializing the queue with the source 
		Path p = new Path(source, target, cities);
		
		if (!cities.containsKey(source)){
			echo("Invalid source city: " + source);
			return null;
		}

		if (!cities.containsKey(target)){
			echo("Invalid destination city: " + target);
			return null;
		}
		
		if (source.equalsIgnoreCase(target)){
			echo("Source and destination cities are the same");
			return null;
		}
		

		
		q.enqueue(p); 
		distances.put(source, 0.);
		

		while (!q.isEmpty()){
			Path guess = q.dequeue();
			
			if (guess.isComplete())
				return guess;
			
			visited.add(guess.getCity()); 
			
			for (Connection c: map.get(guess.getCity())) {
				Path newPath = guess.extend(c);
				if(!visited.contains(c.getCity())){
					double newDist = distances.get(guess.getCity()) + c.getDistance();
					if (newDist < (distances.containsKey(c.getCity())?distances.get(c.getCity()): Double.MAX_VALUE)){
						distances.put(c.getCity(), newDist);
						
						//Connection pr = new Connection(guess.getCity(), c.getRoad(), c.getDistance());
						//prev.put(c.getCity(), pr);
						
					}
					q.enqueue(newPath);
				}
				
			}
		}
		
		return null;
		
		
	}
	
	private static void p(String s){
		if (DEBUG)
			System.out.println(s);
	}
	
	private static void echo(String s){
		System.out.println(s);
		
	}
	
	/*
	private static void printResults(MapQuest m, String source, String destination){
		String s = destination;
		double d = 0;
		Stack<String> stack = new Stack<String>();
		
		do{	
			Connection pr = m.prev.get(s);
			stack.push(pr.getCity() + " " + s + " " + pr.getRoad());
			s = pr.getCity();
			d += pr.getDistance();
		} while (!s.equalsIgnoreCase(source));
		
		echo("Route: " + ((int)d));
		
		while(!stack.isEmpty()){
			echo(stack.pop());
		}
		
		
	}
	*/
	
	
}
