package clinic_Database;

/**
 *
 * @author dell
 */
//class for patient history
public class Patient_History {

	private String visitDate;
	private String present_Illness;
	private String Prescribedmedication;
	private int patientId;
	
	//constructor
	Patient_History(String visit_Date, String present_Illness, String medication, int patientId){
		
		this.visitDate = visit_Date;
		this.present_Illness = present_Illness;
		this.Prescribedmedication = medication;
		this.patientId = patientId;
	}

        public String getVisitDate() {
            return visitDate;
        }

        public String getPresent_Illness() {
            return present_Illness;
        }

        public String getPrescribedmedication() {
            return Prescribedmedication;
        }

        public int getPatientId() {
            return patientId;
        }	
}