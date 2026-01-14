import java.time.LocalDate;
import java.util.*;

public class PropertyRepository{
    private HashMap<String, Property> propertiesID = new HashMap<>();
    private int propertyIndex;
    private int roomIndex;

    public PropertyRepository(){
        this.propertyIndex =0;
        this.roomIndex =0;
    }
    //setters
    public void setPropertyIndex(int index){
        propertyIndex = index;
    }
    public void setRoomIndex(int index){
        roomIndex = index;
    }

    //this method attaches a room to a property's room list
    public void addRoomToProperty(String propertyID, String description, Double price, List<BookingSlot> bookings){
        Property property = getPropertyByID(propertyID); // gets the property to add the room to

        Room newRoom = new Room(generateRoomID(), description, price); // creates room to add
        if(!bookings.isEmpty()){ // if there are booking slots, add them to the room
            for(BookingSlot booking: bookings){
                LocalDate start = booking.getStartDate();
                LocalDate end = booking.getEndDate();
                newRoom.addBooking(start, end);
            }
        }

        property.addRoom(newRoom);
    }

    //generates new property id by using the repository's index
    public String generatePropertyID(){
        String paddedNum = String.format("%03d", propertyIndex); // creates 3 digit number, padded with zeros if not already 3 digits
        propertyIndex ++;
        return "P".concat(paddedNum);
    }

    //generates new room id by using the repositorys index
    public String generateRoomID(){
        String paddedNum = String.format("%03d", roomIndex); // creates 3 digit number, padded with zeros if not already 3 didgits
        roomIndex++;
        return "Ro".concat(paddedNum);
    }
    //adds property to repository
    public void addProperty(Property newProperty){
        propertiesID.put(newProperty.getPropertyId(), newProperty);
    }
    //returns a property object from a given property id
    public Property getPropertyByID(String propertyID){
        return propertiesID.get(propertyID);
    }
    //gets every property in the repository
    public List<Property> getAllProperties(){
        List<Property> allProperties = new ArrayList<>();

        for(Property property: propertiesID.values()){
            allProperties.add(property);
        }
        return allProperties;
    }
    //function returns true if the any properties are in that university
    public Boolean isUniIn(String university){
        for(Property property: propertiesID.values()){
            if(property.getUniversity().equalsIgnoreCase(university)){
                return true;
            }
        }
        return false;
    }
    //checks if room id is attached to any property, returns false if not
    public Boolean isRoomIn(String RoomID){
        Boolean isIn = false;
        for(Property property: propertiesID.values()){
            for(Room room: property.getRooms()){
                if(room.getRoomID().equalsIgnoreCase(RoomID)){
                    isIn = true;
                    break;
                }
            }
        }
        return isIn;
    }
    // returns the property that the roomid is in
    public Property getPropertyByRoom(String roomID){
        for(Property property: propertiesID.values()){
            for(Room room: property.getRooms()){
                if(room.getRoomID().equals(roomID)){
                    return property;
                }
            }
        }
        return null;
    }
    
    //returns the property ids that a homeowner has
    public List<String> propertyIdsByHomeOwnerID(String id){
        List<String> propertyIds = new ArrayList<>();
        for(Property property: propertiesID.values()){
            if(property.getOwner().equals(id)){
                propertyIds.add(property.getPropertyId());
            }
        }
        return propertyIds;
    }
    
    //iterates through repository adding all universities avoiding duplicates
    public List<String> getCurrentValidUnis(){
        List<String> validUnis = new ArrayList<>();

        for(Property property: propertiesID.values()){
            if(!validUnis.contains(property.getUniversity())){
                validUnis.add(property.getUniversity());
            }
        }

        return validUnis;
    }

    //returns amount of properties in repository
    public void propertyCount(){
        System.out.println(propertiesID.size());
    }
}