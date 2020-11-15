
public class Connection {

	private String city;
	private String road;
	private double distance;
	
	public Connection(String city, String road, double distance){
		this.city = city;
		this.road = road;
		this.distance = distance;
		
	}
	
	public Connection(String line){
		
		String[] split = line.trim().split("\\s+");
		
		this.city = split[0];
		this.road = split[2];
		this.distance = Double.parseDouble(split[1]);
		
	}
	
	
	public String getCity(){return city;}
	public String getRoad() {return road;}
	public double getDistance() {return distance;}
	
	@Override
	public Object clone(){
		return new Connection(this.city, this.road, this.distance);
	}
	
	@Override
	public String toString(){
		return city + " " + road;
	}
	
}
