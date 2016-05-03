/*
 * COMP 6302 - Web Services / Internet
 * PA3
 * Marc Badrian and Hien Vo - Due 5/4/16
 * 
 */

package SOAP.endpoints;

import javax.jws.WebMethod;
import javax.jws.WebService;

//import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@WebService(endpointInterface = "SOAP.endpoints.ReviewServiceInterface")
public class BroadwayService implements ReviewServiceInterface{


	@WebMethod
	public ConcurrentHashMap<Integer, String[]> readFile() {
	    ConcurrentHashMap<Integer, String[]> broadwayReviews = new ConcurrentHashMap<Integer, String[]>();
		Integer recordNum = 0;
        String line;
        try {
    		BufferedReader br = new BufferedReader(new FileReader("../../Broadway.com.txt"));
			while ((line = br.readLine()) != null) {
				recordNum = recordNum++;
				if(line != null && !line.isEmpty()) {
			    	String[] line_split = line.split("\\|");
			    	broadwayReviews.put(recordNum, line_split);
				}
				br.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return broadwayReviews;
	}
	
	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getReviewsByDate(String date) {
		ConcurrentHashMap<Integer, String[]> reviewsByDate = this.readFile();
		Set<Integer> keySet=reviewsByDate.keySet();
		for(Integer key :keySet){
			String[] review = reviewsByDate.get(key);
			String reviewDate = review[2].trim();
			if(!reviewDate.equals(date)) {
				reviewsByDate.remove(key);
			}
		}  

		return reviewsByDate;
	}

	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getReviewsByGenre(String genre) {
		ConcurrentHashMap<Integer, String[]> reviewsByGenre = this.readFile();
		Set<Integer> keySet=reviewsByGenre.keySet();
		for(Integer key :keySet){
			String[] review = reviewsByGenre.get(key);
			String reviewGenre = "";
			if(review.length == 5) {
				reviewGenre = review[3].trim();
			}
			if(!reviewGenre.equals(genre)) {
				reviewsByGenre.remove(key);
			}
		}  
		return reviewsByGenre;
	}
	
	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getReviewsByName(String name) {
		ConcurrentHashMap<Integer, String[]> reviewsByName = this.readFile();
		Set<Integer> keySet=reviewsByName.keySet();
		for(Integer key :keySet){
			String[] review = reviewsByName.get(key);
			String reviewName = review[0].trim();
			if(!reviewName.equals(name)) {
				reviewsByName.remove(key);
			}
		}  
		return reviewsByName;
	}
	
	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getAllReviews() {
		ConcurrentHashMap<Integer, String[]> allReviews = this.readFile();
		return allReviews;
	}
	
}
