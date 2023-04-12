
public class QueueSystem {
    private static int clock;
    private static int totalWaitingTime;
    private static Client[] clientsWorld;
    private Queue queue;
    private static int totalClientsInSystem;
    private boolean VIPQueueExists;

    private boolean VIPclientpassesFIFO = false;

    private boolean VIPQueueAcceptingClients = false;

    private Client[] clientsBeingServed;

    private boolean VIPClientsExist = false;
    private static int waitingLineSize;
    private static int processingTimeIncrement = 0;
    private boolean wlFullChecker = false;
    private static Client[] waitingLine;

    private Client[] clientsToSystem;
    private static boolean tvInWaitingArea;
    private boolean multipleClientChecker = false;
    private static boolean coffeeInWaitingArea;
    private static Queue[] queues;

    public QueueSystem(int waitingLineSize, boolean tvInWaitingArea, boolean coffeeInWaitingArea) {
        setTvInWaitingArea(tvInWaitingArea);
        setCoffeeInWaitingArea(coffeeInWaitingArea);
        setWaitingLineSize(waitingLineSize);
        clock = 0;
    }
    public static void setClientsWorld(Client[] clientsWorl) {
        clientsWorld = clientsWorl;
    }
    public static void setQueues(Queue[] queue) {
        queues = queue;
    }
    public void IncreaseTime(int time) {
        clock += time;

    }
    public void arrayOfQueues(Queue queue) {
        if (queues == null) {
            int supportingItemsLength = 1;

            queues = new Queue[supportingItemsLength];

            queues[0] = queue;

            this.queues = queues;


            return;

        }



        Queue[] tempQueues = new Queue[queues.length + 1];


        for(int i=0; i <= queues.length; i++) {

            if (i == queues.length) {

                tempQueues[i] = queue;

                break;

            }
            tempQueues[i] = queues[i];
        }
        queues = new Queue[tempQueues.length];

        for(int i=0; i < queues.length; i++) {

            queues[i] = tempQueues[i];

        }

        this.queues = queues;

    }
    public static void setTotalWaitingTime(int totalWaitingTim) {
        totalWaitingTime = totalWaitingTim;

    }
    public static void setTotalClientsInSystem(int totalClientsInSyste) {
        totalClientsInSystem = totalClientsInSyste;
    }
    public static void setWaitingLine(Client[] waitingLin) {
        waitingLine = waitingLin;
    }
    public static void setWaitingLineSize(int waitingLineSiz) {
        waitingLineSize = waitingLineSiz;
    }

    public static void setTvInWaitingArea(boolean tvInWaitingAre) {
        tvInWaitingArea = tvInWaitingAre;

    }
    public static void setCoffeeInWaitingArea(boolean coffeeInWaitingAre) {
        coffeeInWaitingArea = coffeeInWaitingAre;

    }
    public static void setClock(int cloc) {
        clock = cloc;
    }


    public static int getClock() {
        return clock;
    }

    public static int getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public static Client[] getClientsWorld() {
        return clientsWorld;
    }
    public static int getTotalClientsInSystem() {
        return totalClientsInSystem;
    }

    public static int getWaitingLineSize() {
        return waitingLineSize;
    }

    public static Client[] getWaitingLine() {
        return waitingLine;
    }
    public static Queue[] getQueues() {
        return queues;
    }
    public boolean multipleClientsChecker(boolean value) {
        this.multipleClientChecker = value;
        return this.multipleClientChecker;
    }
    public void patienceIncreaseChecker() {
        if (tvInWaitingArea == true) {
            for (int i = 0; i < clientsWorld.length; i++) {
                if (clientsWorld[i] != null) {
                    clientsWorld[i].patienceIncrease(20);
                }
            }
        }
        if (coffeeInWaitingArea == true) {
            for (int i =0; i < clientsWorld.length; i++) {
                if (2023 - clientsWorld[i].getYearOfBirth() >= 18 && clientsWorld[i] != null) {
                    clientsWorld[i].patienceIncrease(15);

                }
            }
        }
    }
    public void increaseTime(int time) {
        for (int i = 1; i <= time; i++) {
            increaseTime();
        }
    }
    public void increaseTime() {
        clock++;
        //at clock == 1, just get the people with AT = 1, use FIFO to assign them to server/queue
        if (clock == 1) {
            VIPclientChecker();
            patienceIncreaseChecker();
            int multipleClientsChecker = 0;
            for (int i = 0; i < clientsWorld.length; i++) {
                if (clientsWorld[i].getArrivalTime() == clock) {
                    clientsWorld[i].serviceTime();
                    //from this array, move them to server, queue, or WL, it should be empty before next clock
                    ClientsMoveToSystem(clientsWorld[i]);
                    multipleClientsChecker += 1;
                }
            }
            if (multipleClientsChecker > 1) {
                multipleClientsChecker(true);

            }
            if (multipleClientChecker == true) {
                arrangeAscending(clientsToSystem);
            }
            //if its just one client, they will be served by the first queue server
            if (this.multipleClientChecker == false && clientsToSystem != null) {
                queues[0].ClientToBeServed(clientsToSystem[0]);
            //if multiple clients enter system, we will use FIFO method to determine
            //who goes to server, and if servers are full, they go to queue, then WL

            } else if (this.multipleClientChecker == true && clientsToSystem != null) {
                //call FIFO method to determine who goes to servers, queue, or WL.
                FIFO(clientsToSystem);
            }
            this.multipleClientChecker = false;
            RequestUpdater();
            clientsToSystem = null;

        }
        //code for when
        if (clock > 1) {
            int multipleClientsChecker = 0;

            /* RequestUpdater updates requests, checks if client has been served, moves
            next person from queue to server, or WL to server.
             */
            RequestUpdater();
            //updates attributes
            clientInSystemAttributeUpdater();
            if (clientsWorld != null) {


            for (int i = 0; i < clientsWorld.length; i++) {
                if (clientsWorld[i].getArrivalTime() == clock && clientsWorld[i].getClientServedChecker() == false) {
                    clientsWorld[i].serviceTime();
                    ClientsMoveToSystem(clientsWorld[i]);
                    multipleClientsChecker += 1;
                }
            }
                if (multipleClientsChecker > 1) {
                    multipleClientsChecker(true);

                }
                if (this.multipleClientChecker == true) {
                    arrangeAscending(clientsToSystem);
                }

            if (this.multipleClientChecker == false && clientsToSystem != null) {
                sendClientToQorS(clientsToSystem[0]);

            }
             else if (this.multipleClientChecker == true && clientsToSystem != null) {
                //call FIFO method to determine who goes to servers, queue, or WL.
                FIFO(clientsToSystem);
            } //checks which clients need to leave
            }
            clientsLeaveNoServe();
            clientsToSystem = null;
            this.multipleClientChecker = false;
        }

        }

    public void RequestUpdater() {
        for (int i = 0; i < queues.length; i++) {
            Request currentReq = queues[i].getCurrentRequest();
            if (currentReq == null && queues[i].getClientBeingServed() == null) {
                continue;
            } else if (currentReq == null && queues[i].getClientBeingServed() != null) {
                queues[i].RequestProcessing();
                currentReq = queues[i].getCurrentRequest();
                queues[i].setProcessingStartTime(0);
                if (currentReq.getStartTime() == -1) {
                    currentReq.setStartTime(clock);
                    currentReq.setEndTime(clock);
                    currentReq.setStatus(Status.IN_PROGRESS);
                    continue;
                }
            }
            if (currentReq != null && queues[i].getClientBeingServed() != null) {

                if (currentReq.getStartTime() == -1) {
                    currentReq.setStartTime(clock);
                    currentReq.setEndTime(clock);
                    currentReq.setStatus(Status.IN_PROGRESS);
                } else if (currentReq.getStartTime() != clock && clock != currentReq.getEndTime()) {
                    queues[i].processingTimeIncrementer();
                } else if (clock == currentReq.getEndTime()) {
                    currentReq.setStatus(Status.PROCESSED);
                    queues[i].requestIndexIncrease();
                } if (queues[i].allRequestsProcessed() == true) {
                    queues[i].setProcessingStartTime(0);
                    queues[i].moveQueueToServer();
                    queues[i].setAllRequestsProcessed(false);
                    moveWLtoServerOrQueue(queues[i]);
                    //if queue is empty, next person from waitingList goes to queue
                    //if queue server is not busy, he goes directly to server
                    //for above
                    //check if queues are full or are not null
                    //if true, move a person from queue to server
                    //if false, move a person from waiting to server
                    //if empty, move the next person from system to queue
                    //to the server;
                }
            }
        }
    }
    public void VIPclientChecker() {
        if (clientsWorld != null) {
            for (int i = 0; i < clientsWorld.length; i++) {
                if (clientsWorld[i] == null) {
                    continue;
                }
               if (clientsWorld[i].getVIPClientChecker() == true) {
                   setVIPClientsExist(true);
               }
            }
            if (queues != null) {
                for (int i = 0; i < queues.length; i++) {
                    if (queues[i] == null) {
                        continue;
                    }
                   if (queues[i].getVIPqueueChecker() == true) {
                       setVIPQueueExists(true);

                       if (queues[i].getAcceptingAnyClients() == true) {
                           setVIPqueueAcceptingClients(true);

                       }
                   }
                }

            }
        }
    }
    public void setVIPQueueExists(boolean value) {
        this.VIPQueueExists = value;
    }
    public void setVIPqueueAcceptingClients(boolean value) {
        this.VIPQueueAcceptingClients = value;

    }
    public void setVIPClientsExist(boolean value) {
        this.VIPClientsExist = value;
    }
    public void clientInSystemAttributeUpdater() {
        // update attributes for clients in queue
        for (int i = 0; i < queues.length; i++) {
            if (queues[i] != null) {
                queues[i].attributeUpdater();
            }

        }
        if (waitingLine == null) {
            return;
        }
        for (int i = 0; i < waitingLine.length; i++) {
            if (waitingLine[i] != null) {
                waitingLine[i].waitingLineIncreaser();
            }
        }
    }
    public void moveWLtoServerOrQueue(Queue queue) {
        if (waitingLine == null) {
            return;
        }
        if (queue.getClientBeingServed() == null) {
            for (int i = 0; i < waitingLine.length; i++) {
                if (waitingLine[0] != null) {
                    queue.ClientToBeServed(waitingLine[0]);
                    waitingLine[0] = null;

                }
            }
            if (waitingLineSize > 1 && waitingLine[0] != null) {
                WLresize();
                return;
            }
        }
        else if (queue.getClientBeingServed() != null) {
            if (queue.getQueueFullChecker() == false) {
                for (int i = 0; i < waitingLine.length; i++) {
                    if (waitingLine[0] != null) {
                        queue.addClientsInQueue(waitingLine[0]);
                        waitingLine[0] = null;

                    }
                }
                if (waitingLineSize > 1 && waitingLine[0] == null) {
                    WLresize();
                    return;
                }
            }
        }
        return;
    }
    public void WLresize() {
        int AmtofPeopleInWL = 0;
        for (int i = 0; i < waitingLine.length; i++) {
            if (waitingLine[i] != null) {
                AmtofPeopleInWL++;
            }
        }
        if (AmtofPeopleInWL == 0) {
            return;
        }
        Client[] tempWaitingLine = new Client[waitingLineSize];
        int index = 0;
        for (int i = 0; i < waitingLine.length; i++) {
            if (waitingLine[i] != null) {
                tempWaitingLine[index] = waitingLine[i];
                index++;

            }
        }

        if (index < waitingLineSize) {
            waitingLine = tempWaitingLine;
        }

    }
    //this method will check which clients must exit the system
    public void clientsLeaveNoServe() {
        //for clients in queue
        if (queues == null) {
            return;
        }
        for (int i = 0; i < queues.length; i++) {
            Client[] individualClientQueue = queues[i].getClientsInQueue();
            if (individualClientQueue == null) {
                return;
            }
            for (int j = 0; j < individualClientQueue.length; j++) {
                if (individualClientQueue[j] != null) {
                    if (individualClientQueue[j].getWaitingTime() +
                            individualClientQueue[j].getTimeInQueue() > individualClientQueue[j].getPatience()) {
                            individualClientQueue[j] = null;
                            if (queues[i].getQueueSize() > 1) {
                                queues[i].QueueResize();
                            }
                    }
                }
            }

        }
        //for clients in waitingLine
        if (waitingLine == null) {
            return;
        }
        for (int i = 0; i < waitingLine.length; i++) {
            if (waitingLine[i] != null) {
                if (waitingLine[i].getWaitingTime() +
                        waitingLine[i].getTimeInQueue() > waitingLine[i].getPatience()) {
                    waitingLine[i] = null;
                    if (waitingLineSize > 1) {
                        WLresize();
                    }
                }
            }

        }

    }
    //adding clients to waitingLinne here
    public void waitingLine(Client client) {
        if (waitingLine == null) {

            waitingLine = new Client[1];

            waitingLine[0] = client;

            this.waitingLine = waitingLine;

            return;

        }

        if (waitingLine.length < waitingLineSize) {



        Client[] tempWaitingLine = new Client[waitingLine.length + 1];


        for(int i=0; i <= waitingLine.length; i++) {

            if (i == waitingLine.length) {

                tempWaitingLine[i] = client;

                break;

            }
            tempWaitingLine[i] = waitingLine[i];
        }
        waitingLine = new Client[tempWaitingLine.length];

        for(int i=0; i < waitingLine.length; i++) {

            waitingLine[i] = tempWaitingLine[i];

        }

        this.waitingLine = waitingLine;
            waitListFullChecker(false);
        }
        else {
            waitListFullChecker(true);

        }
}
    public boolean waitListFullChecker(boolean value) {
        wlFullChecker = value;
        return value;
    }
//checking if a queue is full here
    public boolean QueueFullChecker() {
        for (int i = 0; i < queues.length; i++) {
            if (queue.getQueueFullChecker() == false)  {
                return false;


            }
        }
        return true;
    }
    //if waitingLine is not full and the queue is not empty, FIFO will apply to see who goes in queue
    //next
    //this FIFO is queueToServer and initial case
    public void FIFO(Client[] clients) {
        Client clientLargestAge = null;
        int amountOfClients = clients.length - 1;
        boolean agePriority = false;
        boolean processingPriority = false;
        boolean lastNamePriority = false;
        boolean firstNamePriority = false;
        boolean VIPclientInSystem = false;
        for (int i = 0; i < clients.length; i++) {
            if(clients[i].getVIPClientChecker() == true) {
                VIPclientInSystem = true;
            }
        }
        if (VIPclientInSystem == true) {
            vipCLientFIFO(clients);
            if (VIPclientpassesFIFO == true) {
                return;
            }
        }
        //loop and check if
        //loop through and check if any client has the same age
        //if so, we will have to
        for (int i = 0; i < clients.length - 1; i++) {
            if (agePriority == true) {
                break;
            }
            for (int j = i + 1; j < clients.length; j++) {
                if (clients[i].getYearOfBirth() == clients[j].getYearOfBirth()) {
                    agePriority = true;
                    break;
                }

            }
        }
        //if clients entering the system also have the same age, they must be compared by
        //service time so we check if thats similar too
        if (agePriority == true) {
            for (int i = 0; i < clients.length - 1; i++) {
                if (processingPriority == true) {
                    break;
                }
                for (int j = i + 1; j < clients.length; j++) {
                    if (clients[i].getServiceTime() == clients[j].getServiceTime()) {
                        processingPriority = true;
                        break;
                    }

                }
            }

        }
        if (processingPriority == true) {
            for (int i = 0; i < clients.length - 1; i++) {
                if (lastNamePriority == true) {
                    break;
                }
                for (int j = i + 1; j < clients.length; j++) {
                    if (clients[i].getLastName().equals(clients[j].getLastName())) {
                        lastNamePriority = true;
                        break;
                    }

                }
            }

        }
        //if they have same lastName, we compare first names
        if (lastNamePriority == true) {
            for (int i = 0; i < clients.length - 1; i++) {
                if (firstNamePriority == true) {
                    break;
                }
                for (int j = i + 1; j < clients.length; j++) {
                    if (clients[i].getFirstName().equals(clients[j].getFirstName())) {
                        firstNamePriority = true;
                        break;
                    }

                }
            }

        }

        //if clients have the same age and processing time, we check last name
        //priority 1
        //rearrange array from highest to lowest age. Assign highest age to server.


        if (agePriority == false) {
            int largestAge = 0;
            for (int i = 0; i < clients.length; i++) {
                for (int j = i; j < clients.length; j++) {

                    if (2023 - clients[i].getYearOfBirth() < 2023 - clients[j].getYearOfBirth()) {
                        clientLargestAge = clients[i];
                        clients[i] = clients[j];
                        clients[j] = clientLargestAge;
                    }
                }

            }
            //after rearranging, use loop to send them to server/queue in order
            for (int i = 0; i < clients.length; i++) {

                sendClientToQorS(clients[i]);

            }
            return;
        }
        //priority 2
        //arrange processingOrder in ascending order
        //send to system in order
        else if (processingPriority == false) {
            Client lowestPT = null;

            for (int i = 0; i < clients.length; i++) {
                for (int j = i; j < clients.length; j++)
                    if (clients[i].getServiceTime() > clients[j].getServiceTime()) {
                        lowestPT = clients[i];
                        clients[i] = clients[j];
                        clients[j] = lowestPT;

                    }
            }
            //after rearranging, use loop to send them to server/queue in order
            for (int i = 0; i < clients.length; i++) {

              sendClientToQorS(clients[i]);

            }
            return;
        }

        //priority 3 - alphabetic order of last name
        //using for loop to set initial last name;
        else if (lastNamePriority == false) {
            Client clientWithHighestLN = null;

            for (int i = 0; i < clients.length; i++) {
                for (int j = i; j < clients.length; j++) {

                    if (clients[i].getLastName().charAt(0) < clients[j].getLastName().charAt(0)) {
                        clientWithHighestLN = clients[i];
                        clients[i] = clients[j];
                        clients[j] = clientWithHighestLN;

                    }

                }
            }
            for (int i = 0; i < clients.length; i++) {

                 sendClientToQorS(clients[i]);
            }
            return;
        }
        //for priority 4 flip sign to ">" above
        else if (firstNamePriority == false) {
            Client clientWithHighestFN = null;

            for (int i = 0; i < clients.length; i++) {
                for (int j = i; j < clients.length; j++) {

                    if (clients[i].getFirstName().charAt(0) < clients[j].getFirstName().charAt(0)) {
                        clientWithHighestFN = clients[i];
                        clients[i] = clients[j];
                        clients[j] = clientWithHighestFN;

                    }

                }
            }
            for (int i = 0; i < clients.length; i++) {
                sendClientToQorS(clients[i]);
            }
            return;

        }
        //last priority, smaller id
        else {
            Client lowestId;
            for (int i = 0; i < clients.length; i++) {
                for (int j = 0; j < clients.length; j++) {
                    if (clientsToSystem[i].getId() > clientsToSystem[j].getId()) {
                        lowestId = clients[i];
                        clients[i] = clients[j];
                        clients[j] = lowestId;


                    }

                }

            }

        }
        for (int i = 0; i < clients.length; i++) {
            sendClientToQorS(clients[i]);
        }
    }
    public void vipCLientFIFO(Client[] clients) {
        boolean PriorityVal = false;
        boolean MemberVal = false;
        //checking if vipclients have the same priority
        for (int i = 0; i < clients.length - 1; i++) {
        if (clients[i].getVIPClientChecker() != true) {
            continue;
        }
            if (PriorityVal == true) {
                break;
            }

            for (int j = i + 1; j < clients.length; j++) {
                if (clients[j].getVIPClientChecker() != true) {
                    continue;

                }

                    if (clients[i].getPriority() == clients[j].getPriority()) {
                        PriorityVal = true;
                        break;
                    }

            }
        }
        if (PriorityVal == true) {
            for (int i = 0; i < clients.length - 1; i++) {
                if (clients[i].getVIPClientChecker() != true) {
                    continue;
                }
                    if (MemberVal == true) {
                        break;
                    }
                for (int j = i + 1; j < clients.length; j++) {
                    if (clients[j].getVIPClientChecker() != true) {
                        continue;
                    }

                        if (clients[i].getMemberSince() == clients[j].getMemberSince()) {
                            MemberVal = true;
                            break;
                        }

                }
            }

        }
        else if (PriorityVal == false) {
            Client largestPriority = null;
            for (int i = 0; i < clients.length; i++) {
                if (clients[i].getVIPClientChecker() != true) {
                    continue;
                }
                for (int j = i; j < clients.length; j++) {
                    if (clients[j].getVIPClientChecker() != true) {
                        continue;
                    }

                    if (clients[i].getPriority() < clients[j].getPriority()) {
                        largestPriority = clients[i];
                        clients[i] = clients[j];
                        clients[j] = largestPriority;
                    }
                }

            }
            //after rearranging, use loop to send them to server/queue in order
            for (int i = 0; i < clients.length; i++) {
                if (clients[i].getVIPClientChecker() != true) {
                    continue;
                }

                sendClientToQorS(clients[i]);

            }
            setVIPfifo(true);
            return;

        }
        else if (MemberVal == false) {
            Client longestMember = null;
            for (int i = 0; i < clients.length; i++) {
                if (clients[i].getVIPClientChecker() != true) {
                    continue;
                }
                for (int j = i; j < clients.length; j++) {
                    if (clients[j].getVIPClientChecker() != true) {
                        continue;
                    }

                    if (clients[i].getMemberSince() < clients[j].getMemberSince()) {
                        longestMember = clients[i];
                        clients[i] = clients[j];
                        clients[j] = longestMember;
                    }
                }

            }
            //after rearranging, use loop to send them to server/queue in order
            for (int i = 0; i < clients.length; i++) {
                if (clients[i].getVIPClientChecker() != true) {
                    continue;
                }

                sendClientToQorS(clients[i]);

            }
            setVIPfifo(true);
            return;

        }
        else {
            int FIFOcounter = 0;
            for (int i = 0; i < clients.length; i++) {
                if (clients[i].getVIPClientChecker() == true) {
                    FIFOcounter++;
                }
                if (FIFOcounter > 1) {
                    break;
                }


            }
            if (FIFOcounter == 1) {
                for (int i = 0; i < clients.length; i++) {
                    if (clients[i].getVIPClientChecker() == true) {
                        sendClientToQorS(clients[i]);
                        }
                    }
                setVIPfifo(true);
                return;
            }


        }

    }
    public void setVIPfifo(boolean value) {
        VIPclientpassesFIFO = value;

    }



    //after FIFO determination, this method is called to either add client to queue or to the server
    public void sendClientToQorS(Client client) {
        //first loop checks if queue server is empty and client will be assigned
        int checkerFulfilled = 0;
        int checkerIfQueueNotFull = 0;
        int checkerifAllQueueIsFull = 0;
        int vipCounter = 0;
        //for vip client
        if (client.getVIPClientChecker() == true && this.VIPQueueExists == true) {
            for (int i = 0; i < queues.length; i++) {
                if (queues[i].getVIPqueueChecker() != true) {
                    continue;
                }
                if (queues[i].getClientBeingServed() == client) {
                    return;
                }
            }

            for (int i = 0; i < queues.length; i++) {
                if (queues[i].getVIPqueueChecker() != true) {
                    continue;
                }
                if (queues[i].getClientBeingServed() == null) {
                    queues[i].ClientToBeServed(client);
                    checkerFulfilled = 1;
                    return;
                }

            }
            //check if all queues are not full, then vip client goes to queue that is available
            if (checkerFulfilled == 0) {
                for (int i = 0; i < queues.length; i++) {
                    if (queues[i].getVIPqueueChecker() != true) {
                        continue;
                    }
                    if (queues[i].getQueueFullChecker() == false) {
                        queues[i].addClientsInQueue(client);
                        return;

                    }


                }
                //if all vip queues are full, client goes to regular queue

            }
        }
        //check if client is already being served
        for (int i = 0; i < queues.length; i++) {
            if (queues[i].getClientBeingServed() == client) {
                return;
            }
        }

        for (int i = 0; i < queues.length; i++) {
            if (queues[i].getClientBeingServed() == null) {
                queues[i].ClientToBeServed(client);
                checkerFulfilled = 1;
                return;
            }
        }
        //check if all queues are not full, then client goes to queue that is lower index
        if (checkerFulfilled == 0) {
            for (int i = 0; i < queues.length; i++) {
                if (queues[i].getQueueFullChecker() == false) {
                    //checkerIfQueueNotFull++;

                }
                else if (queues[i].getQueueFullChecker() == true) {
                    checkerifAllQueueIsFull++;
                }

            }
            if (checkerifAllQueueIsFull != queues.length) {
                Queue lowestQueue = lowestQueueIndex();
                lowestQueue.addClientsInQueue(client);
                checkerFulfilled = 1;
                return;

            }


        }
        //if all queues are full, client checks to see if VIPqueue is available to join
        if (this.VIPQueueExists == true && this.VIPQueueAcceptingClients == true &&
                this.VIPClientsExist == false) {
            //first we check if there is an available VIPqueue
            for (int i = 0; i < queues.length; i++) {
                if (queues[i].getVIPqueueChecker() != true) {
                    continue;
                }
                else if (queues[i].getQueueFullChecker() == false) {
                    queues[i].addClientsInQueue(client);
                    return;
                }

            }

        }
        //if all queues are full, client goes to waitingLine
        if (checkerFulfilled == 0 && (checkerifAllQueueIsFull == queues.length)) {
            waitingLine(client);

        }


    }
    public int numOfVIPQueues() {
        int counter = 0;
        if (queues == null) {
            return 0;
        }
        for (int i = 0; i < queues.length; i++) {
            if (queues[i].getVIPqueueChecker() == true) {
                counter++;
            }
        }
        return counter;
    }

    public void ClientsMoveToSystem(Client clientToSystem) {
        if (clientsToSystem == null) {

            clientsToSystem = new Client[1];

            clientsToSystem[0] = clientToSystem;

            this.clientsToSystem = clientsToSystem;

            return;

        }



        Client[] tempClientToSystem = new Client[clientsToSystem.length + 1];


        for(int i=0; i <= clientsToSystem.length; i++) {

            if (i == clientsToSystem.length) {

                tempClientToSystem[i] = clientToSystem;

                break;

            }
            tempClientToSystem[i] = clientsToSystem[i];
        }
        clientsToSystem = new Client[tempClientToSystem.length];

        for(int i=0; i < clientsToSystem.length; i++) {

            clientsToSystem[i] = tempClientToSystem[i];

        }

        this.clientsToSystem = clientsToSystem;

    }
    //checks for which queue has lower index
    public Queue lowestQueueIndex() {
        Queue lowestIndex = queues[0];
        boolean QueueEqualityChecker = QueueEqualityChecker();
        //first check if all queues have the same index, if so, return closest index
        //queue0 = 5 queue1 = 3 queue2 = 3 - returns queue1;
        if (queues[0].getClientsInQueue() == null) {
            return queues[0];
        }
        int lowestQueueIndex = queues[0].LowestIndex();
        int lowestIndexVal = 0;
        Queue lowerQueueIndex = null;
        if (QueueEqualityChecker == true) {
            for (int i = queues.length - 1; i > 0; i--) {
                if (queues[i].LowestIndex() == lowestIndex.LowestIndex()) {
                    continue;
                }
                if (queues[i].LowestIndex() < lowestIndex.LowestIndex()) {
                    lowestIndex = queues[i];
                    lowestIndexVal = i;
                }
            }
            for (int i = 0; i < queues.length - 1; i++) {
                if (queues[i].LowestIndex() == lowestIndex.LowestIndex()) {
                    lowestIndexVal = Math.min(i,lowestIndexVal);
                    break;
                }

            }
        return queues[lowestIndexVal];
        }
        else if(QueueEqualityChecker == false) {

            for (int i = 0; i < queues.length; i++) {
                if (lowestIndex.LowestIndex() == 0 && lowestIndex == queues[i]) {
                    continue;

                }
                else if (lowestIndex.LowestIndex() == 0 && lowestIndex != queues[i]) {
                    lowestIndex = queues[i];
                }
                if (queues[i].LowestIndex() < lowestIndex.LowestIndex()) {
                    lowestIndex = queues[i];
                }


            }
        }
            return lowestIndex;
        }

        public boolean QueueEqualityChecker() {
            for (int i = 0; i < queues.length; i++) {
                for (int j = i + 1; j < queues.length; j++) {
                    if (queues[i].LowestIndex() == queues[j].LowestIndex()) {
                        return true;
                    }
                }
            }
            return false;

        }

    public void arrangeAscending(Client[] clientsToSystem) {
        Client lowestId;
        for (int i = 0; i < clientsToSystem.length; i++) {
            for(int j = i; j < clientsToSystem.length; j++) {
            if (clientsToSystem[i].getArrivalTime() == clock) {
                if (clientsToSystem[i].getId() > clientsToSystem[j].getId()) {
                    lowestId = clientsToSystem[i];
                    clientsToSystem[i] = clientsToSystem[j];
                    clientsToSystem[j] = lowestId;


                }

            }

            }

        }

    }
    public Client[] getClientsBeingServed() {
        this.clientsBeingServed = null;
        if (queues != null) {


            for (int i = 0; i < queues.length; i++) {
                if (queues[i] != null) {
                    if (queues[i].getClientBeingServed() != null) {
                        addClientsBeingServed(queues[i].getClientBeingServed());

                    }
                }

            }
        }
        return this.clientsBeingServed;
    }
    public void addClientsBeingServed(Client client) {
        if (clientsBeingServed == null) {
            int supportingItemsLength = 1;

            clientsBeingServed = new Client[supportingItemsLength];

            clientsBeingServed[0] = client;

            this.clientsBeingServed = clientsBeingServed;

            return;

        }



        Client[] tempMembers = new Client[clientsBeingServed.length + 1];


        for(int i=0; i <= clientsBeingServed.length; i++) {

            if (i == clientsBeingServed.length) {

                tempMembers[i] = client;

                break;

            }
            tempMembers[i] = clientsBeingServed[i];
        }
        clientsBeingServed = new Client[tempMembers.length];

        for(int i=0; i < clientsBeingServed.length; i++) {

            clientsBeingServed[i] = tempMembers[i];

        }

        this.clientsBeingServed = clientsBeingServed;

    }
    public static int queueNumReturner(Queue queue) {
        if (queues == null) {
            return 1;
        }
        for (int i = 0; i < queues.length; i++) {
            if (queues[i] == queue) {
                return i;
            }

        }
        return 1;
    }
    public String toString() {
        String rep = "";
        if (queues != null) {
            for (int i = 0; i < queues.length; i++) {
                if (queues[i] != null) {
                   rep += queues[i].queueSystemtoString() + "\n";
                }
            }
        }
        rep += "---" + "\n" + "[WaitingLine]-";
        if (waitingLine != null) {
            for (int i = 0; i < waitingLineSize; i++) {
                if (i >= waitingLine.length) {
                    rep += "[ " + " ]";
                }
                if (waitingLine[i] != null) {
                    if (waitingLine[i].getId() < 10) {
                        rep += "[" + "0" + waitingLine[i].getId() + "]";
                    }
                    else {
                        rep += "[" + waitingLine[i].getId() + "]";

                    }
                }
                else {
                    rep += "[ " + " ]";

                }

            }
            return rep;

        }
        else {
            for (int i = 0; i < waitingLineSize; i++) {
                rep += "[ " + " ]";
            }

        }
        return rep;

    }
    public String toString(boolean showID) {
        String rep = "";
        if (showID == false) {
            if (queues != null) {


                for (int i = 0; i < queues.length; i++) {
                    if (i == 0) {
                        rep = queues[i].QueueSHOWIDtoString() + "\n";
                        continue;
                    }

                    rep += queues[i].QueueSHOWIDtoString() + "\n";

                }
            }
            rep += "---" + "\n" + "[WaitingLine]-";
            if (waitingLine != null) {
                for (int i = 0; i < waitingLineSize; i++) {
                    if (i >= waitingLine.length) {
                        rep += "[ " + " ]";
                    }
                    if (waitingLine[i] != null) {
                        if (waitingLine[i].getId() < 10) {
                            rep += "[" + "0" + waitingLine[i].getServiceTime() + "]";
                        } else {
                            rep += "[" + waitingLine[i].getServiceTime() + "]";

                        }
                    } else {
                        rep += "[ " + " ]";

                    }

                }
                return rep;

            } else {
                for (int i = 0; i < waitingLineSize; i++) {
                    rep += "[ " + " ]";
                }
                return rep;

            }
        }
        else if  (showID == true) {
            return toString();
        }
        return rep;
    }

}
