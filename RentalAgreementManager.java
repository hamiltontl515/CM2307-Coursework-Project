import java.util.*;
import java.time.LocalDate;

public class RentalAgreementManager{
    private RentalAgreementRepository rentalAgreementRepository;

    public RentalAgreementManager(RentalAgreementRepository rentalAgreementRepository){
        this.rentalAgreementRepository = rentalAgreementRepository;
    }
    //for a homeowner to accept a rental request and create a rental agreement
    public void acceptRentalAgreement(RentalRequest rentalRequest){
                
    }

    public List<RentalAgreement> agreementByStudent(String studentID){
        return rentalAgreementRepository.agreementByStudent(studentID);
    }
}