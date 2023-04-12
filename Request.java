public abstract class Request implements Prioritizable {
    private String description;

    private int  priority;

    private int difficulty;

    private int factor;

    private int startTime = -1;

    private int endTime = -1;

    private int completionLevel;

    private Status status;
    public void setStartTime(int start) {
        this.startTime = startTime;

    }
    public void setEndTime(int end) {
        this.endTime = endTime;

    }
    public int getStartTime() {
        return this.startTime;
    }
    public int getEndTime() {
        return this.endTime;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int getFactor() {
        return this.factor;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setCompletionLevel(int completionLevel) {
        this.completionLevel = completionLevel;
    }

    public int getCompletionLevel() {
        return this.completionLevel;
    }


    public int calculateProcessingTime() {
        return 0;
    }




}
