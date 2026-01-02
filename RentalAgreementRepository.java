import java.util.*;
import java.time.LocalTime;

public class RentalAgreementRepository{
    private HashMap<String, RentalAgreement> agreementsID = new HashMap<>();

    public void addRentalAgreement(RentalAgreement newRentalAgreement){
        agreementsID.put(newRentalAgreement.getRentalAgreementID(), newRentalAgreement);
    }
}