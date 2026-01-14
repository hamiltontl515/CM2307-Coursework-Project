import java.util.*;

public class RentalAgreementRepository{
    private HashMap<String, RentalAgreement> agreementsID = new HashMap<>();
    private int agreementIndex;

    public RentalAgreementRepository(){
        agreementIndex =0;
    }
    public void setAgreementIndex(int index){
        agreementIndex = index;
    }

    public void addRentalAgreement(RentalAgreement newRentalAgreement){
        agreementsID.put(newRentalAgreement.getRentalAgreementID(), newRentalAgreement);
    }
    public String generateAgreementID(){
        String paddedNum = String.format("%3d", agreementIndex);
        agreementIndex ++;
        return "A".concat(paddedNum);
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