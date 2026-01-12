import java.util.*;
import java.time.LocalDate;

public class PropertyManager{
    private PropertyRepository propertyRepo;


    public List<Room> roomSearch(String university, LocalDate start, LocalDate end, Double pricePerWeek){

        List<Room> searchResult = new ArrayList<>();

        Boolean searchUniveristy = true;

        for(Property property: propertyRepo.getAllProperties()){
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

    public List<String> validUnis(){
        return propertyRepo.getCurrentValidUnis();
    }

    public void validateUni(String university){
        if(!propertyRepo.isUniIn(university)){
            throw new IllegalArgumentException("ERROR: no homes exist in this university yet");
        }
    }
}