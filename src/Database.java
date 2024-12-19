import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Saksham
 */
public class Database {

    //Global Arrays:
    private static final String[] list = {"AMS","BAH","PEK","BNE","CAI", "CPT","DMM","DEL","DXB","DUR","FRA","JNB",
            "LAX","LHR","MRU","MED","MEX","MUC","MCT","JFK","CDG","SCL","GRU","PVG","SIN"};
    private static final String[] names = {"Amsterdam"			,            "Bahrain"   		,
            "Beijing"   	 	,            "Brisbane"  	 	,            "Cairo"		,
            "Cape Town"    		,            "Dammam"    		,            "Delhi"   	 	,
            "Dubai"    			,            "Durban"    		,            "Frankfurt"    	,
            "Johannesburg"              ,            "Los Angeles"      	,            "London"    	,
            "Mauritius"    		,            "Medina"   	 	,            "Mexico City"    	,
            "Munich"    		,            "Muscat"    		,            "New York"    	,
            "Paris"    			,            "Santiago"    		,            "São Paulo"    	,
            "Shanghai"    		,            "Singapore"};

    public static boolean checkDetails(String email){
        boolean res = true;
        email = email.trim();
        email = email.toLowerCase();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from userdetails where email ='" + email + "';");
            if (!resultSet.isBeforeFirst()) {
                res = false;
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return res;
    }

    public static int getRowCount() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from userdata");
            rs.last();
            int val = rs.getInt(1);
            con.close();
            return val;
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static boolean userExists(String email) {
        email = email.trim();
        email = email.toLowerCase();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from userdata where email ='" + email + "';");
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return true;
    }

    static String[] getUserDetails(String email) {
        email = email.trim();
        email = email.toLowerCase();
        String[] res = new String[4];

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from userdetails where email ='" + email + "';");
            while(rs.next()) {
                //Order is: IconCode, Name, DateofBirth, Mobile Number
                String iconCode = Integer.toString(rs.getInt(7));
                res[0] = iconCode;                              //Icon
                res[1] = rs.getString(1);             //Name
                Date date = rs.getDate(4);
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String strDate= formatter.format(date);
                res[2] = strDate;                               //Date
                res[3] = rs.getString(6);             //Number
//                res[4] = rs.getString(3);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return res;
    }

    public static char getGender(String email) {

        char gender;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select gender from userdetails where email ='" + email + "';");
            while(rs.next()) {
                String iconCode = rs.getString(1);
                gender = iconCode.charAt(0);
                return gender;
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return ' ';

    }

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getPasscode(String email) {
        String pass = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Password from userdata where email ='" + email + "';");
            while(rs.next()) {
                pass = rs.getString(1);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return pass;
    }

    public static void updatePasscode(String email, String pass1) {
        String pass = getMd5(pass1);
        //Updating in Database:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE userdata SET password ='" + pass + "' WHERE Email = '" + email + "';");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean startVerification(String email, String number) {

//        //Getting number from TextField.
//        String number = NumberField.getText();
        number = number.trim();

        //Getting country code from Country ComboBox.
        String str = Database.getCountry(email);
        int index = str.indexOf("(");
        String country_code = str.substring(index+1, index+3);

        String access_key = "cf9fb051b51ac25f4971abccb34b2935";             //Your apikey key
        String format = "1";

        URLConnection myURLConnection;
        URL myURL;
        BufferedReader reader;

        String mainUrl="http://apilayer.net/api/validate?";

        //API Paramters
        StringBuilder sendSmsData= new StringBuilder(mainUrl);
        sendSmsData.append("access_key="+access_key);
        sendSmsData.append("&number="+number);
        sendSmsData.append("&country_code="+country_code);
        sendSmsData.append("&format="+format);

        //final string
        mainUrl = sendSmsData.toString();
        boolean result = false;
        try
        {
            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                if(response.contains("valid")){
                    String ans = response.substring(10);
                    if(ans.charAt(0) == 't'){
                        result = true;
                    }
                }

            //finally close connection
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static String getCountry(String email) {

        String country = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select country from userdetails where email ='" + email + "';");
            while(rs.next()) {
                country = rs.getString(1);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        int index = country.indexOf("(");
        String country_code = country.substring(index+1, index+3);
        return country;

    }

    public static boolean checkInternet()
    {
        //Method 1:
//        Process process = java.lang.Runtime.getRuntime().exec("ping www.google.com");
//        int x = process.waitFor();
//        if (x == 0) {
//            return true;
//        }
//        else {
//            return false;
//        }

        //Method 2:
        try {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

    public static void updateNumber(String email, String number) {
        number = number.trim();
        //Updating in Database:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE userdetails SET number ='" + number + "' WHERE Email = '" + email + "';");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateCountry(String email, String country) {

        //Updating in Database:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE userdetails SET Country ='" + country + "' WHERE Email = '" + email + "';");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void updateDate(String email, String newDate) throws ParseException {
        java.util.Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(newDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate= formatter.format(date1);
        //Updating in Database:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE userdetails SET DateofBirth ='" + strDate + "' WHERE Email = '" + email + "';");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

        public static int getOTP(){
            Random rand = new Random();
            int code = rand.nextInt(899999)+100000;
            return code;
        }

    public static double getX(String code) {
        double value = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select coordinateX from airport_list where code ='" + code + "';");
            while(rs.next()) {
                value = rs.getDouble(1);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return value;
    }

    public static double getY(String code) {

        double value = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select coordinateY from airport_list where code ='" + code + "';");
            while(rs.next()) {
                value = rs.getDouble(1);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return value;
    }

    public static double getTime(String from, String to, boolean connecting){
        double time = 0;

        if(connecting){

            if(!(from.equals("DXB") || to.equals("DXB"))) {

                double d1 = distance(from, "DXB");
                double d2 = distance("DXB", to);
                double time1, time2;

                if(d1 > 10000){
                    time1 = d1/810.0;
                }else{
                    time1 = d1/625.0;
                }

                if(d2 > 10000){
                    time2 = d2/810.0;
                }else{
                    time2 = d2/625.0;
                }
                time = time1 + time2;

            }

        }else{

            double d = distance(from, to);
            if(d > 10000){
                time = d/810.0;
            }else{
                time = d/625.0;
            }
        }

        return time;
    }

    private static double distance(String fromCode, String toCode){
        double fromX = getX(fromCode);
        double fromY = getY(fromCode);
        double toX = getX(toCode);
        double toY = getY(toCode);

        return distanceN(fromY, toY, fromX, toX);
    }

    public static double distanceN(double lat1, double lat2, double lon1, double lon2){

        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;
        return(c * r);

    }

//    0e0d729919f20893a13d562502703cea
//    aeb51233051c581f2ff33aba2dafe107

    public static boolean getConnectingStatus(String flightCode) {
        
        boolean value = true; 
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select connecting from flights where fligthcode ='" + flightCode + "';");
            while(rs.next()) {
                value = rs.getBoolean(1);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        return value;
        
    }

    public static String[] getFlightDetails(String flightCode) {
        
        // Order is: [from, to, dDate, dTime, dAirport, dCity, dcountry, aDate, aTime, aAirport, aCity, acountry]
        String[] res = new String[12];
        String[] result;
        int index;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT fromm, too, departuredate, arrivaldate FROM flights where fligthcode ='" + flightCode + "';");
            while(rs.next()) {
                res[0] = rs.getString(1);
                res[1] = rs.getString(2);

                //Departure Date and Time:
                SimpleDateFormat s = new SimpleDateFormat("dd.MM.yyyy");
                res[2] = s.format(rs.getDate(3));
                res[3] = rs.getString(3).substring(11, 16);

                //Departure Airport Details:
                result = getAirportDetails(res[0]);
                res[4] = result[0];
                res[5] = result[1];
                res[6] = result[2];

                //Arrival Date and Time:
                res[7] = s.format(rs.getDate(4));
                res[8] = rs.getString(4).substring(11, 16);

                //Departure Airport Details:
                result = getAirportDetails(res[1]);
                res[9] = result[0];
                res[10] = result[1];
                res[11] = result[2];
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        
        return res;
        
    }

    private static String[] getAirportDetails(String code) {

        String[] result = new String[3];
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, city, country FROM airport_list WHERE code = '" + code + "';");
            while(rs.next()) {
                result[0] = rs.getString(1);
                result[1] = rs.getString(2);
                result[2] = rs.getString(3);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return result;

    }

    public static String getBookingNumber() {

        int val = 1;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT bookingNumber FROM bookings ORDER BY bookingNumber DESC;");
            if(rs.next()){
                val = rs.getInt(1);
                val++;
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return Integer.toString(val);

    }

    public static void bookFlight(String[] bookingDetails) {

        //Order is: BookingNumber, Reference, Name, Email, FlightCode, DepDate,
        //              Passengers, Adults, Kids, Class, Meal, Preference, Cancellation, Amount
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt = (Statement) con.createStatement();
            String query = "INSERT INTO bookings VALUES( "
                    + Integer.parseInt(bookingDetails[0]) + ", '"
                    + bookingDetails[1] + "', '"
                    + bookingDetails[2] + "', '"
                    + bookingDetails[3] + "', '"
                    + bookingDetails[4] + "', '"
                    + bookingDetails[5] + "', "
                    + Integer.parseInt(bookingDetails[6]) + ", "
                    + Integer.parseInt(bookingDetails[7]) + ", "
                    + Integer.parseInt(bookingDetails[8]) + ", '"
                    + bookingDetails[9]  + "', '"
                    + bookingDetails[10] + "', '"
                    + bookingDetails[11] + "', '"
                    + bookingDetails[12] + "', "
                    + Integer.parseInt(bookingDetails[13]) + " );";
            stmt.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void bookReturnFlight(String[] bookingDetails1, String[] bookingDetails2) {

        //Order is: BookingNumber, Reference, Name, Email, FlightCode, DepDate,
        //              Passengers, Adults, Kids, Class, Meal, Preference, Cancellation, Amount
        for (int i = 0; i < 2 ; i++) {
            String[] bookingDetails;
            if(i == 0){
                bookingDetails = bookingDetails1.clone();
            }else{
                bookingDetails = bookingDetails2.clone();
            }

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
                Statement stmt = (Statement) con.createStatement();
                String query = "INSERT INTO bookings VALUES( "
                        + Integer.parseInt(bookingDetails[0]) + ", '"
                        + bookingDetails[1] + "', '"
                        + bookingDetails[2] + "', '"
                        + bookingDetails[3] + "', '"
                        + bookingDetails[4] + "', '"
                        + bookingDetails[5] + "', "
                        + Integer.parseInt(bookingDetails[6]) + ", "
                        + Integer.parseInt(bookingDetails[7]) + ", "
                        + Integer.parseInt(bookingDetails[8]) + ", '"
                        + bookingDetails[9]  + "', '"
                        + bookingDetails[10] + "', '"
                        + bookingDetails[11] + "', '"
                        + bookingDetails[12] + "', "
                        + Integer.parseInt(bookingDetails[13]) + " );";
                stmt.executeUpdate(query);
            }
            catch(Exception e){
                System.out.println(e);
            }

        }
    }

    public static void updateFlightPassengers(String flightCode, int count) {

        int currentPassengers = getFlightPassengers(flightCode);
        currentPassengers = currentPassengers - count;

        //Updating in Database:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE flights SET remseats =" + currentPassengers
                    + " WHERE fligthcode = '" + flightCode + "';");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static int getFlightPassengers(String flightCode) {

        int p = 200;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT remseats FROM flights WHERE fligthcode = '"
                    + flightCode + "';");
            while(rs.next()) {
                p = rs.getInt(1);
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return p;

    }

    public static boolean getFlightStatus(String email) {

        boolean flag = false;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tempDate = df1.format(date);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM bookings WHERE email = '"
                    + email + "' AND depDate > '" + tempDate + "';");
            while(rs.next()) {
                flag = true;
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return flag;

    }

    public static String[] getRecentFlightDetails(String email) {

        String[] result = new String[7];
        //Order is: Reference No., From , To, DDate, Dtime, Class, Meal
        
        java.util.Date date = new java.util.Date();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tempDate = df1.format(date);


        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT Reference, depDate, class, meal FROM bookings WHERE email = '"
                    + email + "' AND depDate > '" + tempDate + "' ORDER BY depDate ASC;");
            if(rs.next()){
                result[0] = rs.getString(1);
                SimpleDateFormat s = new SimpleDateFormat("dd.MM.yyyy");
                result[3] = s.format(rs.getDate(2));
                result[4] = rs.getString(2).substring(11, 16) + " Hrs";
                result[5] = rs.getString(3);
                if(rs.getString(4).equals("I")){
                    result[6] = "Included";
                }else{
                    result[6] = "Excluded";
                }
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            String flightCode = result[0].substring(0, result[0].indexOf("-"));
            ResultSet rs=stmt.executeQuery("SELECT fromm, too FROM flights WHERE fligthCode = '"
                    + flightCode + "';");
            while(rs.next()) {
                int index = Arrays.asList(list).indexOf(rs.getString(1));
                result[1] = names[index];
                index = Arrays.asList(list).indexOf(rs.getString(2));
                result[2] = names[index];
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        

        return result;
    }

    public static String getFlightDepDate(String code) {

        String val = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT departureDate FROM flights WHERE fligthcode = '"
                    + code + "';");
            while(rs.next()) {
                val =  rs.getString(1);
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return val;

    }

    public static int getBaseFare(String flightCode) {

        String from ="", to = "";
        double price;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT fromm, too FROM flights WHERE fligthCode = '"
                    + flightCode + "';");
            while(rs.next()) {
                from = rs.getString(1);
                from = from.toUpperCase();
                to = rs.getString(2);
                to = to.toUpperCase();
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        double dist = distance(from, to);
            if (dist <= 1378) {
                price = 10000;
            } else if (dist <= 2409) {
                price = 15000;
            } else if (dist <= 3439) {
                price = 20000;
            } else if (dist <= 4469) {
                price = 25000;
            } else if (dist <= 5499) {
                price = 30000;
            } else if (dist <= 6529) {
                price = 35000;
            } else if (dist <= 7559) {
                price = 40000;
            } else if (dist <= 8589) {
                price = 45000;
            } else if (dist <= 9619) {
                price = 50000;
            } else if (dist <= 10649) {
                price = 55000;
            } else if (dist <= 11679) {
                price = 60000;
            } else if (dist <= 12709) {
                price = 65000;
            } else if (dist <= 13739) {
                price = 70000;
            } else if (dist <= 14769) {
                price = 75000;
            } else if (dist <= 15799) {
                price = 80000;
            } else if (dist <= 16829) {
                price = 85000;
            } else if (dist <= 17859) {
                price = 85000;
            } else if (dist <= 18889) {
                price = 85000;
            } else {
                price = 90000;
            }

        return (int)price;
    }

    public static boolean checkBookingExistence(String str, String email) {
        boolean val = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM bookings WHERE reference = '"
                    + str + "' AND email = '" + email + "';");
            if(rs.next()) {
                val = true;
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return val;
    }

    public static String[] getBookingDetails(String str) {
        //Order is: Reference, Class, Amount, Passengers, Adults, Kids, Refund Status, Meal, Type of Meal,
        //          from and to details.

        ArrayList<String> list = new ArrayList<>();

        String[] res = getFlightDetails(str.substring(0, str.indexOf("-")));
        // Order is: [from, to, dDate, dTime, dAirport, dCity, dcountry, aDate, aTime, aAirport, aCity, acountry]

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM bookings WHERE reference = '"
                    + str + "';");
            while(rs.next()) {
                list.add(rs.getString(2));              //Reference Number
                list.add(rs.getString(10));              //Class
                list.add(rs.getString(14));             //Amount
                list.add(rs.getString(7));              //Passengers
                list.add(rs.getString(8));              //Adults
                list.add(rs.getString(9));              //Kids
                list.add(rs.getString(13));             //Refund Status
                list.add(rs.getString(11));             //Meal
                list.add(rs.getString(12));             //Type of Meal
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        Collections.addAll(list, res);
        String[] ret = new String[list.size()];
        ret = list.toArray(ret);

        return ret;
    }

    public static boolean checkBookingExistenceEmail(String email) {
        boolean val = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM bookings WHERE email = '"
                    + email + "';");
            if(rs.next()) {
                val = true;
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return val;
    }

    public static void cancelFlight(String reference) {

        //Updating in Database:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            restorePassengers(reference);
            stmt.executeUpdate("DELETE FROM bookings WHERE reference = '" + reference
                    + "';");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static boolean checkDateDiff(String str1, String str2) {

        boolean val = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();

            ResultSet rs1 =stmt1.executeQuery("SELECT departureDate, arrivalDate FROM flights WHERE fligthcode = '"
                    + str1 + "';");
            
            ResultSet rs2 =stmt2.executeQuery("SELECT departureDate FROM flights WHERE fligthcode = '"
                    + str2 + "';");
            rs1.next();
            rs2.next();
        
            String dDate1 = rs1.getString(1);
            String aDate1 = rs1.getString(2);
            String dDate2 = rs2.getString(1);
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            java.util.Date ddate1 = formatter.parse(dDate1);  
            java.util.Date adate1 = formatter.parse(aDate1); 
            java.util.Date ddate2 = formatter.parse(dDate2); 

            if(adate1.before(ddate2)){
                val = true;
            }

            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        return val;

    }

    public static void cancelReturnFlight(String reference, String email) {

        String newReference = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT bookingNumber, reference FROM bookings WHERE email ='"
                    + email + "'  AND reference != '" + reference + "';");
            while(rs.next()) {
                String str = rs.getString(2);
                if(str.contains("#")){
                    String lastPart = reference.substring(str.indexOf('#'));        //#5
                    if(str.contains(lastPart)){
                        newReference = rs.getString(2);
                    }
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt1 = con.createStatement();
            Statement stmt2 = con.createStatement();

            restorePassengers(reference);
            restorePassengers(newReference);

            stmt1.executeUpdate("DELETE FROM bookings WHERE reference = '" + reference + "';");
            stmt2.executeUpdate("DELETE FROM bookings WHERE reference = '" + newReference + "';");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static void restorePassengers(String reference) {

        String flightCode = reference.substring(0, reference.indexOf("-"));
        int currentPassengers = getFlightPassengers(flightCode);
        int count = 1;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1","root","123456789");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT passengers FROM bookings WHERE reference = '"
                    + reference + "';");
            while(rs.next()) {
                count =  rs.getInt(1);
            }
            con.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        currentPassengers = currentPassengers + count;

        //Updating in Database:
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("UPDATE flights SET remseats =" + currentPassengers
                    + " WHERE fligthcode = '" + flightCode + "';");
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


//    What does MD5 mean?
//
//    MD5 is the abbreviation of 'Message-Digest algorithm 5'.
//    The MD5 algorithm is used as an encryption or fingerprint function for a file.
//    Often used to encrypt database passwords, MD5 is also able to generate a file
//      thumbprint to ensure that a file is identical after a transfer for example.
//    An MD5 hash is composed of 32 hexadecimal characters.
//    Enter a word in the MD5 encryption form above to know the corresponding MD5 hash
