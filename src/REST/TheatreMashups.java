package REST;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//import javax.ws.rs.Consumes;
//import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import SOAP.client.client.*;

@Path("/")
public class TheatreMashups {
	
	BroadwayServiceService serviceBroadway = new BroadwayServiceService();
	NYTimesServiceService serviceNYTimes = new NYTimesServiceService();
	PlaybillServiceService servicePlaybill = new PlaybillServiceService();


	ReviewServiceInterface serverBroadway = (ReviewServiceInterface) serviceBroadway.getBroadwayServicePort();
	ReviewServiceInterface serverNYTimes = (ReviewServiceInterface) serviceNYTimes.getNYTimesServicePort();
	ReviewServiceInterface serverPlaybill = (ReviewServiceInterface) servicePlaybill.getPlaybillServicePort();

	
	public TheatreMashups(){}
	
	@GET
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/plays/all")
	public Response getAllPlays() {
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getAllReviews();
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getAllReviews();
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
	@Produces({ MediaType.TEXT_PLAIN })
	@Path("/plays/genres")
	public Response getAllGenres() {
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
		String playGenres = String.join("|", genres);
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
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getReviewsByName(name);
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getReviewsByName(name);
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getReviewsByName(name);
		String html = "";
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
		
		return Response.ok(html,"text/html").build();
	}
	
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/plays/{date}") 
	public Response getReviewsByDate(@PathParam("date") String reviewDate) {
		String formattedDate = reviewDate.replaceAll("_", "/");
		ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getReviewsByDate(formattedDate);
		ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getReviewsByDate(formattedDate);
		ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getReviewsByDate(formattedDate);
		String html = "";
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
		
		return Response.ok(html,"text/html").build();
	}
	
	@GET
	@Produces({ MediaType.TEXT_HTML })
	@Path("/plays/{source}")
	public Response getReviewsBySource(@PathParam("source") String source) {
		String html = "";
		switch(source) {
		case "broadway" :
			ConcurrentHashMap<Integer, String[]>  allBroadwayReviews = serverBroadway.getAllReviews();
			Set<Integer> broadwayKeys=allBroadwayReviews.keySet();
			for(Integer key :broadwayKeys){
				String[] review = allBroadwayReviews.get(key);
				String title = review[1].trim();
				String date = review[2].trim();
				if(review.length == 5) {
					String genre = review[3].trim();
					String critique = review[4].trim();
					html += "<h1>" + title + "</h1> <br> <h2>Review Date: " + date + "</h2> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
				} else {
					String critique = review[3].trim();
					html += "<h1>" + title + "</h1> <br> <h2>Review Date: " + date + "</h2> <br> <p>" + critique + "</p> <br> <br>";
					
				}
				
			}
			break;
		case "nytimes" :
			ConcurrentHashMap<Integer, String[]> allNYTimesReviews = serverNYTimes.getAllReviews();
			Set<Integer> nytimesKeys=allNYTimesReviews.keySet();
			for(Integer key :nytimesKeys){
				String[] review = allNYTimesReviews.get(key);
				String title = review[1].trim();
				String date = review[2].trim();
				if(review.length == 5) {
					String genre = review[3].trim();
					String critique = review[4].trim();
					html += "<h1>" + title + "</h1> <br> <h2>Review Date: " + date + "</h2> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
				} else {
					String critique = review[3].trim();
					html += "<h1>" + title + "</h1> <br> <h2>Review Date: " + date + "</h2> <br> <p>" + critique + "</p> <br> <br>";
					
				}
				
			}
			break;
		case "playbill" :
			ConcurrentHashMap<Integer, String[]> allPlaybillReviews = serverPlaybill.getAllReviews();
			Set<Integer> playbillKeys=allPlaybillReviews.keySet();
			for(Integer key :playbillKeys){
				String[] review = allPlaybillReviews.get(key);
				String title = review[1].trim();
				String date = review[2].trim();
				if(review.length == 5) {
					String genre = review[3].trim();
					String critique = review[4].trim();
					html += "<h1>" + title + "</h1> <br> <h2>Review Date: " + date + "</h2> <br> <h3>Genre: " + genre + "</h3> <br> <p>" + critique + "</p> <br> <br>";
				} else {
					String critique = review[3].trim();
					html += "<h1>" + title + "</h1> <br> <h2>Review Date: " + date + "</h2> <br> <p>" + critique + "</p> <br> <br>";
					
				}
				
			}
			
		}

		return Response.ok(html,"text/html").build();
	}
		

	
}
