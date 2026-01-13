import java.util.*;
import java.time.LocalDate;

public class PropertyManager{
    private PropertyRepository propertyRepo;

    public PropertyManager(PropertyRepository propertyRepository){
        this.propertyRepo = propertyRepository;
    }

    public Property getProperty(String propertyID){
        return propertyRepo.getPropertyByID(propertyID);
    }

    public String getPropertyIDByRoomID(String roomID){
        return propertyRepo.getPropertyByRoom(roomID).getPropertyId();
    }

    public List<Room> roomSearch(String university, LocalDate start, LocalDate end, Double pricePerWeek){

        List<Room> searchResult = new ArrayList<>();

        for(Property property: propertyRepo.getAllProperties()){
            boolean matches = true;
            if(university != null && !property.getUniversity().equalsIgnoreCase(university)){
                matches = false;
            }

            if(matches){
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

    public List<String> validUnis(){
        if (propertyRepo == null) {
            throw new IllegalStateException("PropertyRepository not initialised");
        }
        return propertyRepo.getCurrentValidUnis();
    }

    public void validateRoom(String roomID){
        if(!propertyRepo.isRoomIn(roomID)){
            throw new IllegalArgumentException("ERROR: no room with this ID exists");
        }
    }

    public void validateUni(String university){
        if(!propertyRepo.isUniIn(university)){
            throw new IllegalArgumentException("ERROR: no homes exist in this university yet");
        }
    }
}