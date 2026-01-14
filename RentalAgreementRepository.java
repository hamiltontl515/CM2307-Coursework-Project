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

    //creates a new index for a new rental agreemnet
    public String generateAgreementID(){
        String paddedNum = String.format("%03d", agreementIndex); // pads with extra zeros so that its 3 digits long
        agreementIndex ++;
        return "A".concat(paddedNum);
    }

    //returns list of agreements a student user has
    public List<RentalAgreement> agreementByStudent(String userID){
        List<RentalAgreement> returnAgreements = new ArrayList<>();
        for(RentalAgreement agreement: agreementsID.values()){
            if(agreement.getRentalAgreementStudent().getUserID().equals(userID)){
                returnAgreements.add(agreement);
            }
        }
        return returnAgreements;
    }
    //returns a list of agreements thet a homeowner user has
    public List<RentalAgreement> agreementByHomeOwner(String userID){
        List<RentalAgreement> returnAgreements = new ArrayList<>();
        for(RentalAgreement agreement: agreementsID.values()){
            if(agreement.getRentalAgreementHomeOwner().getUserID().equals(userID)){
                returnAgreements.add(agreement);
            }
        }
        return returnAgreements;
    }
}