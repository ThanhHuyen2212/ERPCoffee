package Entity;

public class Member {
    private String phoneNumber;
    private String fullName;
    private int point;

    public Member() {
    }

    public Member(String phoneNumber, String fullName, int point) {
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.point = point;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
