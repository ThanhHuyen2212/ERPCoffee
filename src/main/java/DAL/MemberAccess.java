package DAL;

import Entity.Category;
import Entity.Member;
import Logic.MemberManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberAccess extends DataAccess {
    public static ArrayList<Member> retrieve(){
        MemberAccess memberAccess =new MemberAccess();
        ArrayList<Member> members = new ArrayList<>();
        try {
            memberAccess.createConnection();
            PreparedStatement prSt = memberAccess.getConn().prepareStatement("call select_members()");
            ResultSet rs = prSt.executeQuery();
            while (rs!=null && rs.next()){
                members.add(new Member(rs.getString(1),rs.getString(2),rs.getInt(3)));
            }
        } catch (SQLException e) {
            System.out.println("MemberAccess");
            System.out.println(e.getMessage());
        }
        return members;
    }
    public void createMember(Member member){
        MemberAccess memberAccess =new MemberAccess();
        memberAccess.createConnection();
        try {
            PreparedStatement prSt=memberAccess.getConn().prepareStatement("call insert_members(?,?);");
            prSt.setString(1,member.getPhoneNumber());
            prSt.setString(2,member.getFullName());
            prSt.executeQuery();
        } catch (SQLException e) {
            System.out.println("MemberAccess");
            System.out.println(e.getMessage());
        }
    }

}
