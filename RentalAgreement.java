

public class RentalAgreement{
    private String agreementID;
    private HomeOwner agreementHomeOwner;
    private Student agreementStudent;
    private Room agreementRoom;
    private BookingSlot agreementSlot;

    public RentalAgreement(String agreementID, HomeOwner homeOwner, Student student, Room agreementRoom, BookingSlot bookingSlot ){
        this.agreementID = agreementID;
        this.agreementHomeOwner = homeOwner;
        this.agreementStudent = student;
        this.agreementRoom = agreementRoom;
        this.agreementSlot = bookingSlot;
    }
    //getters
    public String getRentalAgreementID(){
        return agreementID;
    }
    public HomeOwner getRentalAgreementHomeOwner(){
        return agreementHomeOwner;
    }
    public Student getRentalAgreementStudent(){
        return agreementStudent;
    }
    public BookingSlot getRentalAgreementBookingSlot(){
        return agreementSlot;
    }

    //displays the rental agreement
    public void displayAgreement(){
        System.out.println("Agreement ID:"+agreementID);
        System.out.println("Agreement room ID:"+ agreementRoom.getRoomID());
        System.out.println("Agreement slot from:"+ agreementSlot.getStartDate()+"to:"+agreementSlot.getEndDate());
        System.out.println("Agreement home owner:"+agreementHomeOwner.getName());
        System.out.println("Agreement Student:"+agreementStudent.getName());
    }

}