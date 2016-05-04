package REST;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import SOAP.client.client.*;
import REST.MysqlConnector;

@Path("/REST")
public class TheatreMashups {
	
	BroadwayServiceService serviceBroadway = new BroadwayServiceService();
	NYTimesServiceService serviceNYTimes = new NYTimesServiceService();
	PlaybillServiceService servicePlaybill = new PlaybillServiceService();


	ReviewServiceInterface serverBroadway = (ReviewServiceInterface) serviceBroadway.getBroadwayServicePort();
	ReviewServiceInterface serverNYTimes = (ReviewServiceInterface) serviceNYTimes.getNYTimesServicePort();
	ReviewServiceInterface serverPlaybill = (ReviewServiceInterface) servicePlaybill.getPlaybillServicePort();

		String dbName = "Theatre_Mashup";
		String user = "root";
		String pass = "1234";
		MysqlConnector connector = new MysqlConnector(dbName, user, pass);
		Connection conn = connector.getConnection();

	
	public static void main(String args[]) {
		
		String dbName = "Theatre_Mashup";
		String user = "root";
		String pass = "1234";
		MysqlConnector connector = new MysqlConnector(dbName, user, pass);
		Connection conn = connector.getConnection();
		
		connector.createDB(conn, dbName);
		connector.createReviewsTable(conn);
    };
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/plays")
	public String getPlays() {
		return "TRUE";
	}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/plays/all")
	public Response getAllPlays() {
		String playNames = "";
		boolean checkDB = connector.isEmpty();
		if(!checkDB) {
			playNames = connector.getPlays();
		} else if (checkDB) {
			ArrayList<String> plays = new ArrayList<String>();

		ConcurrentHashMap<Integer, String[]> allBroadwayReviews = serverBroadway.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getAllReviews();
		try {
			String genre = "";
			String critique = "";
			String source = "";
			boolean success = false;
		Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
		for(Integer key :broadwayKeys){
			String[] review = allBroadwayReviews.get(key);
			source = "Broadway";
			String name = review[0].trim();
			String title = review[1].trim();
			String date = review[2].trim();
			if(review.length == 5) {
				genre = review[3].trim();
				critique = review[4].trim();
			} else {
				critique = review[3].trim();				
			}
			TheatreMashup play_review = new TheatreMashup();
			play_review.setReviewSource(source);
			play_review.setPlayName(name);
			play_review.setTitle(title);
			play_review.setReviewDate(date);
			play_review.setGenre(genre);
			play_review.setCritique(critique);
			success = connector.insertReview(play_review);
			if (!plays.contains(name)){
				plays.add(name);
			}
		}
		if(!success){
		    System.out.println("Error!");
		}else{
			System.out.println("You have successfully added a set of " + source + " reviews to the database for future use.");
		}
		Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
		for(Integer key :nytimesKeys){
			String[] review = allNYTimesReviews.get(key);
			success = false;
			source = "NYTimes";
			String name = review[0].trim();
			String title = review[1].trim();
			String date = review[2].trim();
			if(review.length == 5) {
				genre = review[3].trim();
				critique = review[4].trim();
			} else {
				critique = review[3].trim();				
			}
			TheatreMashup play_review = new TheatreMashup();
			play_review.setReviewSource(source);
			play_review.setPlayName(name);
			play_review.setTitle(title);
			play_review.setReviewDate(date);
			play_review.setGenre(genre);
			play_review.setCritique(critique);
			success = connector.insertReview(play_review);
			if (!plays.contains(name)){
				plays.add(name);
			}
		}
		if(!success){
		    System.out.println("Error!");
		}else{
			System.out.println("You have successfully added a set of " + source + " reviews to the database for future use.");
		}
		Set<Integer> playbillKeys=allPlaybillReviews.keySet();
		for(Integer key :playbillKeys){
			String[] review = allPlaybillReviews.get(key);
			success = false;
			source = "Playbill";
			String name = review[0].trim();
			String title = review[1].trim();
			String date = review[2].trim();
			if(review.length == 5) {
				genre = review[3].trim();
				critique = review[4].trim();
			} else {
				critique = review[3].trim();				
			}
			TheatreMashup play_review = new TheatreMashup();
			play_review.setReviewSource(source);
			play_review.setPlayName(name);
			play_review.setTitle(title);
			play_review.setReviewDate(date);
			play_review.setGenre(genre);
			play_review.setCritique(critique);
			success = connector.insertReview(play_review);
			if (!plays.contains(name)){
				plays.add(name);
			}
		}
		if(!success){
		    System.out.println("Error!");
		}else{
			System.out.println("You have successfully added a set of " + source + " reviews to the database for future use.");
		}
		} catch (Exception e) {
			return Response.status(500).build();
		}
		playNames = String.join("|", plays);
		}
		return Response.ok(playNames,"text/plain").build();
	}
	
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/plays/genres")
	public Response getAllGenres() {
		String playGenres = "";
		boolean checkDB = connector.isEmpty();
		if(!checkDB) {
			playGenres = connector.getGenres();
		} else if (checkDB) {
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getAllReviews();
		ArrayList<String> genres = new ArrayList<String>();
		Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
		for(Integer key :broadwayKeys){
			String[] review = allBroadwayReviews.get(key);
			if(review.length == 5) {
				String genre = review[3].trim();
				if (!genres.contains(genre)){
					genres.add(genre);
				}
			}
		}
		Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
		for(Integer key :nytimesKeys){
			String[] review = allNYTimesReviews.get(key);
			if(review.length == 5) {
				String genre = review[3].trim();
				if (!genres.contains(genre)){
					genres.add(genre);
				}
			}
		}
		Set<Integer> playbillKeys=allPlaybillReviews.keySet();
		for(Integer key :playbillKeys){
			String[] review = allPlaybillReviews.get(key);
			if(review.length == 5) {
				String genre = review[3].trim();
				if (!genres.contains(genre)){
					genres.add(genre);
				}
			}
		}
		playGenres = String.join("|", genres);
		}
		return Response.ok(playGenres,"text/plain").build();
	}
	
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/plays/{genre}")
	public Response getPlaysByGenre(@PathParam("genre") String genre) {
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getReviewsByGenre(genre);
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getReviewsByGenre(genre);
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getReviewsByGenre(genre);
		ArrayList<String> plays = new ArrayList<String>();
		Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
		for(Integer key :broadwayKeys){
			String[] review = allBroadwayReviews.get(key);
			String name = review[0].trim();
			if (!plays.contains(name)){
				plays.add(name);
			}
		}
		Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
		for(Integer key :nytimesKeys){
			String[] review = allNYTimesReviews.get(key);
			String name = review[0].trim();
			if (!plays.contains(name)){
				plays.add(name);
			}
		}
		Set<Integer> playbillKeys=allPlaybillReviews.keySet();
		for(Integer key :playbillKeys){
			String[] review = allPlaybillReviews.get(key);
			String name = review[0].trim();
			if (!plays.contains(name)){
				plays.add(name);
			}
		}
		String playNames = String.join("|", plays);
		return Response.ok(playNames,"text/plain").build();
	}
	
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/plays/{name}")
	public Response getReviewsByName(@PathParam("name") String playName) {
		String name = playName.replaceAll("_", " ");
		String html = "";
		boolean checkDB = connector.isEmpty();
		if(!checkDB) {
			html = connector.getName(name);
		} else if (checkDB) {
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getReviewsByName(name);
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getReviewsByName(name);
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getReviewsByName(name);
		Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
		for(Integer key :broadwayKeys){
			String[] review = allBroadwayReviews.get(key);
			String title = review[1].trim();
			String date = review[2].trim();
			if(review.length == 5) {
				String genre = review[3].trim();
				String critique = review[4].trim();
				html += "<h1>" + title + "</h1> <br> <h2>Publication: Broadway &emsp&emsp&emsp&emsp&emsp&emsp Review Date: " + date + "</h2> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
			} else {
				String critique = review[3].trim();
				html += "<h1>" + title + "</h1> <br> <h2>Publication: Broadway &emsp&emsp&emsp&emsp&emsp&emsp Review Date: " + date + "</h2> <br> <p>" + critique + "</p> <br> <br>";
				
			}
			
		}
		Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
		for(Integer key :nytimesKeys){
			String[] review = allNYTimesReviews.get(key);
			String title = review[1].trim();
			String date = review[2].trim();
			if(review.length == 5) {
				String genre = review[3].trim();
				String critique = review[4].trim();
				html += "<h1>" + title + "</h1> <br> <h2>Publication: NYTimes &emsp&emsp&emsp&emsp&emsp&emsp Review Date: " + date + "</h2> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
			} else {
				String critique = review[3].trim();
				html += "<h1>" + title + "</h1> <br> <h2>Publication: NYTimes &emsp&emsp&emsp&emsp&emsp&emsp Review Date: " + date + "</h2> <br> <p>" + critique + "</p> <br> <br>";
				
			}
			
		}
		Set<Integer> playbillKeys=allPlaybillReviews.keySet();
		for(Integer key :playbillKeys){
			String[] review = allPlaybillReviews.get(key);
			String title = review[1].trim();
			String date = review[2].trim();
			if(review.length == 5) {
				String genre = review[3].trim();
				String critique = review[4].trim();
				html += "<h1>" + title + "</h1> <br> <h2>Publication: Playbill &emsp&emsp&emsp&emsp&emsp&emsp Review Date: " + date + "</h2> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
			} else {
				String critique = review[3].trim();
				html += "<h1>" + title + "</h1> <br> <h2>Publication: Playbill &emsp&emsp&emsp&emsp&emsp&emsp Review Date: " + date + "</h2> <br> <p>" + critique + "</p> <br> <br>";
				
			}
			
		}
		}
		return Response.ok(html,"text/html").build();
	}
	
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/plays/dates") 
	public Response getReviewsByDateSorted() {
		String html = "";
		boolean checkDB = connector.isEmpty();
		if(!checkDB) {
			html = connector.getDates();
		} else if (checkDB) {
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getAllReviews();
		ArrayList<String> dates = new ArrayList<String>();
		Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
		for(Integer key :broadwayKeys){
			String[] review = allBroadwayReviews.get(key);
			String date = review[2].trim();
			if (!dates.contains(date)){
				dates.add(date);
			}
		}
		Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
		for(Integer key :nytimesKeys){
			String[] review = allNYTimesReviews.get(key);
			String date = review[2].trim();
			if (!dates.contains(date)){
				dates.add(date);
			}
			
		}
		Set<Integer> playbillKeys=allPlaybillReviews.keySet();
		for(Integer key :playbillKeys){
			String[] review = allPlaybillReviews.get(key);
			String date = review[2].trim();
			if (!dates.contains(date)){
				dates.add(date);
			}
			
		}
		Collections.sort(dates, new Comparator<String>() {
	        DateFormat f = new SimpleDateFormat("MM/dd/yy");
	        @Override
	        public int compare(String o1, String o2) {
	            try {
	                return f.parse(o1).compareTo(f.parse(o2));
	            } catch (Exception e) {
	                throw new IllegalArgumentException(e);
	            }
	        }  
	    });
		
		for (int i = 0; i < dates.size(); i++) {
            String currentDate = dates.get(i);
            html += currentDate + "|" + getReviewsByDate(currentDate);
        }
		}
		return Response.ok(html,"text/plain").build();
	}
	
	// get all the reviews from a certain date from all sources 
	public String getReviewsByDate(String reviewDate) {
		String formattedDate = reviewDate.replaceAll("_", "/");
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getReviewsByDate(formattedDate);
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getReviewsByDate(formattedDate);
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getReviewsByDate(formattedDate);
		String html = "";
		Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
		for(Integer key :broadwayKeys){
			String[] review = allBroadwayReviews.get(key);
			String title = review[1].trim();
			html += title + "|";
		}
		Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
		for(Integer key :nytimesKeys){
			String[] review = allNYTimesReviews.get(key);
			String title = review[1].trim();
			html += title + "|";
		}
		Set<Integer> playbillKeys=allPlaybillReviews.keySet();
		for(Integer key :playbillKeys){
			String[] review = allPlaybillReviews.get(key);
			String title = review[1].trim();
			html += title + "|";
		}
		
		return html;
	}
	
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/plays/source")
	public Response getReviewsBySource() {
		String html = "";
		boolean checkDB = connector.isEmpty();
		if(!checkDB) {
			html = connector.getSource();
			String[] line_split = html.split("\\|");
	    	for(int i = 0; i < line_split.length; i++) {
	    		String source_or_review = line_split[i];
	    		if(source_or_review.equals("Broadway") || source_or_review.equals("NYTimes") || source_or_review.equals("Playbill")) {
	    			html += "<h1>" + source_or_review + " Reviews: </h1> <br> <br>";
	    		} else {
	    			html += source_or_review;
	    		}
	    	}
		} else if (checkDB) {
		html += "<h1>Broadway Reviews: </h1> <br> <br>";
			ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getAllReviews();
			Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
			for(Integer key :broadwayKeys){
				String[] review = allBroadwayReviews.get(key);
				String title = review[1].trim();
				String date = review[2].trim();
				if(review.length == 5) {
					String genre = review[3].trim();
					String critique = review[4].trim();
					html += "<h2>" + title + "</h2> <br> <h3>Review Date: " + date + "</h3> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
				} else {
					String critique = review[3].trim();
					html += "<h2>" + title + "</h2> <br> <h3>Review Date: " + date + "</h3> <br> <p>" + critique + "</p> <br> <br>";
					
				}
				
			}
			html += "<br> <br> <h1>NYTimes Reviews: </h1> <br> <br>";
			ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getAllReviews();
			Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
			for(Integer key :nytimesKeys){
				String[] review = allNYTimesReviews.get(key);
				String title = review[1].trim();
				String date = review[2].trim();
				if(review.length == 5) {
					String genre = review[3].trim();
					String critique = review[4].trim();
					html += "<h2>" + title + "</h2> <br> <h3>Review Date: " + date + "</h3> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
				} else {
					String critique = review[3].trim();
					html += "<h2>" + title + "</h2> <br> <h3>Review Date: " + date + "</h3> <br> <p>" + critique + "</p> <br> <br>";
					
				}
				
			}
			html += "<br> <br> <h1>Playbill Reviews: </h1> <br> <br>";
			ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getAllReviews();
			Set<Integer> playbillKeys=allPlaybillReviews.keySet();
			for(Integer key :playbillKeys){
				String[] review = allPlaybillReviews.get(key);
				String title = review[1].trim();
				String date = review[2].trim();
				if(review.length == 5) {
					String genre = review[3].trim();
					String critique = review[4].trim();
					html += "<h2>" + title + "</h2> <br> <h3>Review Date: " + date + "</h3> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
				} else {
					String critique = review[3].trim();
					html += "<h2>" + title + "</h2> <br> <h3>Review Date: " + date + "</h3> <br> <p>" + critique + "</p> <br> <br>";
					
				}
				
			}
		}

		return Response.ok(html,"text/html").build();
	}
		

	
}
