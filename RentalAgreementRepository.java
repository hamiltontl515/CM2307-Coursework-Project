import java.util.*;

public class RentalAgreementRepository{
    private HashMap<String, RentalAgreement> agreementsID = new HashMap<>();

    public void addRentalAgreement(RentalAgreement newRentalAgreement){
        agreementsID.put(newRentalAgreement.getRentalAgreementID(), newRentalAgreement);
    }

    public List<RentalAgreement> agreementByStudent(String userID){
        List<RentalAgreement> returnAgreements = new ArrayList<>();
        for(RentalAgreement agreement: agreementsID.values()){
            if(agreement.getRentalAgreemStudent().getUserID().equals(userID)){
                returnAgreements.add(agreement);
            }
        }
        return returnAgreements;
    }
}