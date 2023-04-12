public class VIPClient extends Client implements Prioritizable {
    private int id;
    private int memberSince;
    private int priority;
    private String firstName;
    private String server = " ";
    private String lastName;
    private int birthYear;

    private boolean vipClientChecker = true;
    private Gender gender;
    private int arrivalTime;
    private int patience;
    private Request[] requests;
    private boolean clientCurrentlyServed = false;
    private boolean clientInQueue = false;


    public VIPClient(String firstName, String lastName, int birthYear, String gender, int arrivalTime,
    int patience, Request[] requests, int membberSince, int priority) {
        super(firstName, lastName, birthYear, gender, arrivalTime, patience, requests);
        setMemberSince(memberSince);
        setFirstName(firstName);
        setLastName(lastName);
        setGender(gender);
        setArrivalTime(arrivalTime);
        setPatience(patience);
        setRequests(requests);
        setPriority(priority);
        setBirthYear(birthYear);

    }


    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setMemberSince(int memberSince) {
        this.memberSince = memberSince;
    }
    public int getMemberSince() {
        return memberSince;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public boolean getVIPClientChecker() {
        return vipClientChecker;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }
    public void setLastName(String lastName) {
        this.lastName = lastName;

    }


    public int getPriority() {
        return this.priority;
    }

    public String toString() {
        return "Client: " + "<" + this.lastName + ">" + ", " + "<"
                + this.firstName + ">\n" + "** Arrival Time    : " + this.arrivalTime + "\n"
                + "** Waiting Time    : " + this.getWaitingTime() + "\n" +
                "** Time in Queue   : " + this.getTimeInQueue() + "\n" +
                "** Service Time    : " + this.getServiceLevel() + "\n" +
                "** Departure Time  : " + this.getDepartureTime() + "\n" +
                "** Served By Server: " + this.getQueue().getServerName() + "\n" +
                "** VIP since       : "  + this.birthYear + "\n" +
                "** Service Level   : " + this.getServiceLevel() + "\n";
    }





}

