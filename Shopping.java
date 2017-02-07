package java_course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class Shopping extends Items implements Shopping_interface{
	//allow alphabets only for an item
	public static String verify_Itemname(String inputText){
		String temp =inputText.replaceAll("[^a-zA-Z]+", "");
		 return temp;
		 
	}
	
	//show the item details
	public void print(Items shopping_items[]){
	 
		System.out.println("............The Items are...");
 		System.out.format("%10s%10s%10s%10s","Item_Name ","  Item_Price ", "  Item_Priority ", "  Item_Quantity ");
 		System.out.println();
 		System.out.println("-----------------------------------------------------------------------------------------------");
     	for(int i=0;i<7;i++){
     		System.out.println();
     		System.out.format("%10s%10s%2s%10s%10s",shopping_items[i].getItem_name(),"$",shopping_items[i].getItem_price(),shopping_items[i].getPriority(),shopping_items[i].getItem_quantiy());
     		System.out.println();

     	}  
	}
	 //search for item you brought
	 public static boolean  search_incart(@SuppressWarnings("rawtypes") ArrayList item_cart,String search_value){
		 String temp_string = null;
			@SuppressWarnings("rawtypes")
			Iterator iterator=item_cart.iterator();  
     	     while(iterator.hasNext()){  
     	      temp_string =(String)iterator.next(); 
     	  			 if(temp_string.equalsIgnoreCase(search_value)){
     		         		
     		         		return true;
     	  			 }
     	  		 }
     	      return false; 
	 }
	 //search for item 
	 public static boolean  search(String item_name,Items shopping_items[]){
		 for(int j=0; j<shopping_items.length; j++){
	  			 if(shopping_items[j].getItem_name().equalsIgnoreCase(item_name)){
		         		return true;
	  			 }
	  		 }
			 
		 
		return false; 
	 }
	//compare item name

	 public boolean equals(Shopping item) {
		 return this.hasSameItemName(item);
	 }
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws ItemFoundException{
		//creating object for Derived class Shopping
		Shopping[] shopping_items= new Shopping[7];
		
		String item_Name =null;
		double item_price =0.0;
		int userChoice =0;
		int temp_priority = 0;
		int item_quantity =0;
		String person_name=null;
		String bank_name=null;
		ArrayList<Integer> item_Priority=new ArrayList<Integer>();//creating arraylist  
	 	
		
		do{
			try{
			 System.out.println("\n\n****** Shopping operations ********\n");  
			 System.out.println("[ 1 ] Exit");
			 System.out.println("[ 2 ] Add Items");
			 System.out.println("[ 3 ] Shopping");
	         System.out.println("Select Your Choices:");   
	         Scanner keyboard = new Scanner (System.in);
	         if(keyboard.hasNextLine())
	         userChoice = Integer.parseInt(keyboard.nextLine());
			
	         switch(userChoice){
	         case 1:
	        	 System.out.println("End of Shopping");
	        	 keyboard.close();
	        	 System.exit(0);
	         case 2:
	     		double total_cost = 0.0;
	        	   //Reading inputs from file
	     		Path currentRelativePath = Paths.get("input.txt");
	            String abs_path = currentRelativePath.toAbsolutePath().toString();
	            
	    		    try {
	    		         BufferedReader buffer_reader = new BufferedReader(new FileReader(abs_path));

	    		        String line = buffer_reader.readLine();
	    		        int i=0;
	    		        while (line != null) {     
	    		            String []record = line.split(",");
	    		            
	    		            shopping_items[i] = new Shopping();
			         		//taking item name
			         		item_Name=record[0];
			         		item_Name=verify_Itemname(item_Name);
			         		shopping_items[i].setItem_name(item_Name);
			         		for (int j = 0; j<i; j++) {
			         			if(shopping_items[i].equals(shopping_items[j])){
			         				throw new ItemFoundException("Item already in list please enter another Item");
			         				
			         		     }
			         		}

			         		//taking item price
			         		item_price = Double.parseDouble(record[1]);
			         		if(item_price <= 0){
			         			System.out.println("give a valid 'price'for Item"+ (i + 1));
				         		item_price=keyboard.nextDouble();

			         		}

			         		//taking item quantity
			         		item_quantity = Integer.parseInt(record[2]);
				          	
			         		
			         		total_cost = total_cost + (item_price * item_quantity) ;
			         		//checking total cost $100
			         		if(total_cost < 100 && i == 6){
			         			do{		         	
			         				System.out.println("the total cost should be more then 100 , please enter Quantity for 7 th Item");
			         				 item_quantity = Integer.parseInt(keyboard.next());
			         			}while((total_cost + (item_price * item_quantity)) < 100);

			         			
			         		}
				        	 //taking priorities
			         		 int temp = 1;
			         		if(record[3]==null){
			         			System.out.println("Please enter valid 'priority' for Item"+(i+1)+"default is 1");
			         			
			         		}else {
				          	 temp = Integer.parseInt(record[3]);
				          	 if( temp< 0 || temp > 7){
				          		 System.out.println("Please enter valid 'priority' for Item"+(i+1));
				          	 }	
			         		}
				          	 item_Priority.add(temp);
				         		temp_priority=temp;	
				         		shopping_items[i].setItem_name(item_Name);
				         		shopping_items[i].setItem_price(item_price);
				         		shopping_items[i].setItem_quantiy(item_quantity);
				         		shopping_items[i].setPriority(temp_priority);
			         		i= i+1;
	    		            line = buffer_reader.readLine();
	    		           if(line == null && i<7){
	    		        	   System.out.println("File should be 7 records");
	    		        	   break;
	    		        	   
	    		           }
	    		        }
	    		        buffer_reader.close();
	    		    }catch(Exception e){
	    		        e.printStackTrace();
	    		    }
		            //Displaying items 
		         
	    		    shopping_items[0].print(shopping_items);
	          	
	              //sorting in descending order
		         	Comparator<Integer> comparator = Collections.reverseOrder();
		         	Collections.sort(item_Priority, comparator);
	        	 break;
	         case 3:
	     		ArrayList<String> item_cart=new ArrayList<String>();//creating arraylist 
	     		ArrayList<Integer> buy_quantity=new ArrayList<Integer>();
	     		int i=0;

	          	 boolean out_of_stock = false;
	          	 String current_string =null;
	        	 if(shopping_items[6]== null){
	        		 System.out.println("No Items in List !!! please add items to list");
	        		 break;
	        	 }
	        	 
	    		    shopping_items[0].print(shopping_items);
	          	try{
	          	do{
	          	    System.out.println("enter item you want to buy, type 'Q' for Quit shopping");

	        	    current_string= keyboard.next();
	        	    if(current_string.equalsIgnoreCase("q")){
	        				break;
	        		}	
	         			while(search(current_string,shopping_items)== false){
	         				System.out.println("Item not in list please enter another Item");
	         				current_string=keyboard.next();
	         		     }
	         			
	        		
	        		   
		          		//checking for items you brought 
	        		  boolean flag = true;
	        		  while(flag){
		          		
		          	  			 if(search_incart(item_cart, current_string)){
		          		         		System.out.println("Item you already brought enter another item");
		          		         		current_string=keyboard.next();
		          	  			 }else{
		          	  				 flag=false;
		          	  			 }
		          	  		 }
		          	       
	        		   
	         			item_Name = current_string;

				        	 //taking buying quantity
			         	 	 System.out.println("Enter 'Quantity' for an item ");
				          	 int temp = Integer.parseInt(keyboard.next());
				          	 if( temp< 0){
				          		 System.out.println("Please enter valid 'Quantity'");
				          		 temp = Integer.parseInt(keyboard.next());
				          	 }	
				          	//checking item quantity and updating quantity 
				          	     String cart_item =null;
				          	     int temp_quantity = 0;
				          	     
				          	  		 for(int j=0; j<shopping_items.length; j++){
				          	  			 if(shopping_items[j].getItem_name().equalsIgnoreCase(item_Name)){
				          	  				 if(shopping_items[j].getItem_quantiy() <= 0){
				          	  				    System.out.println("out_of_stock");
				          	  				    out_of_stock =true;
				          		         			
				          		         	  }else{
				          		         		temp_quantity = shopping_items[j].getItem_quantiy() - temp ;
				          		         		shopping_items[j].setItem_quantiy(temp_quantity);	
				          		         	  }
				          	  			 }
				          	  		 }
				          	     
	                      i++;
	                      if(out_of_stock == false){
	                    	  item_cart.add(item_Name);
	                    	  buy_quantity.add(temp);
	                      }
	          	}while(i<7);	 
	          	
	          	 }catch(Exception e){
	    		        e.printStackTrace();
	    		    }
	          	
	          	//creating output file
	          	try{
	          		System.out.println("enter person name");
	        	    person_name= keyboard.next();
	        	   
	        	    System.out.println("enter bank name");
	        	    bank_name= keyboard.next();
	        	    
	        	    FileWriter filewriter=new FileWriter("receipt.txt");  
	        	    filewriter.write("Person Name is :" +person_name);
	        	    filewriter.write(System.getProperty( "line.separator" ));
	        	    filewriter.write("Bank name is: " + bank_name);
	        	    filewriter.write(System.getProperty( "line.separator" ));
	        	    filewriter.write(".................The Items you Brought .................");
	        	    filewriter.write(System.getProperty( "line.separator" ));
	        	    filewriter.write("Item_Name   Item_Price  Item_Quantity  Total Amount");
	        	    filewriter.write(System.getProperty( "line.separator" ));
	        	    filewriter.write("----------------------------------------------------------");
	        	    filewriter.write(System.getProperty( "line.separator" ));

	          	
	         	//retrieving purchase items
	          	System.out.println(".................The Items you Brought .................");
	        	 System.out.format("%10s%10s%10s%10s","Item_Name ","   Item_Price","  Item_Quantity  ","Total Amount");
	        	 System.out.println();
	        	 System.out.println("----------------------------------------------------");
	            System.out.println();
	            // iterators for total amount
	            Iterator iterator_buy=buy_quantity.iterator();  
	            Iterator iterator_cart=item_cart.iterator();  
	            String cart_item =null;
	            int cart_item_quantity = 0;
	            double grand_total =0.0;
         	     while(iterator_cart.hasNext()){  
         	      cart_item =(String)iterator_cart.next(); 
         	     cart_item_quantity = (int) iterator_buy.next();
         	  		 for(int j=0; j<shopping_items.length; j++){
         	  			 if(shopping_items[j].getItem_name().equalsIgnoreCase(cart_item)){
         		         	
         	  				System.out.println(); 
    		         		System.out.format("%10s%10s%2s%10s%10s%2s",shopping_items[j].getItem_name(),"$",shopping_items[j].getItem_price(),cart_item_quantity,"$",(shopping_items[j].getItem_price()*cart_item_quantity));
	                		System.out.println();
	    	        	    filewriter.write(System.getProperty( "line.separator" ));
	                		 filewriter.write(shopping_items[j].getItem_name()+"		" + "$"+shopping_items[j].getItem_price()+"		"+cart_item_quantity+"		" + "$"+shopping_items[j].getItem_price()*cart_item_quantity);
	                		 grand_total= grand_total + (shopping_items[j].getItem_price()* cart_item_quantity) ;
	                		 }
         	  		 }
		         }
         	     // printing grand total
         	     System.out.println("Grand Total : " + grand_total);
       		     filewriter.write(System.getProperty( "line.separator" ));
       		     filewriter.write("---------------Grand Total-------------------" + grand_total);
  			 
         	    
	         	 System.out.println(".................The Items you did not Buy .................");
	        	 System.out.format("%10s%10s%10s","Item_Name ","   Item_Price","  Item_Priority","Item_Quantity");
	        	 System.out.println();
	        	 System.out.println("-----------------------------------------------------------------------------------");
	            System.out.println();
	         	     for(int j=0; j<shopping_items.length; j++){
	         	    	 System.out.println();
	         			 if(!(search_incart(item_cart,shopping_items[j].getItem_name()))){
		                	System.out.println();
    		         		System.out.format("%10s%10s%2s%10s%10s",shopping_items[j].getItem_name(),"$",shopping_items[j].getItem_price(),shopping_items[j].getPriority(),shopping_items[j].getItem_quantiy());
	                		System.out.println();
	                		
	         		 }
	         	 }
	      
	            
	            //list after shopping

	         	 System.out.println(".................The Items after shopping .................");
	    		  shopping_items[0].print(shopping_items);
        
	            filewriter.close();
	          	}catch(Exception e){
    		        e.printStackTrace();
    		    }
	         	
	        	 
	           break;	 
	         default:
	        		 System.out.println("enter Integers 1 or 2 or 3 only");
	         }
	         
			}catch(Exception ex){
				System.out.println("enter numerics only !!");
			}
		}while(true);
		
	}
	
}
