import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public List<String> propertyIdsByHomeownerID(String homeOwnerID){
        return propertyRepo.propertyIdsByHomeOwnerID(homeOwnerID);
    }

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
    public String addPropertyAndReturnID(String address, String university, String owner){
        String propertyID = propertyRepo.generatePropertyID();
        Property newProperty = new Property(propertyID, address, university, owner);
        propertyRepo.addProperty(newProperty);
        return propertyID;
    }
    public void addRoomToProperty(String propertyID, String description, Double price, List<BookingSlot> bookings){
        propertyRepo.addRoomToProperty(propertyID, description, price, bookings);
    }

    public void validateRoomDescription(String description){
        if(description.length()>200){
            throw new IllegalArgumentException("ERROR: entered description is too long.");
        }
    }

    public void validateRoomPrice(String price){
        String priceRegex = "^\\d+\\.\\d{2}$";

        if(!price.matches(priceRegex)){
            throw new IllegalArgumentException("ERROR: price entered is in wrong format.");
        }
    }
    public void validateDate(String date){
        String dateRegex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";

        if(!date.matches(dateRegex)){
            throw new IllegalArgumentException("ERROR: date format incorrect");
        }
    }
    public LocalDate stringToDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return LocalDate.parse(date, formatter);
    }
    public Double stringToPrice(String price){
        return Double.parseDouble(price);
    }
}