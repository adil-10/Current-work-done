package uk.ac.mmu.advprog.hackathon;
import static spark.Spark.get;

import static spark.Spark.port;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Handles the setting up and starting of the web service
 * You will be adding additional routes to this class, and it might get quite large
 * Feel free to distribute some of the work to additional child classes, like I did with DB
 * @author You, Mainly!
 */
public class AMIWebService {

	/**
	 * Main program entry point, starts the web service
	 * @param args not used
	 */
	public static void main(String[] args) {		
		
		port(8088);
		
		//Simple route so you can check things are working...
		//Accessible via http://localhost:8088/test in your browser
		get("/test", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					return "Number of Entries: " + db.getNumberOfEntries();
				}
			}			
		});
		
		get("/lastsignal", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					String Name = request.queryParams("signal_id");
					return "previous signal for :" + " " + db.lastsignal(Name);
				}
			}			
		});
		
		//works without user interface
		get("/frequency", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					String group = request.queryParams("motorway");
					return db.frequency(group) ;
				}
			}			
		});
		
		get("/groups", new Route() {
			@Override
			public Object handle(Request request, Response response) throws Exception {
				try (DB db = new DB()) {
					//request data to be outputted into an xml type
					response.type("application/xml");
					return db.signalsGroup() ;
				}
			}			
		});
		
		System.out.println("Server up! Don't forget to kill the program when done!");
	}

}
