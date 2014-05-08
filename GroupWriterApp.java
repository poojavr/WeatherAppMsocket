/**
 * Mobility First - Global Name Resolution Service (GNS)
 * Copyright (C) 2013 University of Massachusetts - Emmanuel Cecchet.
 * Contact: cecchet@cs.umass.edu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 *
 * Initial developer(s): Emmanuel Cecchet.
 * Contributor(s): ______________________.
 */


import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import edu.umass.cs.msocket.multicast.MSocketGroupWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;




public class GroupWriterApp {
	
	public static void main(String[] args) throws Exception {
		String writerName = args[0];
		String lat = args[1] ; 
		String longx = args[2] ; 
		String radius = args[3]; 
		String groupName = args[4];

		System.out.println("inside writer"); 
		System.out.println(writerName); 
		System.out.println(groupName);

		
		URL url = new URL("http://forecast.weather.gov/product.php?site=NWS&issuedby=CTP&product=HWO"); 
		URLConnection con = url.openConnection();
		Document doc = Jsoup.parse(url,3*100);
		String text = doc.body().text();

		String send = text; 
		
		//System.out.println(send);

		MSocketGroupWriter groupWriter =  new MSocketGroupWriter(writerName);
		groupWriter.setLocation(Double.parseDouble(lat),Double.parseDouble(longx));
		groupWriter.setRadius(Double.parseDouble(radius)); 
		System.out.println("Created the group writer"); 
		groupWriter.connect(groupName);
		System.out.println("connected to the group"); 

		
		OutputStream gout = groupWriter.getOutputStream();
		
		while(true) {
			String hello = "hello group members of"+groupName+"The weather warning information for your current location";
			//gout.write(send.getBytes());
			gout.write(hello.getBytes());
			System.out.println(hello+" written");
			Thread.sleep(2000);
		}
	}
}
