import java.io.*;
import java.util.ArrayList;
import java.util.List;

final class HomeOwner extends User{
    private List<Integer> rooms = new ArrayList<>();

    public HomeOwner(String UserID, String Name, String Email, List<Integer> rooms){
        super(UserID, Name, Email);
        this.rooms = new ArrayList<>(rooms);
    }

    //getters
    public ArrayList<Integer> getRooms(){
        return new ArrayList<>(rooms);
    }

    //modifyers
    public void addRoom(int newRoomID){
        rooms.add(newRoomID);
    }
    public void removeRoom(int roomID){
        if(rooms != null && rooms.contains(roomID)){
            rooms.remove(roomID);
        }else{
            throw new IllegalArgumentException("room does not exist");
        }
    }
}