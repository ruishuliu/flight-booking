
public class Plot {
	private String name;
	private double area;
	private String latitude;
	private String longitude;
	
	public Plot(String plotName, double area, String latitude, String longitude) {
		this.name = plotName;
		this.area= area;
		this.latitude = latitude;
		this.longitude = longitude;		
	}
	
	public void setName(String plotName) {
		this.name = plotName;		
	}
	public String getName() {
		return this.name;
	}
	public void setArea(double area ) {
		this.area= area;
	}
	public double getArea() {
		return this.area;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLatitude() {
		return this.latitude;
	}
	public void setLongitude(String longitude) {
		this.latitude = longitude;
	}
	public String getLongitude() {
		return this.longitude;
	}
	public String toString() {
		return "Plot's name: " + this.name + ", area: " + this.area + ", location: " + this.latitude + "," + this.longitude;
	}
}
