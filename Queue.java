public class Queue {
    private String serverName;

    private int queueSize;
    private Client clientBeingServed;
    private Request requestInProgress;
    private boolean VIPqueueChecker = false;

    private static int timeInQueueIncrementer = 0;

    private Client client;

    private boolean allRequestsProcessed = false;

    private int lowestIndex = 0;
    private int processingStartTime;
    private Client[] clientsHistory;
    private Request[] allRequests;
    private Request currentRequest;
    private String queueRep;
    private QueueSystem queueSystem;

    private int queueNum = 1;
    private static int queueNumIncrease = 0;
    private int queueId;

    private static int requestIndex = 0;
    private Client[] clientsInQueue = null;
    private boolean queueFullChecker = false;

    public Queue(String serverName, int queueSize) {
        setServerName(serverName);
        setQueueSize(queueSize);
        //this.queueNum = queueNumIncrease++;
        this.VIPqueueChecker = VIPqueueChecker;
        //this.queueId = ++queueNumIncrease;


    }
    public boolean getVIPqueueChecker() {
        return this.VIPqueueChecker;
    }
    public boolean getAcceptingAnyClients() {
        return true;
    }

    public Client[] getClientsInQueue() {
        return this.clientsInQueue;
    }

    public int getQueueSize() {
        return this.queueSize;
    }

    public void setProcessingStartTime(int processingStartTime) {
        this.processingStartTime = processingStartTime;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;

    }
    public void processingTimeIncrementer() {
        this.processingStartTime += 1;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;

    }


    public Client getClientBeingServed() {
        return this.clientBeingServed;
    }

    //all clients are added to ClientsInQueue here
    public void addClientsInQueue(Client clientToQueue) {
        if (clientsInQueue == null) {
            clientsInQueue = new Client[1];
            clientsInQueue[0] = clientToQueue;
            this.clientsInQueue = clientsInQueue;
            if (clientsInQueue.length == queueSize) {
                queueFullChecker(true);
            }
            return;
        } else if (clientsInQueue[0] == null && queueSize == 1) {
            clientsInQueue[0] = clientToQueue;
        }
        if (clientsInQueue.length < queueSize) {


            Client[] tempWaitingLine = new Client[clientsInQueue.length + 1];


            for (int i = 0; i <= clientsInQueue.length; i++) {

                if (i == clientsInQueue.length) {

                    tempWaitingLine[i] = clientToQueue;

                    break;

                }
                tempWaitingLine[i] = clientsInQueue[i];
            }
            clientsInQueue = new Client[tempWaitingLine.length];

            for (int i = 0; i < clientsInQueue.length; i++) {

                clientsInQueue[i] = tempWaitingLine[i];

            }

            this.clientsInQueue = clientsInQueue;
            if (clientsInQueue.length < queueSize) {
                queueFullChecker(false);
            }
            else {
                queueFullChecker(true);
            }
        } else {
            queueFullChecker(true);

        }


    }
    public boolean QueueLoopFullChecker() {
        for (int i = 0; i < clientsInQueue.length; i++) {
            if (clientsInQueue[i] == null) {
                return false;
            }
        }
        return true;
    }

    public int LowestIndex() {
        if (clientsInQueue != null) {
            this.lowestIndex = queueSize - clientsInQueue.length;
        }
        else {
            return 0;
        }

        return this.lowestIndex;
    }


    public String getServerName() {
        return this.serverName;
    }

    //passes in the client to be served from queuesystem
    public void ClientToBeServed(Client client) {
        this.clientBeingServed = client;
        this.clientBeingServed.setServer(this.serverName);
        this.allRequestsProcessed = false;
    }
    public void setClientBeingServed(Client clientBeingServed) {
        this.clientBeingServed = clientBeingServed;
    }
    public void setRequestInProgress(Request requestInProgress) {
        this.requestInProgress = requestInProgress;
    }

    public Request getRequestInProgress() {
        return this.requestInProgress;
    }
    public int getProcessingStartTime() {
        return this.processingStartTime;
    }
    public void setClientsHistory(Client[] clientsHistory) {
        this.clientsHistory = clientsHistory;
    }
    public void setClientsInQueue() {
        this.clientsInQueue = clientsInQueue;
    }
    public void RequestProcessing() {
        allRequests = this.clientBeingServed.getRequests();
        if (requestIndex < allRequests.length) {
            this.currentRequest = allRequests[requestIndex];
        } else if (requestIndex >= allRequests.length) {
            this.clientBeingServed.ClientServedChecker(true);
            setAllRequestsProcessed(true);
            int clockValue = queueSystem.getClock();
            this.clientBeingServed.setDepartureTime(clockValue);
            AddclientsInHistory(this.clientBeingServed);
            this.clientBeingServed = null;
            this.currentRequest = null;
            requestIndex = 0;
        }

    }

    public void AddclientsInHistory(Client client) {
        if (clientsHistory == null) {
            clientsHistory = new Client[1];
            clientsHistory[0] = client;
            this.clientsHistory = clientsHistory;
            return;
        }
        Client[] tempclientsHistory = new Client[clientsHistory.length + 1];
        for (int i = 0; i <= clientsHistory.length; i++) {
            if (i == clientsHistory.length) {
                tempclientsHistory[i] = client;
                break;
            }
            tempclientsHistory[i] = clientsHistory[i];
        }
        clientsHistory = new Client[tempclientsHistory.length];
        for (int i = 0; i < clientsHistory.length; i++) {
            clientsHistory[i] = tempclientsHistory[i];
        }
        this.clientsHistory = clientsHistory;
    }

    public Client[] getClientsHistory() {
        return this.clientsHistory;
    }

    public void setAllRequestsProcessed(boolean value) {
        this.allRequestsProcessed = value;
    }

    public boolean allRequestsProcessed() {
        return this.allRequestsProcessed;
    }

    public Request getCurrentRequest() {
        return this.currentRequest;

    }

    public void requestIndexIncrease() {
        requestIndex++;
        RequestProcessing();
    }

    public boolean queueFullChecker(boolean a) {
        this.queueFullChecker = a;
        return a;
    }

    public boolean getQueueFullChecker() {
        return this.queueFullChecker;
    }

    //method will be used in QueueSystem in requestProcessing when the server is available and queue is full
    public void moveQueueToServer() {
        if (clientsInQueue == null) {
            return;
        }
        boolean ClientMovedToServer = false;
        for (int i = 0; i < clientsInQueue.length; i++) {
            if (clientsInQueue[0] != null) {
                ClientToBeServed(clientsInQueue[0]);
                ClientMovedToServer = true;
                clientsInQueue[0] = null;
                if (QueueLoopFullChecker() == false) {
                    queueFullChecker(false);

                }
                else if (QueueLoopFullChecker() == true) {
                    queueFullChecker(true);
                }

            }
        }
        if (queueSize > 1 && clientsInQueue[0] == null) {
            QueueResize();
        }
        if (ClientMovedToServer = true) {
            return;
        }
        return;
    }

    //after client moves to server from queue, queue is resized
    public void QueueResize() {
        int counter = 0;
        for (int i = 0; i < clientsInQueue.length; i++) {
            if (clientsInQueue[i] != null) {
                counter++;
            }

        }
        if (counter == 0) {
            return;
        }
        Client[] tempClientsInQueue = new Client[counter];
        int index = 0;
        for (int i = 0; i < clientsInQueue.length; i++) {
            if (clientsInQueue[i] != null) {
                tempClientsInQueue[index] = clientsInQueue[i];
                index++;
            }


        }
        clientsInQueue = tempClientsInQueue;
    }

    public void attributeUpdater() {
        if (clientsInQueue == null) {
            return;
        }
        for (int i = 0; i < clientsInQueue.length; i++) {
            if (clientsInQueue[i] != null) {
                clientsInQueue[i].TimeInQueueIncreaser();
            }
        }
    }

    public String toString() {
        String queueReps = "a";
        String CurrentqueueReps = " ";
        if (this.clientBeingServed != null) {
            if (this.clientBeingServed.getId() < 10) {
                queueReps = "[Queue:" + this.queueNum + "][" + "0" + this.clientBeingServed.getId() + "]-----";

            } else {
                queueReps = "[Queue:" + this.queueNum + "][" + this.clientBeingServed.getId() + "]-----";
            }
            if (clientsInQueue != null) {
                for (int i = 0; i <= this.queueSize - 1; i++) {
                    if (i >= clientsInQueue.length) {
                        queueReps += "[ " + " ]";
                        continue;
                    }
                    if (clientsInQueue[i] == null) {
                        queueReps += "[ " + " ]";
                        continue;
                    }
                    if (clientsInQueue[i].getId() < 10) {
                        queueReps += "[" + "0" + clientsInQueue[i].getId() + "]";
                        ;

                    } else {
                        queueReps += "[" + clientsInQueue[i].getId() + "]";
                    }


                }
                return queueReps;
            } else {
                for (int i = 0; i < queueSize; i++) {
                    queueReps += "[ " + " ]";

                }
                return queueReps;
            }
        } else {
            CurrentqueueReps = "[Queue:" + this.queueNum + "][ " + " ]-----";
            for (int i = 0; i <= this.queueSize - 1; i++) {

                CurrentqueueReps += "[ " + " ]";

            }
        }
        return CurrentqueueReps;
    }



    public String toString(boolean showID) {
        if (this.clientBeingServed != null) {
            int remainingTime = this.clientBeingServed.getServiceTime() - this.processingStartTime;
            int remainingTimeOthers = 0;
            String CurrentqueueReps = "";
            if (remainingTime > 0) {
                CurrentqueueReps = "[Queue:" + this.queueNum + "][" + "0" + remainingTime + "]-----";
            }
            else {
                CurrentqueueReps = "[Queue:" + this.queueNum + "][" + remainingTime + "]-----";
            }
            if (showID == false) {
                if (clientsInQueue != null ) {
                    for (int i = 0; i <= this.queueSize - 1; i++) {
                        if (i >= clientsInQueue.length) {
                            CurrentqueueReps += "[ " + " ]";
                            continue;
                        }
                        if (clientsInQueue[i] == null) {
                            CurrentqueueReps += "[ " + " ]";
                            continue;
                        }
                        remainingTimeOthers = clientsInQueue[i].getServiceTime();
                        if (remainingTimeOthers < 10) {
                            CurrentqueueReps += "[" + "0" + remainingTimeOthers + "]";
                        }
                        else {
                            CurrentqueueReps += "[" + remainingTimeOthers + "]";
                        }


                    }

                    return CurrentqueueReps;
                }
                else  {
                    //fix
                    if (this.clientBeingServed.getServiceTime() < 10) {
                        remainingTimeOthers = this.clientBeingServed.getServiceTime() - this.processingStartTime;
                        CurrentqueueReps = "[Queue:" + this.queueNum + "][" + "0" + remainingTimeOthers + "]-----";
                                for (int i = 0; i < queueSize; i++) {
                                    CurrentqueueReps += "[ " + " ]";
                                }
                        return CurrentqueueReps;
                    }
                    else {
                        remainingTimeOthers = this.clientBeingServed.getServiceTime();
                        CurrentqueueReps = "[Queue:" + queueNum + "][" + remainingTimeOthers + "]-----";
                        for (int i = 0; i < queueSize; i++) {
                            CurrentqueueReps += "[ " + " ]";
                        }

                        return CurrentqueueReps;

                    }
                }
            }
            else {
                return toString();
            }


        }
        else {
            String CurrentqueueReps = "[Queue:" + queueNum + "][ " +  " ]-----";
            for (int i = 0; i <= this.queueSize - 1; i++) {

                CurrentqueueReps += "[ " + " ]";

            }
            return CurrentqueueReps;
        }
    }
    public String queueSystemtoString() {
        this.queueNum = QueueSystem.queueNumReturner(this) + 1;
        String queueReps = "a";
        String CurrentqueueReps = " ";
        if (this.clientBeingServed != null) {
            if (this.clientBeingServed.getId() < 10) {
                queueReps = "[Queue:" + this.queueNum + "][" + "0" + this.clientBeingServed.getId() + "]-----";

            } else {
                queueReps = "[Queue:" + this.queueNum + "][" + this.clientBeingServed.getId() + "]-----";
            }
            if (clientsInQueue != null) {
                for (int i = 0; i <= this.queueSize - 1; i++) {
                    if (i >= clientsInQueue.length) {
                        queueReps += "[ " + " ]";
                        continue;
                    }
                    if (clientsInQueue[i] == null) {
                        queueReps += "[ " + " ]";
                        continue;
                    }
                    if (clientsInQueue[i].getId() < 10) {
                        queueReps += "[" + "0" + clientsInQueue[i].getId() + "]";
                        ;

                    } else {
                        queueReps += "[" + clientsInQueue[i].getId() + "]";
                    }


                }
                return queueReps;
            } else {
                for (int i = 0; i < queueSize; i++) {
                    queueReps += "[ " + " ]";

                }
                return queueReps;
            }
        } else {
            CurrentqueueReps = "[Queue:" + this.queueNum + "][ " + " ]-----";
            for (int i = 0; i <= this.queueSize - 1; i++) {

                CurrentqueueReps += "[ " + " ]";

            }
        }
        return CurrentqueueReps;
    }
    public String QueueSHOWIDtoString() {
        this.queueNum = QueueSystem.queueNumReturner(this) + 1;
        if (this.clientBeingServed != null) {
            int remainingTime = this.clientBeingServed.getServiceTime() - this.processingStartTime;
            int remainingTimeOthers = 0;
            String CurrentqueueReps = "";
            if (remainingTime > 0) {
                CurrentqueueReps = "[Queue:" + this.queueNum + "][" + "0" + remainingTime + "]-----";
            }
            else {
                CurrentqueueReps = "[Queue:" + this.queueNum + "][" + remainingTime + "]-----";
            }
            if (clientsInQueue != null ) {
                for (int i = 0; i <= this.queueSize - 1; i++) {
                    if (i >= clientsInQueue.length) {
                        CurrentqueueReps += "[ " + " ]";
                        continue;
                    }
                    if (clientsInQueue[i] == null) {
                        CurrentqueueReps += "[ " + " ]";
                        continue;
                    }
                    remainingTimeOthers = clientsInQueue[i].getServiceTime();
                    if (remainingTimeOthers < 10) {
                        CurrentqueueReps += "[" + "0" + remainingTimeOthers + "]";
                    }
                    else {
                        CurrentqueueReps += "[" + remainingTimeOthers + "]";
                    }


                }

                return CurrentqueueReps;
            }
            else  {

                if (this.clientBeingServed.getServiceTime() < 10) {
                    remainingTimeOthers = this.clientBeingServed.getServiceTime() - this.processingStartTime;
                    CurrentqueueReps = "[Queue:" + this.queueId + "][" + "0" + remainingTimeOthers + "]-----";
                    for (int i = 0; i < queueSize; i++) {
                        CurrentqueueReps += "[ " + " ]";
                    }
                        return CurrentqueueReps;
                    }
                else {
                    remainingTimeOthers = this.clientBeingServed.getServiceTime();
                    CurrentqueueReps = "[Queue:" + this.queueNum + "][" + remainingTimeOthers + "]-----";
                    for (int i = 0; i < queueSize; i++) {
                        CurrentqueueReps += "[ " + " ]";
                    }

                    return CurrentqueueReps;

                    }
                }


        }
        else {
            String CurrentqueueReps = "[Queue:" + this.queueId + "][ " +  " ]-----";
            for (int i = 0; i <= this.queueSize - 1; i++) {

                CurrentqueueReps += "[ " + " ]";

            }
            return CurrentqueueReps;
        }
    }

}
