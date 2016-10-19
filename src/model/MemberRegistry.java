/* By:
 * Sebastian Airisniemi
 * sa223gd 
 */

package model;

import java.util.*;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MemberRegistry {
	//List of members.
	private List<Member> memberList = new ArrayList<Member>();
	
	//The unique ID of the members.
	private int id = 0;
	
	//Registry file where member data is read and stored.
	File file = new File("/Users/Sebastian/Documents/test.txt");
	
	
	//Constructor which reads the member registry saved in the registry file.
	public MemberRegistry() { readFromFile(file); }
	
	//Returns the list of the members
	public List<Member> getMemberList() {	return memberList;	}
	
	//Method to generate each members unique ID.
	private int generateID() { return id++; }
	
	public int whatIsID() { return id; }
	
	
	public void addMember(String memberName, int memberPersNr) {
		//Set the int id so that it is a unique one and ready to be assigned to member.
		generateID();
		Member currMember = new Member(memberName, memberPersNr, id);
		
		//Add member to list.
		memberList.add(currMember);
		//Update file.
		writeToFile(file);
	}
	
	public void removeMember(int id) {
		//Find user with the specified id and remove it from the list.
		for (Member member : memberList) {
			if (member.getID() == id) {
				memberList.remove(member);
				break;
			}
		}
		
		//Update file.
		writeToFile(file);
	}

	public void updateMember(String name, int persNr, int id) {
		for (Member member : memberList) {
			if (member.getID() == id) {
				member.setName(name);
				member.setPersonalNumber(persNr);
				break;
			}
		}
		//Update file.
		writeToFile(file);
	}

	public void registerBoat(int boatType, int length, int id) {
		//Create new boat and set the properties of the boat and then send it to addBoatToMember
		//so it can be added to a members boat list.
		Boat boat = new Boat();
		switch (boatType) {
		case(1):
			boat.setBoatType(Boat.TypeOfBoat.Sailboat);
			boat.setLength(length);
			boat.setBoatInt(1);
			addBoatToMember(boat, id);
			break;
			
		case(2):
			boat.setBoatType(Boat.TypeOfBoat.Motorsailer);
			boat.setLength(length);
			boat.setBoatInt(2);
			addBoatToMember(boat, id);
			break;

		case(3):
			boat.setBoatType(Boat.TypeOfBoat.KayakOrCanoe);
			boat.setLength(length);
			boat.setBoatInt(3);
			addBoatToMember(boat, id);
			break;
			
		case(4):
			boat.setBoatType(Boat.TypeOfBoat.Other);
			boat.setLength(length);
			boat.setBoatInt(4);
			addBoatToMember(boat, id);
			break;
		}
	}

	private void addBoatToMember(Boat boat, int id) {
		for (Member member : memberList) {
			if (member.getID() == id) {
				member.setBoat(boat);
				break;
			}
		}
		//Update file.
		writeToFile(file);
	}
	
	public void removeBoat(int id, int boatID) {
		for (Member member : memberList) {
			if (member.getID() == id) {
				for (Boat boat : member.getBoats()) {
					if (boat.getBoatID() == boatID) {
						member.removeBoat(boat);
						break;
					}
				}
				
			}
		}
		//Update file.
		writeToFile(file);
	}

	public void changeBoat(int id, int boatID, String boatType, int length) {
		for (Member member : memberList) {
			if (member.getID() == id) {
				for (Boat boat : member.getBoats()) {
					if (boat.getBoatID() == boatID) {
						boat.setBoatType(Boat.TypeOfBoat.valueOf(boatType));
						boat.setLength(length);
						break;
					}
				}
				
			}
		}
		//Update file.
		writeToFile(file);
	}
	public void readFromFile(File file) {
		try (Scanner in = new Scanner(file)) {
			//while there are more to read from file
			while (in.hasNext()) {
				String line = in.nextLine();
				
				//If we are reading members
				if (line.equals("Member info:")) {
					//Read from file
					line = in.nextLine();
					
					//Split the input string to get each specific info of member.
					String[] inputMember = line.split(";");
					String memberName = inputMember[0];
					int personalNumber = Integer.parseInt(inputMember[1]);
					int memberID = Integer.parseInt(inputMember[2]);
					
					//Create new member with the name, personal number and ID.
					Member tempMember = new Member(memberName, personalNumber, memberID);
					memberList.add(tempMember);
					
					//increment id for each new member added from registry file.
					id++;
				}
				
				//If we are reading boats
				else if (line.equals("Boats:")) {
					if (in.hasNext()) {
						//Read from file
						line = in.nextLine();
						
						//Split the input string to get each specific info of member.
						String[] inputBoat = line.split(";");
						int boatType = Integer.parseInt(inputBoat[0]);
						int length = Integer.parseInt(inputBoat[1]);
						
						//Register boat to member.
						registerBoat(boatType, length, id);
					}				
				}	
			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
	}
	
	private void writeToFile(File file) {
		//Empty the file to make sure there are no duplicates..
		try (PrintWriter emptyWriter = new PrintWriter(file)) { emptyWriter.close(); }
		catch (FileNotFoundException e) { e.printStackTrace(); }
		
		//Write registry to file
		try (PrintWriter pw = new PrintWriter(file)) {
			
			for (Member m : memberList) {
				//Write member info
				pw.println("Member info:");
				pw.println(m.getName() + ";" + m.getPersonalNumber() + ";" + m.getID());
				
				if (!(m.getBoats().isEmpty())) {
					//Write boat info
					pw.println("Boats:");
					for (Boat b : m.getBoats()) 
						pw.println(b.getBoatInt() + ";" + b.getLength());
				}
			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); } 
	}
}
