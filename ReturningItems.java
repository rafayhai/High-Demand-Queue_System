public class ReturningItems extends Request {
    private String[] itemsToReturn;
    private String description;
    private int  priority;

    private int difficulty;
    private int factor;

    private Status status;
    private int startTime;

    private int endTime;

    private int completionLevel;
    private QueueSystem queueSystem;
    private int processingTime;

    public ReturningItems(String description, int priority, int difficulty, String[] itemsToReturn) {
        factor = 3;
        status = Status.NEW;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.itemsToReturn = itemsToReturn;


    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public void setStartTime(int startTime) {
        startTime = queueSystem.getClock();
        this.startTime = startTime;
    }
    public void setEndTime(int endTime) {
        endTime = startTime + this.processingTime;
        this.endTime = endTime;

    }
    public int getStartTime() {
        return this.startTime;
    }
    public int getEndTime() {
        return this.endTime;
    }
    public int calculateProcessingTime() {
        processingTime =  this.difficulty * factor * itemsToReturn.length;
        endTime = processingTime;
        return processingTime;
    }
    public void setCompletionLevel(int completionLevel) {
        this.completionLevel = completionLevel;
    }

    public int getCompletionLevel() {
        return this.completionLevel;
    }

    public String[] getItemsToReturn() {
        return this.itemsToReturn;
    }

    public void setItemsToReturn(String[] itemsToReturn) {
        this.itemsToReturn = itemsToReturn;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return this.priority;
    }

}
