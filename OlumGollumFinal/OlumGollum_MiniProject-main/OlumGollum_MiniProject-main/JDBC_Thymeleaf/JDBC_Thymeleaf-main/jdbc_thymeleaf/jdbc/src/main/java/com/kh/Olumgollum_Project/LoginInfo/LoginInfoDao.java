package com.kh.Olumgollum_Project.LoginInfo;


import com.kh.Olumgollum_Project.COMMON.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoginInfoDao {
    Scanner sc = new Scanner(System.in);

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pStmt = null;
    ResultSet rs = null;
    String ID = null;

    public static final String black = "\u001B[30m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36;m";
    public static final String white = "\u001B[37m";
    public static final String exit = "\u001B[0m";

    public List<LoginInfoVo> logsel() {
        List<LoginInfoVo> list = new ArrayList<>();
        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT * FROM INFO";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String user_mail = rs.getString("USER_MAIL");
                String password = rs.getString("PASSWORD");
                String name = rs.getString("NAME");
                int first_id_cardnum = rs.getInt("FIRST_ID_CARDNUM");
                int phone_number = rs.getInt("PHONE_NUMBER");
                String address = rs.getString("ADDRESS");
                int gender = rs.getInt("GENDER");
                String nickname = rs.getString("NICKNAME");
                list.add(new LoginInfoVo(user_mail, password, name, first_id_cardnum, phone_number, address, gender, nickname));
            }
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<String> login(LoginVo User_id_pw) throws InterruptedException {
        List<String> id_pw = new ArrayList<>();
        String sql = "SELECT PASSWORD FROM INFO WHERE USER_MAIL= ? ";
        String pwd;
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, ID);
            rs = pStmt.executeQuery(); // rs 객체에 쿼리 결과를 할당

            if (rs.next()) {
                pwd = rs.getString("PASSWORD");
                if (pwd.equals(User_id_pw.getUserid())) {
                    System.out.println(green + "                         로그인에 성공하셨습니다!" + exit);
                    id_pw.add(ID);
                    id_pw.add(pwd);
                    id_pw.add("User");
                    return id_pw;
                } else {
                    System.out.println("  비밀번호가 틀렸습니다.");
                }
            } else {
                System.out.println("  아이디를 잘못 입력했습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        }
        return id_pw;
    }

    public List<String> logInsert() {
        List<String> id_pw = new ArrayList<>();
        System.out.print(blue + "|" + exit + "          " + green + "아이디 입력" + exit + "         " + blue + "|     " + exit);
        String user_mail = sc.next();
        id_pw.add(user_mail);
        System.out.println(blue + "========================================================================" + exit);
        System.out.print(blue + "|" + exit + "         " + green + "패스워드 입력" + exit + "         " + blue + "|     " + exit);
        String password = sc.next();
        id_pw.add(password);
        System.out.println(blue + "========================================================================" + exit);
        System.out.print(blue + "|" + exit + "          " + green + "이름 입력" + exit + "           " + blue + "|     " + exit);
        String name = sc.next();
        System.out.println(blue + "========================================================================" + exit);
        System.out.print(blue + "|" + exit + "      " + green + "주민번호 앞자리 입력" + exit + "      " + blue + "|     " + exit);
        int first_id_cardnum = sc.nextInt();
        System.out.println(blue + "========================================================================" + exit);
        System.out.print(blue + "|" + exit + "      " + green + "전화번호 입력(-빼고)" + exit + "     " + blue + "|     " + exit);
        int phone_number = sc.nextInt();
        sc.nextLine();
        System.out.println(blue + "========================================================================" + exit);
        System.out.print(blue + "|" + exit + "          " + green + "주소 입력" + exit + "           " + blue + "|     " + exit);
        String address = sc.nextLine();
        System.out.println(blue + "========================================================================" + exit);
        System.out.print(blue + "|" + exit + "     " + green + "성별입력([1]남[2]여)" + exit + "     " + blue + "|     " + exit);
        int gender = sc.nextInt();
        sc.nextLine();
        System.out.println(blue + "========================================================================" + exit);
        System.out.print(blue + "|" + exit + "         " + green + "닉네임 입력" + exit + "          " + blue + "|     " + exit);
        String nickname = sc.next();
        System.out.println(blue + "========================================================================" + exit);
        String sql = "INSERT INTO INFO(USER_MAIL, PASSWORD, NAME, FIRST_ID_CARDNUM, PHONE_NUMBER, ADDRESS, GENDER, NICKNAME) VALUES (?,?,?,?,?,?,?,?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, user_mail);
            pStmt.setString(2, password);
            pStmt.setString(3, name);
            pStmt.setInt(4, first_id_cardnum);
            pStmt.setInt(5, phone_number);
            pStmt.setString(6, address);
            pStmt.setInt(7, gender);
            pStmt.setString(8, nickname);
            int rst = pStmt.executeUpdate();
//            System.out.println("rst : " + rst);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
        id_pw.add("User");
        return id_pw;
    }

    public void logSelectPrn(List<LoginInfoVo> list) {
        for (LoginInfoVo e : list) {
            System.out.print(e.getUser_mail() + " ");
            System.out.print(e.getPassword() + " ");
            System.out.print(e.getName() + " ");
            System.out.print(e.getFirst_id_cardnum() + " ");
            System.out.print(e.getPhone_number() + " ");
            System.out.print(e.getAddress() + " ");
            System.out.print(e.getGender() + " ");
            System.out.println(e.getNickname());
            System.out.println(blue + "========================================================================" + exit);
        }
        System.out.println();
    }
    // Thymeleaf 로그인 동작을 위한 메소드 제작.
    public boolean AdminLoginfunc(LoginVo vo){
        boolean check = false;

        try {
            String query = "SELECT * FROM ADMIN_INFO WHERE ADM_ID = ? AND ADM_PW = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(query);
            pStmt.setString(1, vo.getUserid());
            pStmt.setString(2, vo.getPassword());
            rs = pStmt.executeQuery();
            if (rs.next()) {
                check =  true;
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }
    public boolean UserLoginfunc(LoginVo vo){
        boolean check = false;

        try {
            String query = "SELECT * FROM INFO WHERE USER_MAIL = ? AND PASSWORD = ?";
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(query);
            pStmt.setString(1, vo.getUserid());
            pStmt.setString(2, vo.getPassword());
            rs = pStmt.executeQuery();
            if (rs.next()) {
                check =  true;
            }
            Common.close(rs);
            Common.close(pStmt);
            Common.close(conn);
        }catch (Exception e){
            e.printStackTrace();
        }
        return check;
    }
    public void UserInsert(LoginInfoVo LIV) {
        List<String> id_pw = new ArrayList<>();
        String sql = "INSERT INTO INFO(USER_MAIL, PASSWORD, NAME, FIRST_ID_CARDNUM, PHONE_NUMBER, ADDRESS, GENDER, NICKNAME) VALUES (?,?,?,?,?,?,?,?)";
        try {
            conn = Common.getConnection();
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, LIV.getUser_mail());
            pStmt.setString(2, LIV.getPassword());
            pStmt.setString(3, LIV.getName());
            pStmt.setInt(4, LIV.getFirst_id_cardnum());
            pStmt.setInt(5, LIV.getPhone_number());
            pStmt.setString(6, LIV.getAddress());
            pStmt.setInt(7, LIV.getGender());
            pStmt.setString(8, LIV.getNickname());
            pStmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Common.close(pStmt);
        Common.close(conn);
    }
}


