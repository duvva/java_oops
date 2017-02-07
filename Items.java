package java_course;


public class Items extends Item_quantity{
private String item_name;
private int priority;
private double item_price;
public Items() {
	super();
	item_name = null;
	priority = 0;
	item_price = 0.00;
	
}
//Setter methods
public void setPriority(int priority) {
	this.priority = priority;
}
public void setItem_name(String item_name) {
	this.item_name = item_name;
}
public void setItem_price(double item_price) {
	this.item_price = item_price;
}
//getter methods
public String getItem_name() {
	return item_name;
}
public int getPriority() {
	return priority;
}
public double getItem_price() {
	return item_price;
}
//checking same item name
public boolean hasSameItemName(Items item)    {
	return this.item_name.equalsIgnoreCase(item.item_name);  
}
//The copy method will return exact copy of an object 
public Object copy(Items item)throws CloneNotSupportedException{
 
	 Items new_item=(Items)item.clone();  
	 return new_item;
}

}
