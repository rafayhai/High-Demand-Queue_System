public class InformationRequest extends Request {
    private String[] questions;

    private QueueSystem queueSystem;

    private int factor;

    private Status status;

    private String description;
    private int  priority;

    private int difficulty;
    private int startTime = -1;

    private int endTime = -1;

    private int completionLevel;

    private int processingTime;


    public InformationRequest(String description, int priority, int difficulty, String[] questions) {
        factor = 1;
        status = Status.NEW;
        this.description = description;
        setPriority(priority);
        this.difficulty = difficulty;
        this.questions = questions;
        //setEndTime(endTime);

    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public String[] getQuestions() {
        return this.questions;
    }

    public void setCompletionLevel(int completionLevel) {
        this.completionLevel = completionLevel;
    }

    public int getCompletionLevel() {
        return this.completionLevel;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
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
        this.processingTime = this.difficulty * this.factor * this.questions.length;
        return processingTime;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return this.priority;
    }
}
