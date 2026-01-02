import java.util.*;
import java.time.LocalTime;

public class RentalAgreement{
    private String agreementID;
    private HomeOwner agreementHomeOwner;
    private Student agreementStudent;
    private BookingSlot agreementSlot;

    public RentalAgreement(String agreementID, HomeOwner homeOwner, Student student, BookingSlot bookingSlot ){
        this.agreementID = agreementID;
        this.agreementHomeOwner = homeOwner;
        this.agreementStudent = student;
        this.agreementSlot = bookingSlot;
    }
    //getters
    public String getRentalAgreementID(){
        return agreementID;
    }
    public HomeOwner getRentalAgreementHomeOwner(){
        return agreementHomeOwner;
    }
    public Student getRentalAgreemStudent(){
        return agreementStudent;
    }
    public BookingSlot getRentalAgreementBookingSlot(){
        return agreementSlot;
    }

}