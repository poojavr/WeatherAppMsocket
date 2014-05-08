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


import edu.umass.cs.msocket.multicast.MSocketGroupMember;
import edu.umass.cs.msocket.multicast.MSocketGroupMemberInputStream;


public class GroupMemberApp {
	
	public static void main(String[] args) throws Exception {
		String localName = args[0];
		String Attr = args[1];
		String value = args[2];
		String lat = args[3] ; 
		String longx = args[4] ; 

		MSocketGroupMember groupMember =  new MSocketGroupMember(localName);
		
		//pr here changed to set the attributes to the group members
		System.out.println("Check"); 
		System.out.println(localName); 
		System.out.println(value); 
		System.out.println(Attr); 

		groupMember.setAttributes(Attr, value);
		groupMember.setLocation(Double.parseDouble(lat),Double.parseDouble(longx)) ; 
		
		MSocketGroupMemberInputStream min = (MSocketGroupMemberInputStream) groupMember.getInputStream();
		System.out.println("Input Stream ready") ; 
 		System.out.println("waiting");
		
		while(true) {
			
                      
			byte[] b =  min.readAny();
			if(b!=null)
			{
				System.out.println(new String(b));
			}
			else
			System.out.println("Still null ") ;  
			Thread.sleep(2000);
		}
	}
}
