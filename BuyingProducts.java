public class BuyingProducts extends Request {
    private String[] itemsToBuy;
    private String description;
    private int  priority;

    private int difficulty;
    private int factor;
    private int startTime;

    private int endTime;

    private int completionLevel;
    private QueueSystem queueSystem;
    private int processingTime;

    private Status status;
    public BuyingProducts(String description, int priority, int difficulty, String[] itemsToBuy) {
        factor = 2;
        status = Status.NEW;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.itemsToBuy = itemsToBuy;
    }

    public String[] getItemsToBuy() {
        return this.itemsToBuy;
    }

    public void setItemsToBuy(String[] itemsToBuy) {
        this.itemsToBuy = itemsToBuy;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public void setStartTime(int startTime) {
        startTime = queueSystem.getClock();
        this.startTime = startTime;
    }
    public void setCompletionLevel(int completionLevel) {
        this.completionLevel = completionLevel;
    }

    public int getCompletionLevel() {
        return this.completionLevel;
    }
    public void setEndTime(int endTime) {
        endTime = this.startTime + calculateProcessingTime();
        this.endTime = endTime;

    }
    public int getStartTime() {
        return this.startTime;
    }
    public int getEndTime() {
        return this.endTime;
    }
    public int calculateProcessingTime() {
        this.processingTime = this.difficulty * this.factor * this.itemsToBuy.length;
        return processingTime;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return this.priority;
    }
}
