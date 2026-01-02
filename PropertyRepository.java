import java.util.*;
import java.time.LocalDate;

public class PropertyRepository{
    private HashMap<String, Property> propertiesID = new HashMap<>();

    public void addProperty(Property newProperty){
        propertiesID.put(newProperty.getPropertyId(), newProperty);
    }

    public Property getPropertyByID(String propertyID){
        return propertiesID.get(propertyID);
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
}