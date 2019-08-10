/*
*Endless Sky Wikia Standardized Article Writer Version 1.0
*Made by 1NF1N173#9254 on Discord
*Infiknight999 on Wikia Fandom
*Infinight999 on Github
*Message me on discord if you have questions about the code
*
*Date created: 8 August 2019
*Last update: 8 August 2019
*Github page: https://github.com/Infinight999/EndlessSkyWikiaArticleWriter
*/
package endlessSkyWikiaArticleHelper;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class ArticleWriter{
	public static void main(String[]args) {
		//input string is directly from game files, just copy and paste from outfit to the last quote of the description and run this, currently has sample inside
		String input ="outfit \"Beam Laser\"\r\n" + 
				"	category \"Guns\"\r\n" + 
				"	cost 29000\r\n" + 
				"	thumbnail \"outfit/laser\"\r\n" + 
				"	\"mass\" 8\r\n" + 
				"	\"outfit space\" -8\r\n" + 
				"	\"weapon capacity\" -8\r\n" + 
				"	\"gun ports\" -1\r\n" + 
				"	weapon\r\n" + 
				"		sprite \"projectile/laser\"\r\n" + 
				"			\"frame rate\" 1\r\n" + 
				"		sound \"laser\"\r\n" + 
				"		\"hit effect\" \"beam laser impact\"\r\n" + 
				"		\"inaccuracy\" .5\r\n" + 
				"		\"velocity\" 300\r\n" + 
				"		\"lifetime\" 1\r\n" + 
				"		\"reload\" 1\r\n" + 
				"		\"firing energy\" .5\r\n" + 
				"		\"firing heat\" 1.2\r\n" + 
				"		\"shield damage\" 1\r\n" + 
				"		\"hull damage\" 1.3\r\n" + 
				"	description \"In the early part of the space era, the settlements in the region of space known as the Deep developed in relative isolation from the rest of human space. One result of that isolation is that their weapons technology mostly uses beam weapons, instead of the energy projectile weapons that are more common elsewhere.\"";
		String type = input.substring(0,input.indexOf(" ")); //3 types i'll use, System, Ship, Outfit		
		//variable to output
		String output = "";
		
		//to format numbers
		DecimalFormat format = new DecimalFormat("#.###");
		
		//start outfits
		if(type.equals("outfit")){
			//constructor variables using regex
			String namePattern = "(" + type + " \")([\\w\\s]*)([\\S\\s]*)";
			String name = input.replaceAll(namePattern, "$2");
			
			String descriptionPattern = "([\\S\\s]*?description \")([^\"]*)([\\S\\s]*)";
			String description = input.replaceAll(descriptionPattern, "$2");
			
			//I N F O B O X
			String infobox = "{{Generalized Infobox|title1="+name;
			
			//get picture file name
			String thumbnailPattern = "([\\S\\s]*?thumbnail \"outfit/)([^\"]*)([\\S\\s]*)";
			String thumbnail = input.replaceAll(thumbnailPattern, "$2");
			
			//add picture file name to infobox
			infobox += "|image1="+thumbnail+".png";
			
			//remove description, thumbnail and everything before category
			String remaining = input.replaceAll("([\\S\\s]*?)(\tcategory[\\S\\s]*?)(\tthumbnail \"outfit/[\\w\\s]*\"\r\n)([\\S\\s]*?)(\tdescription)([\\S\\s]*)", "$2$4");
			
			//get category name
			String categoryPattern = "([\\S\\s]*?category \")([^\"]*)([\\S\\s]*)";
			String category = remaining.replaceAll(categoryPattern, "$2");
			
			//add category name to infobox
			infobox += "|category="+category;
			
			//remove category (assume category is at top), replaces all spaces inside of quotes with underscores, removes all quotes
			remaining = remaining.replaceAll("([\\S\\s]*?\r\n)([\\S\\s]*)", "$2");
			remaining = remaining.replaceAll("\"(\\w*)\\s(\\w*)\"", "$1_$2");
			remaining = remaining.replaceAll("\"", "");
			
			//loops through remainder and adds all other attributes to infobox starting from top
			double rangeCalc = 0;
			double perSecCalc = 0;
			while(remaining.length() > 6){
				String temp = remaining.replaceAll("\\s*(\\w*)[\\S\\s]*", "$1");
				try {	
					double number = Double.valueOf(remaining.substring(remaining.indexOf(" ")+1,remaining.indexOf("\r")));
					//solve for per second calcs when finding damage
					if(temp.equals("shield_damage")||temp.equals("hull_damage")) {
						infobox +="|"+temp+"_per_sec="+format.format(number*perSecCalc)+" damage per second";
						if(name.contains("Laser")||name.contains("Beam")||name.contains("Electron"))
							remaining = remaining.replaceAll("([\\S\\s]*?\r\n)([\\S\\s]*)", "$2");
						else {
							infobox +="|"+temp+"_per_shot="+format.format(number)+" damage per shot";
							remaining = remaining.replaceAll("([\\S\\s]*?\r\n)([\\S\\s]*)", "$2");
						}
					}
					//solves for per second calcs when finding energy, heat, and fuel
					else if(temp.equals("firing_energy")||temp.equals("firing_heat")||temp.equals("firing_fuel")) {
						infobox +="|"+temp+"_per_sec="+format.format(number*perSecCalc)+" per second";
						remaining = remaining.replaceAll("([\\S\\s]*?\r\n)([\\S\\s]*)", "$2");
					}
					else {
						//multiplies numbers by needed amounts (found in source code)
						if(temp.equals("active_cooling")||temp.equals("afterburner_energy")||temp.equals("afterburner_fuel")||temp.equals("afterburner_heat")||temp.equals("cloak")||temp.equals("cloaking_energy")||temp.equals("cloaking_fuel")||temp.equals("cloaking_heat")||temp.equals("cooling")||temp.equals("cooling_energy")||temp.equals("energy_consumption")||temp.equals("energy_generation")||temp.equals("fuel_consumption")||temp.equals("fuel_energy")||temp.equals("fuel_generation")||temp.equals("fuel_heat")||temp.equals("heat_generation")||temp.equals("heat_dissipation")||temp.equals("hull_repair")||temp.equals("hull_energy")||temp.equals("hull_fuel")||temp.equals("hull_heat")||temp.equals("jump_speed")||temp.equals("reverse_thrusting")||temp.equals("reverse_thrusting")||temp.equals("shield_generation")||temp.equals("shield_energy")||temp.equals("shield_fuel")||temp.equals("shield_heat")||temp.equals("solar_collection")||temp.equals("thrusting_energy")||temp.equals("thrusting_heat")||temp.equals("turn")||temp.equals("turning_energy")||temp.equals("turning_heat"))
							number = 60*number;
						if(temp.equals("thrust")||temp.equals("reverse_thrust")||temp.equals("afterburner_thrust"))
							number = 60*60*number;
						if(temp.equals("ion_resistance")||temp.equals("disruption_resistance")||temp.equals("slowing_resistance"))
							number = 60*100*number;
						
						//calculates shots per second, ALWAYS comes before energy, heat and damage in game files
						if(temp.equals("reload")) {
							perSecCalc = 60.0/number;
							if(name.contains("Laser")||name.contains("Beam")||name.contains("Electron")) {}
							else
								infobox += "|shots_per_sec="+format.format(perSecCalc)+" shots per second";
						}
						
						//solve for range calc, or statements hear just in case lifetime comes before velocity (unlikely)
						if((temp.equals("velocity")||temp.equals("lifetime"))&&rangeCalc!=0)
							infobox += "|range="+format.format(number*rangeCalc);
						if((temp.equals("velocity")||temp.equals("lifetime"))&&rangeCalc==0)
							rangeCalc = number;
						
						infobox += "|"+temp+"="+format.format(number);
						remaining = remaining.replaceAll("([\\S\\s]*?\r\n)([\\S\\s]*)", "$2");
					}
				}
				//catches a line that doesn't work and erases it's existence
				catch(NumberFormatException ex) {
					remaining = remaining.replaceAll("([\\S\\s]*?\r\n)([\\S\\s]*)", "$2");
					//System.out.println("caught number format exception");
				}
				catch(StringIndexOutOfBoundsException ex) {
					remaining = remaining.replaceAll("([\\S\\s]*?\r\n)([\\S\\s]*)", "$2");
					//System.out.println("caught index out of bounds exception");
				}
			}
			
			//determines which outfitters sell this outfit
			String[][] outfitters = OutfittersArray.outfitters;
			ArrayList<String> sellers = new ArrayList<String>();
			for(int i = 0; i < outfitters.length; i++)
				for(int j = 0; j < outfitters[i].length; j++)
					if(outfitters[i][j].equals(name))
						sellers.add(outfitters[i][0]);
			
			//add everything to output
			output+="<blockquote>\""+description+"\"</blockquote>\n\n<blockquote>-Outfitter Description</blockquote>\n\n";
			output+=infobox+"}}\n\n";
			if(category.equals("Guns")||category.equals("Turrets")||category.equals("Secondary Weapons"))
				output+="==Weapon Details==\nWe’re still waiting on player feedback on the use of this weapon.";
			output+="\n\n==Outfitters==\nThe "+name+" can be purchased at the following ports:\n\n";
			for(String s:sellers)
				output+="[[:Category:"+s+"|"+s+"]]\n\n";
			
			//add categories to output
			if(!category.equals("Guns"))
				output+="[[Category:"+category+"]]\n";
			if(category.equals("Guns")||category.equals("Turrets"))
				output+="[[Category:Energy Weapons]]\n";
			if(category.equals("Guns")||category.equals("Turrets")||category.equals("Secondary Weapons"))
				output+="[[Category:Weapons]]\n";
			output+="[[Category:Outfits]]\n";
			for(String s:sellers)
				output+="[[Category:"+s+"]]\n";
		}//end outfits
		
		System.out.println(output);
	}
}