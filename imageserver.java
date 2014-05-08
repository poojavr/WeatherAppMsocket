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

/*  /Pooja/ 
 *  This application is changed to 
 *  1) Extracts image data from the given image path
 *  2) Converts the images to a byte stream
 *  3) Creates an msocket server using localName and listening port
 *  4) Writes the image byte stream on the server's OutputStream
 */

import java.awt.image.BufferedImage;
import java.io.*;
import edu.umass.cs.msocket.*;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

public class TestMServerSocket1 {


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
                                        // Extracting the image into a byte stream by calling extractBytes   
					byte[] x = extractBytes("http://www.srh.noaa.gov/images/tae/121211_rainfall.png","png");
					System.out.println("Data Extracted");
                                        // Writing the data on the output stream
                                        mout.write(x);
					
                                        Thread.sleep(x.length);

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

	public static byte[] extractBytes (String ImageName,String type) throws IOException {
		

		//System.out.println("extracting bytes") ; 
		//URL takes the string which is the image path
		URL url = new URL(ImageName);
		//System.out.println(url) ; 	 
		
		//System.out.println("reading image") ; 
                // This snippet converts the image data to a byte array
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		ImageIO.write(ImageIO.read(url),type, bos); 
                byte[] data =  bos.toByteArray();
		bos.close(); 
		return data ; 
	}

	public static void main(String[] args) throws Exception {
                // Local name and port number are given by the user as arguments
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
