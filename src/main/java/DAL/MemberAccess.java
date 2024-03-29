package DAL;

import Entity.Category;
import Entity.Member;
import Logic.MemberManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberAccess extends DataAccess {

    public  ArrayList<Member> retrieve(){
        ArrayList<Member> members = new ArrayList<>();
        try {
            PreparedStatement prSt = getConn().prepareStatement("call select_members()");
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
        try {
            PreparedStatement prSt=getConn().prepareStatement("call insert_members(?,?);");
            prSt.setString(1,member.getPhoneNumber());
            prSt.setString(2,member.getFullName());
            prSt.executeQuery();

        } catch (SQLException e) {
            System.out.println("MemberAccess");
            System.out.println(e.getMessage());
        }
    }
    public void updateMember(Member member){
        createConnection();
        try {
            PreparedStatement prSt=getConn().prepareStatement("call update_members(?, ?, ?)");
            prSt.setString(1,member.getPhoneNumber());
            prSt.setString(2,member.getFullName());
            prSt.setInt(3,member.getPoint());
            ResultSet rs = prSt.executeQuery();
            System.out.println("Cap nhat member");

        } catch (SQLException e) {
            System.out.println("MemberAccess(Update)");
            System.out.println(e.getMessage());
        }

    }
    public Member findByPhone(String phone){
        Member newMember = new Member();
        try {
            PreparedStatement prSt =getConn().prepareStatement("call select_members_with_phone(?)");
            prSt.setString(1, phone);
            ResultSet rs = prSt.executeQuery();
            while (rs!=null && rs.next()){
                newMember.setPhoneNumber(rs.getString(1));
                newMember.setFullName(rs.getString(2));
                newMember.setPoint(rs.getInt(3));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    return  newMember;
    }
    public void UpdateSubPoint(Member member,Integer point){
        try {
            PreparedStatement prSt = getConn().prepareStatement("call update_sub_point_member(?, ?)");
            prSt.setString(1, member.getPhoneNumber());
            prSt.setInt(2,point);
            prSt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
