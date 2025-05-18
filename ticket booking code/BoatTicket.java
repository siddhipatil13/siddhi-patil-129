@@ -0,0 +1,48 @@
import java.util.List;

public class BoatTicket {
    private String boatName;
    private int numberOfMembers;
    private List<String> memberNames;
    private boolean isPaid;
    private String paymentMethod;

    public BoatTicket(String boatName, int numberOfMembers, List<String> memberNames, boolean isPaid, String paymentMethod) {
        this.boatName = boatName;
        this.numberOfMembers = numberOfMembers;
        this.memberNames = memberNames;
        this.isPaid = isPaid;
        this.paymentMethod = paymentMethod;
    }

    // Getter for boat name
    public String getBoatName() {
        return boatName;
    }

    // Getter for number of members
    public int getNumberOfMembers() {
        return numberOfMembers;
    }

    // Getter for member names
    public List<String> getMemberNames() {
        return memberNames;
    }

    // Getter for payment method
    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Method to return a string representation of the ticket
    @Override
    public String toString() {
        String memberNamesString = String.join(", ", memberNames);  // Combine member names
        return "Boat: " + boatName + "\n" +
               "Total Members: " + numberOfMembers + "\n" +
               "Member Names: " + memberNamesString + "\n" +
               "Payment Method: " + paymentMethod + "\n" +
               "--------------------------------------------------";
    }
}