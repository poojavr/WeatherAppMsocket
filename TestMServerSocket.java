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


import java.io.*;
import edu.umass.cs.msocket.*;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestMServerSocket {


	public static class ServerThread implements Runnable {


		MSocket msocket=null;

		ServerThread(MSocket ms) {
			msocket = ms;
		}

		public void run() {

			OutputStream mout=null;
			InputStream min=null;

			try {

				mout = msocket.getOutputStream();
				min = msocket.getInputStream();
				while(true)
				{

					// This snippet below is used to extract the weather warning data from the website
					URL url = new URL("http://forecast.weather.gov/product.php?site=NWS&issuedby=CTP&product=HWO"); 
					URLConnection con = url.openConnection();
					Document doc = Jsoup.parse(url,3*100);
					String text = doc.body().text();
					String send = text;

					//Sends the weather warning information on the output stream
					System.out.println("sending "+send);
					mout.write(send.getBytes());

					Thread.sleep(1000);
				}

				//System.out.println("Sending Complete");
				//FIXME: close may not be working now.
				//msocket.close();
			} catch(IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {



		String serverName = args[0];
		int port = Integer.parseInt(args[1]);

		MServerSocket mss = new MServerSocket(serverName, port);

		for(int i=0; true; i++) {
			System.out.println("Waiting for connections");
			MSocket ms = mss.accept();
			System.out.println("Accepted new connection " + (i+1));

			ServerThread tsc = new ServerThread(ms);
			(new Thread(tsc)).start();
		}
	}
}
