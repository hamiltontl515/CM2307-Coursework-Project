import java.util.*;

public class RentalAgreementManager{
    private RentalAgreementRepository rentalAgreementRepository;

    public RentalAgreementManager(RentalAgreementRepository rentalAgreementRepository){
        this.rentalAgreementRepository = rentalAgreementRepository;
    }
    //for a homeowner to accept a rental request and create a rental agreement
    public void addNewAgreement(RentalRequest rentalRequest, HomeOwner owner, Student student, Room room){
        RentalAgreement newAgreement = new RentalAgreement(rentalAgreementRepository.generateAgreementID(), owner, student, room, rentalRequest.getRentalRequestBookingSlot());
        rentalAgreementRepository.addRentalAgreement(newAgreement);
    }

    public List<RentalAgreement> agreementByStudent(String studentID){
        return rentalAgreementRepository.agreementByStudent(studentID);
    }
}