public class VIPQueue extends Queue {
    private boolean acceptingAnyClients;

    private boolean VIPqueueChecker = true;


    public boolean getVIPqueueChecker() {
        return this.VIPqueueChecker;
    }

    public void setAcceptingAnyClients(boolean value) {
        this.acceptingAnyClients = value;

    }
    public boolean getAcceptingAnyClients() {
        return this.acceptingAnyClients;
    }
    public VIPQueue(String serverName, int queueSize, boolean acceptAnyClients) {
        super(serverName, queueSize);
        setAcceptingAnyClients(acceptAnyClients);
        this.VIPqueueChecker = VIPqueueChecker;


    }
    public VIPQueue(String serverName, int queueSize) {
        super(serverName, queueSize);
    }
}
