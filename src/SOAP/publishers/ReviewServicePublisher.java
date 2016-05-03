/*
 * COMP 6302 - Web Services / Internet
 * PA3
 * Marc Badrian and Hien Vo - Due 5/4/16
 * 
 */

package SOAP.publishers;

import java.util.Scanner;
import javax.xml.ws.Endpoint;

import SOAP.endpoints.BroadwayService;
import SOAP.endpoints.NYTimesService;
import SOAP.endpoints.PlaybillService;

public class ReviewServicePublisher {

	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		// publish the Broadway service
		final String urlBroadway = "http://localhost:8888/PA3/broadway";
		System.out.println("Publishing BroadwayService at endpoint "+ urlBroadway);
		Endpoint epBroadway = Endpoint.publish(urlBroadway, new BroadwayService());
		
		// publish the NYTimes Service 
		final String urlNYTimes = "http://localhost:8888/PA3/nytimes";
		System.out.println("Publishing NYTimesService at endpoint "+ urlNYTimes);
		Endpoint epNYTimes = Endpoint.publish(urlNYTimes, new NYTimesService());
		
		// publish the Playbill service
		final String urlPlaybill = "http://localhost:8888/PA3/playbill";
		System.out.println("Publishing PlaybillService at endpoint "+ urlPlaybill);
		Endpoint epPlaybill = Endpoint.publish(urlPlaybill, new PlaybillService());
		
		System.out.println("Done publishing");
		System.out.println("Enter in \"exit\" to stop publishing:");
		String input = s.nextLine();
		boolean exit = false;
		while (exit == false) {
			if (input.equals("exit")) {
				epBroadway.stop();
				epNYTimes.stop();
				epPlaybill.stop();
				exit = true;
			} else {
				System.out.println("You did not type \"exit\". Please try again:");
				input = s.nextLine();
			};
		};
		s.close();
	}
}