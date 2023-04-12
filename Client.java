public class Client {
    private int id;

    private static int idIncrease = 0;
    private String firstName;
    private String lastName;
    private int yearOfBirth;

    private boolean vipClientChecker = false;

    private String server = " ";
    private Gender gender;
    private Request[] requests;
    private int arrivalTime;

    private boolean clientServedChecker = false;
    private int waitingTime;
    private int timeInQueue;
    private int serviceTime;
    private int departureTime;
    private boolean clientCurrentlyServed = false;
    private boolean clientInQueue = false;

    private int serviceLevel;
    private int patience;

    private Queue queue;

    private QueueSystem queueSystem;

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }
    public void setLastName(String lastName) {
        this.lastName = lastName;

    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;

    }
    public void setGender(String gender) {
        if (gender == "MALE")
        this.gender = Gender.MALE;
        else if (gender == "FEMALE") {
            this.gender = Gender.FEMALE;
        }

    }
    public void setArrivalTime(int arrivalTime) {
        int negativeChecker = arrivalTime * -1;
        if (negativeChecker > 0 || this.arrivalTime == 0) {
            this.arrivalTime = queueSystem.getClock();

        }
        else {
            this.arrivalTime = arrivalTime;
        }
        this.arrivalTime = arrivalTime;

    }
    public void setPatience(int patience) {
        this.patience = patience;

    }
    public void patienceIncrease(int value) {
        this.patience += value;
    }
    public void setRequests(Request[] requests) {
        this.requests = requests;

    }
    public String getFirstName() {
        return this.firstName;

    }
    public String getLastName() {
        return this.lastName;

    }
    public  int getYearOfBirth() {
        return this.yearOfBirth;

    }
    public Gender getGender() {
        return this.gender;

    }
    public int getArrivalTime() {
        return this.arrivalTime;

    }
    public int getPatience() {
        return this.patience;

    }

    public Request[] getRequests() {
        return this.requests;

    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getWaitingTime() {
        return this.waitingTime;
    }

    public void setTimeInQueue(int timeInQueue) {
        this.timeInQueue = timeInQueue;
    }

    public int getTimeInQueue() {
        return this.timeInQueue;
    }

    public int getDepartureTime() {
        return this.departureTime;
    }

    public void setServiceLevel(int serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public int getServiceLevel() {
        return serviceLevel;
    }
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }
    public int getServiceTime() {
        return this.serviceTime;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int num) {
        this.id = num;

    }
    public Client(String firstName, String lastName,
                   int yearOfBirth, String gender, int arrivalTime, int patience, Request[] requests) {
        this.id = ++idIncrease;
        this.waitingTime = 0;
        this.timeInQueue = 0;
        this.serviceTime = 0;
        setArrivalTime(arrivalTime);
        this.departureTime = 0;
        this.yearOfBirth = yearOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.requests = requests;
        this.patience = patience;
        setGender(gender);


    }
    public Client (String firstName, String lastName, int yearOfBirth,
                   String gender, int patience, Request[] requests) {
        this.id = ++idIncrease;
        this.arrivalTime = queueSystem.getClock();
        this.waitingTime = 0;
        this.timeInQueue = 0;
        this.serviceTime = 0;
        this.departureTime = 0;
        this.yearOfBirth = yearOfBirth;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patience = patience;
        setGender(gender);
        this.requests = requests;

    }
        public Client (String firstName,
        String lastName, int yearOfBirth, String gender, int patience) {
            this.id = ++idIncrease;
            this.arrivalTime = queueSystem.getClock();
            this.waitingTime = 0;
            this.timeInQueue = 0;
            this.serviceTime = 0;
            this.departureTime = 0;
            this.yearOfBirth = yearOfBirth;
            this.firstName = firstName;
            this.lastName = lastName;
            this.patience = patience;
            setGender(gender);
        }

        public void setDepartureTime(int departureTime) {
            if (departureTime == 0) {
                this.departureTime = 0;
            } else if (this.patience >= this.arrivalTime + this.waitingTime + this.timeInQueue) {
                departureTime = this.arrivalTime + this.waitingTime + this.timeInQueue;
                this.departureTime = departureTime;
            }
        }

    public int getPriority() {
        return 0;
    }
    public int getMemberSince() {
        return 0;
    }
    public int estimateServiceLevel() {
        if (this.departureTime == 0) {
            return -1;
        }
        int serviceLevel = 10;
        int increase = 4;
        while (increase <= 8) {
            if (serviceLevel == 0) {
                break;
            }
            if (this.waitingTime > increase) {
                serviceLevel -= 1;
                increase += 2;
            }
            else {
                increase = 4;
                break;
            }

        }
        while (increase <= 8) {
            if (serviceLevel == 0) {
                break;
            }
            if (this.timeInQueue > increase) {
                serviceLevel -= 1;
                increase += 2;
            }
            else {
                break;
                }

            }
            setServiceLevel(serviceLevel);
        return serviceLevel;
        }
        public int serviceTime() {
            if (this.requests == null) {
                return 0;
            }

            for(int i = 0; i < requests.length; i++) {
               serviceTime += requests[i].calculateProcessingTime();
            }
            setServiceTime(serviceTime);
            return serviceTime;

        }
        public void ClientServedChecker(boolean checker) {
        this.clientServedChecker = checker;


        }
        public boolean getClientServedChecker() {
        return this.clientServedChecker;
        }
        public void ClientInQueue(boolean checker) {
        this.clientInQueue =  checker;
        }

    public boolean getClientInQueue() {
        return this.clientInQueue;
    }

    public void ClientCurrentlyServed(boolean value) {
        this.clientCurrentlyServed = value;
        }
        public boolean getClientCurrentlyServed() {
        return this.clientCurrentlyServed;
        }
        public void setServer(String server) {
            this.server = server;
        }
        public void TimeInQueueIncreaser() {
        this.timeInQueue += 1;
        }
        public void waitingLineIncreaser() {
        this.waitingTime += 1;
        }
        public boolean getVIPClientChecker() {
        return vipClientChecker;
        }


        public String toString() {
            return "Client: " + "<" + this.lastName + ">" + ", " + "<"
            + this.firstName + ">\n" + "** Arrival Time    : " + this.arrivalTime + "\n"
            + "** Waiting Time    : " + this.waitingTime + "\n" +
            "** Time in Queue   : " + this.timeInQueue + "\n" +
            "** Service Time    : " + this.serviceTime + "\n" +
            "** Departure Time  : " + this.departureTime + "\n" +
            "** Served By Server: " + this.server + "\n" +
            "** Service Level   : " + this.serviceLevel + "\n";

        }



}
