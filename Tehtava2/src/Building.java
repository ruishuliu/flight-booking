
public class Building {

	private Plot plot;
	private int room;
	private double area;
	private Resident[] persons;
	
	public Building(Plot plot, int roomNum, double area, Resident[] residents) {
		
		this.plot =plot;
		this.room = roomNum;
		this.area = area;
		this.persons = residents;
	}
	
	
	public void setRoom(int roomNum) {
		this.room = roomNum;
	}
	public int getRoom() {
		return this.room;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public double getArea() {
		return this.area;
	}
	public void setResident(Resident[] residents) {
		this.persons = residents;
	}
	public Resident[] getResident() {
		return this.persons;
	}
	
	public String toString() {
		String str = "Building in plot: \n plot name: " + this.plot.getName() + "\n plot area: " + plot.getArea()
		+ "\n plot location: " + plot.getLatitude() + "," + plot.getLongitude() + "\n rooms: " 
	    + this.room + ", area: " + this.area + "\n" + "Residents:\n";

		int i =0;
		for( i =0; i< this.persons.length; i ++) {
			str += " " + persons[i] + "\n";			
			
		}
		return str;
	}
}
