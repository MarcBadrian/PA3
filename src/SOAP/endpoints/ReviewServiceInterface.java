/*
 * COMP 6302 - Web Services / Internet
 * PA3
 * Marc Badrian and Hien Vo - Due 5/4/16
 * 
 */

package SOAP.endpoints;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface ReviewServiceInterface {

	@WebMethod
	public ConcurrentHashMap<Integer, String[]> readFile();
	
	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getReviewsByDate(String date);

	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getReviewsByGenre(String genre);
	
	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getReviewsByName(String name);
	
	@WebMethod
	public ConcurrentHashMap<Integer, String[]> getAllReviews();
	
	
}
