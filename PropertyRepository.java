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
    public void setPropertyIndex(int index){
        propertyIndex = index;
    }
    public void setRoomIndex(int index){
        roomIndex = index;
    }
    public void addRoomToProperty(String propertyID, String description, Double price, List<BookingSlot> bookings){
        Property property = getPropertyByID(propertyID);

        Room newRoom = new Room(generateRoomID(), description, price);
        if(!bookings.isEmpty()){
            for(BookingSlot booking: bookings){
                LocalDate start = booking.getStartDate();
                LocalDate end = booking.getEndDate();
                newRoom.addBooking(start, end);
            }
        }

        property.addRoom(newRoom);
    }

    public String generatePropertyID(){
        String paddedNum = String.format("%03d", propertyIndex);
        propertyIndex ++;
        return "P".concat(paddedNum);
    }
    public String generateRoomID(){
        String paddedNum = String.format("%03d", roomIndex);
        roomIndex++;
        return "Ro".concat(paddedNum);
    }

    public void addProperty(Property newProperty){
        propertiesID.put(newProperty.getPropertyId(), newProperty);
    }

    public Property getPropertyByID(String propertyID){
        return propertiesID.get(propertyID);
    }

    public List<Property> getAllProperties(){
        List<Property> allProperties = new ArrayList<>();

        for(Property property: propertiesID.values()){
            allProperties.add(property);
        }
        return allProperties;
    }

    public Boolean isUniIn(String university){
        for(Property property: propertiesID.values()){
            if(property.getUniversity().equalsIgnoreCase(university)){
                return true;
            }
        }
        return false;
    }

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
    
    public List<String> propertyIdsByHomeOwnerID(String id){
        List<String> propertyIds = new ArrayList<>();
        for(Property property: propertiesID.values()){
            if(property.getOwner().equals(id)){
                propertyIds.add(property.getPropertyId());
            }
        }
        return propertyIds;
    }
    
    public List<String> getCurrentValidUnis(){
        List<String> validUnis = new ArrayList<>();

        for(Property property: propertiesID.values()){
            if(!validUnis.contains(property.getUniversity())){
                validUnis.add(property.getUniversity());
            }
        }

        return validUnis;
    }

    public List<Room> roomSearch(String university, LocalDate start, LocalDate end, Double pricePerWeek){

        List<Room> searchResult = new ArrayList<>();

        Boolean searchUniveristy = true;

        for(Property property: propertiesID.values()){
            if(university != null && property.getUniversity() != university){
                searchUniveristy = false;
            }

            if(searchUniveristy){
                for(Room room: property.getRooms()){
                    Boolean returnRoom = true;

                    if((start != null && end != null) && !(room.checkAvailability(start, end))){
                        returnRoom = false;
                    }
                    if(pricePerWeek != null && pricePerWeek < room.getPricePerWeek()){
                        returnRoom = false;
                    }
                    if(returnRoom){
                        searchResult.add(room);
                    }
                }
            }
        }
        return searchResult;
    }

    public void propertyCount(){
        System.out.println(propertiesID.size());
    }
}