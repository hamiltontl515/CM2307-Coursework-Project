import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PropertyManager{
    private PropertyRepository propertyRepo;

    public PropertyManager(PropertyRepository propertyRepository){
        this.propertyRepo = propertyRepository;
    }

    //returns the property from its id
    public Property getProperty(String propertyID){
        return propertyRepo.getPropertyByID(propertyID);
    }

    //returns the property id from room id
    public String getPropertyIDByRoomID(String roomID){
        return propertyRepo.getPropertyByRoom(roomID).getPropertyId();
    }

    //returns a list of all the property ids a homeowner has
    public List<String> propertyIdsByHomeownerID(String homeOwnerID){
        return propertyRepo.propertyIdsByHomeOwnerID(homeOwnerID);
    }

    //returns a list of room ids by a that are in a homeowners properties
    public List<String> roomIDsByHomeOwnerID(String ownerID){
        List<String> propertyIDs = propertyIdsByHomeownerID(ownerID);

        List<String> roomIDs = new ArrayList<>();

        for(String propertyID: propertyIDs){
            List<Room> propertyRooms = propertyRepo.getPropertyByID(propertyID).getRooms();
            if(!propertyRooms.isEmpty()){
                for(Room room: propertyRooms){
                    roomIDs.add(room.getRoomID());
                }
            }
        }
        return roomIDs;
    }

    //outputs all property ids that a homeowner has, throwing an exception if they have none
    public void displayHomeownersPropertyIDs(String ownerID){
        List<String> homeOwnersProperties = propertyRepo.propertyIdsByHomeOwnerID(ownerID);
        if(!homeOwnersProperties.isEmpty()){
            for(String id : homeOwnersProperties){
                System.out.println("ID:" + id);
            }
        }else{
            throw new IllegalArgumentException("EROR: you have no properties");
        }
    }

    //returns a list of rooms conforming to a students criteria
    public List<Room> roomSearch(String university, LocalDate start, LocalDate end, Double pricePerWeek){

        List<Room> searchResult = new ArrayList<>();

        for(Property property: propertyRepo.getAllProperties()){
            boolean matches = true;
            if(university != null && !property.getUniversity().equalsIgnoreCase(university)){ //given a specified university, set matches to false property isnt in university
                matches = false;
            }

            if(matches){ // only call if property is in university
                for(Room room: property.getRooms()){ // iterate and add the rooms to the return result list if the room is avilable in at the given time and if it matches price requirement
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

    //this function returns the universities that are in the repo
    public List<String> validUnis(){
        if (propertyRepo == null) {
            throw new IllegalStateException("PropertyRepository not initialised");
        }
        return propertyRepo.getCurrentValidUnis();
    }

    //this method checks is the a room id macthes a room id in the repository
    public void validateRoom(String roomID){
        if(!propertyRepo.isRoomIn(roomID)){
            throw new IllegalArgumentException("ERROR: no room with this ID exists");
        }
    }

    //this method checks the property repository for any properties that have this university
    public void validateUni(String university){
        if(!propertyRepo.isUniIn(university)){
            throw new IllegalArgumentException("ERROR: no rooms/properties in this university");
        }
    }
    //this function creates new property, adds it to the repository then returns its newly generated id
    public String addPropertyAndReturnID(String address, String university, String owner){
        String propertyID = propertyRepo.generatePropertyID();
        Property newProperty = new Property(propertyID, address, university, owner);
        propertyRepo.addProperty(newProperty);
        return propertyID;
    }
    //adds a room to a property
    public void addRoomToProperty(String propertyID, String description, Double price, List<BookingSlot> bookings){
        propertyRepo.addRoomToProperty(propertyID, description, price, bookings);
    }

    // this method makes sure that a rooms description is valid, throws an exception if not
    public void validateRoomDescription(String description){
        if(description.length()>200){ // only valid if decription is under 200 words
            throw new IllegalArgumentException("ERROR: entered description is too long.");
        }
    }

    //this method makes sure that the price entered is conforming to pounds.pence, throws an exception if not valid
    public void validateRoomPrice(String price){
        String priceRegex = "^\\d+\\.\\d{2}$"; // regex that excludes anything by a number follwed by a . folled by 2 numbers(pence)

        if(!price.matches(priceRegex)){
            throw new IllegalArgumentException("ERROR: price entered is in wrong format.");
        }
    }
    //this method makes sure that the date entered conforms to a dd/mm/yyyy, throws an exception is invalid
    public void validateDate(String date){
        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$"; // regex excluding anything byt dd.mm.yyyy

        if(!date.matches(dateRegex)){
            throw new IllegalArgumentException("ERROR: date format incorrect");
        }
    }
    //this functon returns a local date type of format dd-mm-yyyy
    public LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //formatter

        return LocalDate.parse(date, formatter);
    }
    //this function returns a double for price from a string
    public Double stringToPrice(String price){
        return Double.parseDouble(price);
    }
}