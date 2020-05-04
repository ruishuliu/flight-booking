import java.util.InputMismatchException;
import java.util.Scanner;

public class PlotMain {
	
	static Scanner  sc = new Scanner(System.in);
	
	public static void main(String[] args) {

		Resident[] persons ;
		Building house;
		Plot plot;
		int i;
		
		System.out.println("Please input the information of the plot, name:");
		String plotName = readString();
		System.out.println("Area:");
		double plotArea= readDouble();
		System.out.println("Latitude:");
		String latitude = readString();
		System.out.println("Longitude");
		String longitude = readString();
		
		plot = new Plot(plotName,plotArea,latitude,longitude);
		
		System.out.println("Please input the information of the building, number of rooms:");
		int rooms = readInt();
		System.out.println("Area:");
		double houseArea= readDouble();
		
		System.out.println("Please input the number of the residents in the house:");
		int number = readInt();
		persons = new Resident[number];
		for ( i=0; i < number ; i++)
			persons[i]= new Resident(readPerson(i+1));
		
		house = new Building (plot,rooms,houseArea,persons);
		
		System.out.println(house);
	}
	
	public static int readInt(){

	      boolean ok = false;
	      int num = 0;
	        
	      do {
	            
	          try {
	              num = sc.nextInt();
	              sc.nextLine();
	              ok = true;
	          }catch( InputMismatchException ime ){
	              sc.nextLine();
	              System.out.print("Failed, try again > ");
	          }
	            
	      }while(!ok);
	        
	      return num;
	        
	  }
	    
	    public static double readDouble(){
	    	boolean ok = false;
	        double num = 0.0;
	        
	        do {
	            
	            try {
	                num = sc.nextDouble();
	                sc.nextLine();
	                ok = true;
	            }catch( InputMismatchException ime ){
	                sc.nextLine();
	                System.out.print("Failed, try again> ");
	            }
	            
	        }while(!ok);
	        
	        return num;
	        
	    }
	    public static String readString(){

	        boolean ok = false;
	        String str=new String("");
	        
	        do {
	            
	            try {
	                str = sc.nextLine();
	                 ok = true;
	            }catch( InputMismatchException ime ){
	                sc.nextLine();
	                System.out.print("Failed, try again> ");
	            }
	            
	        }while(!ok);
	        
	        return str;
	        
	    }	
	    public static Resident readPerson(int num) {
	    	System.out.println("Please input the information of  " + num + ". resident, name:");
	    	String name = readString();
	    	System.out.println("Birthday:");
	    	String birthday = readString();
	    	return new Resident(name,birthday);
	    }
}
