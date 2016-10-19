/* By:
 * Sebastian Airisniemi
 * sa223gd 
 */

package view;

import java.io.*;
import java.util.*;

import model.*;

public class OutputToUser {
	private MemberRegistry currRegistry = new MemberRegistry();;
	
	//Gets user input int.
	private int getUserInputNumber() { //throws IllegalArgumentException {
		int userInput;
		try {
			Scanner inputInt = new Scanner(System.in);
			userInput = inputInt.nextInt();
				
			while (userInput == '\r' || userInput =='\n')
				userInput = inputInt.nextInt();
				
			return userInput;
		}
		//If invalid input.
		catch (Exception e) {
			System.out.print("Invalid input. Please try again: ");
			return getUserInputNumber();
		}
	}
	
	//Gets user input String.
	private String getUserInputText() {
		Scanner inputText = new Scanner(System.in);
		String userInput = inputText.nextLine();
		
		return userInput;
	}
	
	//Displays the menu.
	public void showMenu() {
		System.out.println("\n\n\nHello and welcome to BoatRegistry 2000!\n");
		System.out.println("What would you like to do:\n");
		System.out.println("1. Add user.");
		System.out.println("2. Remove user.");
		System.out.println("3. Search user.");
		System.out.println("4. Update user.");
		System.out.println("5. Register boat to user.");
		System.out.println("6. Change boat info.");
		System.out.println("7. Remove boat info.");
		System.out.println("8. View compact list.");
		System.out.println("9. View verbose list.");
		System.out.print("Make your choice by entering the number of the task you would like to do: ");
		
		//Switch to handle user choice in menu.
		while (true) {
			switch (getUserInputNumber()) {
				case 1: 
					System.out.println("       ---Add member---     ");
					showAddMember();
					break;
				case 2:
					System.out.println("       ---Remove member---     ");
					showRemoveMember();
					break;
				case 3: 
					System.out.println("       ---Search for member---     ");
					showSearchMember();
					break;
				case 4: 
					System.out.println("       ---Update member---     ");
					showUpdateMember();
					break;
				case 5:
					System.out.println("       ---Register boat---     ");
					showRegisterBoat();
					break;
				case 6: 
					System.out.println("       ---Change boat---     ");
					showChangeBoat();
					break;
				case 7: 
					System.out.println("       ---Remove boat---     ");
					showRemoveBoat();
					break;
				case 8: 
					System.out.println("     ---Compact list---     ");
					showCompactList();
					break;
				case 9: 
					System.out.println("     ---Verbose list---     ");
					showVerboseList();
					break;
				default: //User has made an invalid input.
					System.out.println("You must type an integer between 1 and 9!");
					break;
			}
		}
	}
	
	//Displays compact list by iterating through member list and printing info.
	private void showCompactList() {	
		for(Member member : currRegistry.getMemberList())
			System.out.println("Name: " + member.getName() + "\nMember ID: " + member.getID() + "\nNumber of boats: " + member.getBoats().size());
		showMenu();
	}
	
	//Displays verbose list by for each member in member list calling searchForMember.
	private void showVerboseList() {
		for(Member member : currRegistry.getMemberList()) 
			searchForMember(member.getID());
		showMenu();
	}
	
	private void showAddMember() {
		//Input for name.
		System.out.print("Name: ");
		String name = getUserInputText();
		
		//input for personal number.
		System.out.print("Personal Number (format yymmdd): ");
		int persNr = getUserInputNumber();
		
		//Add member with information provided
		currRegistry.addMember(name, persNr);
		showMenu();
	}
	
	private void showRemoveMember() {
		//Input for ID.
		System.out.print("Please enter the members ID (integers only): ");
		int id = getUserInputNumber();
		
		//Check for valid ID.
		validateID(id);
		
		//Remove member with ID.
		currRegistry.removeMember(id);
		showMenu();
	}
	
	private void showUpdateMember() {
		//Input for ID.
		System.out.print("Please enter the members ID (integers only): ");
		int id = getUserInputNumber();
		
		//Check for valid ID.
		validateID(id);
		
		//Input for new name.
		System.out.print("Please enter a new name: ");
		String name = getUserInputText();
		
		//Input for new personal number.
		System.out.print("Please enter a new personal number (format yymmdd): ");
		int persNr = getUserInputNumber();
		
		//Update member with new info.
		currRegistry.updateMember(name, persNr, id);
		
		showMenu();
	}
	
	//Check to make sure ID is within current range.
	private void validateID(int id) {
		while (id < 0 || id > currRegistry.whatIsID()) {
			System.out.print("Invalid input. Please try again: ");
			id = getUserInputNumber();
		}
	}
	
	//Shows verbose list of member with specified ID.
	private void showSearchMember() {
		//input for ID.
		System.out.print("Please enter the members ID (integers only): ");
		int id = getUserInputNumber();
		
		//Check for correct ID.
		validateID(id);
		
		//Show verbose list for member.
		searchForMember(id);
		
		showMenu();
	}
	
	//Finds the member with a specific ID and prints the vorbose list for that member.
	private void searchForMember(int id) {
		//for each member in registry.
		for(Member member : currRegistry.getMemberList()) {
			//If ID is found.
			if (member.getID() == id) {
				System.out.println("Name: " + member.getName() + "\nPersonal Number: " + member.getPersonalNumber() + "\nMember ID: " + member.getID() + "\n");
				
				int i = 0;
				//Print boat info
				for(Boat boat : member.getBoats()) {
					System.out.println(i + "Boat Type: " + boat.typeOfBoatString() + "\nLength: " + boat.getLength() + " cm\n");
					i++;
				}
				//Separator for each member info
				System.out.println("---------------");
			}
		}
	}
	
	//Register a boat to a member.
	private void showRegisterBoat() {
		//Present available boat types and instructions.
		System.out.print("Enter a boat type. 1. for Sailboat, 2. for Motorsailer, 3. for Kayak or Canoe or 4. for Other. ");
		
		//Get chosen boat.
		int userInput = getUserInputNumber();
		
		//Check for valid input.
		while (userInput < 1 && userInput > 5) {
			System.out.print("Invalid input. Please try again: ");
			userInput = getUserInputNumber();
		}
		
		//input for length of boat.
		System.out.print("Length of boat (in cm): ");
		int length = getUserInputNumber();
		
		//Input for member who owns the boat.
		System.out.print("Enter the member ID to which the boat is to be registered (integers only): ");
		int id = getUserInputNumber();
		
		validateID(id);
		
		//register boat to member.
		currRegistry.registerBoat(userInput, length, id);
		
		showMenu();
	}
	
	private void showRemoveBoat() {
		//Input for member id.
		System.out.print("Please enter the members ID (integers only): ");
		int id = getUserInputNumber();
		
		validateID(id);
		
		//Present verbose list of member info.
		searchForMember(id);
		
		//Input for boat to remove.
		System.out.print("Which boat would you like to remove (enter the number representing the boat): ");
		int boatID = getUserInputNumber();
		
		//remove boat.
		currRegistry.removeBoat(id, boatID);
		
		showMenu();
	}
	
	private void showChangeBoat() {
		//Searches for the user which has the boat we want to change.
		System.out.print("Please enter the members ID (integers only): ");
		int id = getUserInputNumber();
		
		validateID(id);
		
		searchForMember(id);
		
		System.out.print("Which boat would you like to change (enter the number representing the boat): ");
		int boatID = getUserInputNumber();
		
		System.out.print("Enter a new boat type: ");
		String boat = getUserInputText();
		
		System.out.print("New length of boat (in cm): ");
		int length = getUserInputNumber();
		
		currRegistry.changeBoat(id, boatID, boat, length);
		
		showMenu();
	}
}
