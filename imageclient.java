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
 *  1) Create an msocket client to connect to a server using a port address and local name
 *  2) Extract the bytearray from its input stream
 *  3) Convert the bytestream to an image
 *  4) Display the image on a new window
 */


import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.umass.cs.msocket.*;

public class TestMSocket1 {

	public static ByteArrayInputStream b;

	public static void main(String[] args) throws Exception {
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		int numPaths =Integer.parseInt(args[2]);
		MSocket ms = new MSocket(serverName, port, numPaths);

		OutputStream mout = ms.getOutputStream();
		InputStream min = ms.getInputStream();
                
                if(min != null){

		boolean isRead = false ; 

		while(true)
		{
			byte[] ping= new byte[0];
			byte[] buff = new byte[1024];
			int k = -1;
			int numread=0;
			int i = 0 ; 
			
			while((k = min.read(buff, 0, buff.length)) > 0 && isRead == false) {
				// Uncomment the statement below to see each iteration of the bytes being read
				//System.out.println("Reading data " + i) ;
				//i++;  
				byte[] tbuff = new byte[ping.length + k]; // temp buffer size = bytes already read + bytes last read
				System.arraycopy(ping, 0, tbuff, 0, ping.length); // copy previous bytes
				System.arraycopy(buff, 0, tbuff, ping.length, k);  // copy current lot
				ping = tbuff; // call the temp buffer as your result buff
			}

			if(isRead == false){
				System.out.println("Reading the data and converting it");
				
				// uncomment the below statement to print the recieved string
				//System.out.println("received "+new String(ping));
                                

				if (ping.length>0) {

					
					b = new ByteArrayInputStream(ping);
					BufferedImage recreatedImage = ImageIO.read(b);
					// Uncomment the statement below to print the recreated image
					//System.out.println(recreatedImage);

					// This snippet below is used to display the image in a new window
					JFrame frame = new JFrame();
					JLabel lblimage = new JLabel(new ImageIcon(recreatedImage));
					frame.getContentPane().add(lblimage, BorderLayout.CENTER);
					frame.setSize(1000, 1000);
					frame.setVisible(true);
					isRead = true ; 
					b.close(); 
					System.out.println("Number of bytes read" + ping.length);
				}  
			}          	

		}  
}    
		//FIXME: need to check close
		//    ms.close();
	}
}

