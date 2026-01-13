
import java.util.*;

public class Property{
    private String propertyID;
    private String address;
    private String university;
    private String owner;
    private List<Room> rooms;

    public Property(String propertyID, String address, String university, String owner){
        this.propertyID = propertyID;
        this.address = address;
        this.university = university;
        this.owner = owner;
        this.rooms = new ArrayList<>();
    }

    //getters
    public String getPropertyId(){
        return this.propertyID;
    }
    public String getAddress(){
        return this.address;
    }
    public String getUniversity(){
        return this.university;
    }
    public String getOwner(){
        return this.owner;
    }
    public List<Room> getRooms(){
        return this.rooms;
    }
    public Room getRoom(String roomID){
        for(Room room: rooms){
            if(room.getRoomID().equals(roomID)){
                return room;
            }
        }
        return null; 
    }

    //setters
    public void setPropertyID(String newPropertyID){
        this.propertyID = newPropertyID;
    }
    public void setAddress(String newAddress){
        this.address = newAddress;
    }
    public void setUniversiry(String newUniversity){
        this.university = newUniversity;
    }
    public void setOwner(String newOwner){
        this.owner = newOwner;
    }

    public void addRoom(Room newRoom){
        this.rooms.add(newRoom);
    }
}