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

public class TestMSocket {
	
       public static void main(String[] args) throws Exception {
    	   String serverName = args[0];
    	   int port = Integer.parseInt(args[1]);
    	   int numPaths =Integer.parseInt(args[2]);
    	   MSocket ms = new MSocket(serverName, port, numPaths);

    	   OutputStream mout = ms.getOutputStream();
    	   InputStream min = ms.getInputStream();
    	   
    	   while(true)
           {
          	   byte[] ping= new byte[100000];
          	   int numread=0;
		// reading the data from the input stream
          	   while(numread < 4)	
          	   {
          		   numread+=min.read(ping);
          	   }
          	  
          	   System.out.println("received "+new String(ping));
          	 
           }      
           //FIXME: need to check close
           //    ms.close();
   }
}
