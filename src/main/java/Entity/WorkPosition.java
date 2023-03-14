package Entity;

public class WorkPosition {
    private int positionId;
    private String name;
    private int positionLvl;

    public WorkPosition() {
    }

    public WorkPosition(int positionId, String name, int positionLvl) {
        this.positionId = positionId;
        this.name = name;
        this.positionLvl = positionLvl;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositionLvl() {
        return positionLvl;
    }

    public void setPositionLvl(int positionLvl) {
        this.positionLvl = positionLvl;
    }
}
