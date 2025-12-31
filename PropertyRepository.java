import java.util.*;

public class PropertyRepository{
    private HashMap<String, Property> propertiesID = new HashMap<>();

    public void addProperty(Property newProperty){
        propertiesID.put(newProperty.getPropertyId(), newProperty);
    }

    public Property getPropertyByID(String propertyID){
        return propertiesID.get(propertyID);
    }
}