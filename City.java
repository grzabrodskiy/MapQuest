import java.math.*;

public class City {
	
	private String name;
	private double longitude, latitude;
	
	public City(String name, double latitude, double longitude){
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public City(String line){
		
		try{
		
			String[] split = line.split("\\s+");
		
			this.name = split[0];
			this.latitude = Double.parseDouble(split[1]);
			this.longitude = Double.parseDouble(split[2]);
		}
		catch (Exception e){}
			
		
	}
	
	public final double RADIUS = 3959; // mi 6371; //km
	
	public String getName(){return name;}
	
	
	public double getLongitude(){return longitude;}
	
	public double getLatitude(){return latitude;}	
	
	public double getLongRad() {return longitude * Math.PI/180;}
	
	public double getLatRad() {return longitude * Math.PI/180;}
	
	public double distance(City other){
		
		//haversine formula
		
		double delta_phi = this.getLatRad() - other.getLatRad(); //in radian
		double delta_lambda = this.getLongRad() - other.getLongRad(); 
		
		double a = Math.sin(delta_phi/2)* Math.sin(delta_phi/2) + 
				Math.cos(this.getLatRad())* Math.cos(other.getLatRad())*
				Math.sin(delta_lambda/2)*Math.sin(delta_lambda/2);
		
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		double d = RADIUS * c;
		
		return d;
		
	}
	
	@Override
	public boolean equals(Object other){
		return this.name.equalsIgnoreCase(((City) other).name);
	}
	
	@Override
	public String toString(){
		return name;
	}
	

}
