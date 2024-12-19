import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;


public class Connectivity {

//    private static int index = 1;
    private static int index = 388;
//    private static int index = 775;
//    private static int index = 1162;

    public static void main(String args[]){

//        doWeekSchedule();

        replicate();

//        getPrice("JFK", "BRE");
//        getPriceN("DEL", "AMS");

    }

    private static void replicate() {
        duplicate();
        int weekCount = 1;
        int index = 775;
        while(weekCount <= 28){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from temp");
                while(rs.next()) {
                    String code = "AMI00" + rs.getString(3)
                            + rs.getString(4) + index;
                    String depDate = rs.getString(5);
                    String arrDate = rs.getString(6);
                    SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date dDate = sd.parse(depDate);
                    java.util.Date aDate = sd.parse(arrDate);

                    Calendar c = Calendar.getInstance();
                    c.setTime(dDate);
                    c.add(Calendar.DATE, weekCount*7);
                    dDate = c.getTime();
                    c.setTime(aDate);
                    c.add(Calendar.DATE, weekCount*7);
                    aDate = c.getTime();
                    depDate = sd.format(dDate);
                    arrDate = sd.format(aDate);
                    String[] array = {Integer.toString(index), code, rs.getString(3),
                            rs.getString(4), depDate, arrDate,
                            rs.getString(7), "200"};
                    index++;
                    startEntryFlights(array);
                }
                connection.close();
            }
            catch(Exception e) {
                System.out.println(e);
            }
            weekCount++;
        }
    }

    private static void duplicate() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from temp");
            while(rs.next()) {
                String[] array = {rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), "200"};
                startEntryFlights(array);
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }

    private static void getPriceN(String from, String to) {
        double dist = distance(from, to);
        double totalDistanceDifference = dist - 348;
        double multiplicationFactor = totalDistanceDifference*(-0.001998261);
        System.out.println(multiplicationFactor);
        multiplicationFactor = 43.1 + multiplicationFactor;
        System.out.println(multiplicationFactor);
        double price = multiplicationFactor * dist;

        System.out.println("Double price is: " + price);
        System.out.println();

//        double tNum = (dist - 340) /5.5;
//        tNum  = tNum * 0.01 ;
//        tNum = tNum - 40;
//        double price = dist * tNum;
//
//        return (int)Math.round(price);

        System.out.println(dist);
        System.out.println(totalDistanceDifference);
        System.out.println(multiplicationFactor);
        System.out.println("Double price is: " + price);
        System.out.println((int)Math.round(price));
        Locale currentLocale = Locale.getDefault();
        Currency currentCurrency = Currency.getInstance(currentLocale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);

        System.out.println(currentLocale.getDisplayName());
        System.out.println(currentCurrency.getDisplayName());
        System.out.println(currencyFormatter.format(Math.round(price)));
    }

    private static void doWeekSchedule() {

        String[] source = {"AMS", "BAH", "PEK", "BNE", "CAI", "CPT", "DMM", "DEL", "DXB", "DUR","FRA","JNB","LAX","LHR","MRU","MED","MEX","MUC",
                                "MCT","JFK","CDG","SCL","GRU","PVG","SIN"};

        String[][] destination = {
                //Amsterdam destinations:
                {"BAH", "PEK", "BNE", "CPT", "DMM", "DEL", "DXB", "DUR", "JNB", "MRU", "MCT", "PVG", "SIN"},
                //Bahrain destinations:
                {"AMS", "PEK", "BNE", "CPT", "DEL", "DXB", "DUR", "FRA", "JNB", "LAX", "LHR", "MRU", "MEX", "MUC",
                        "MCT", "JFK", "CDG", "SCL", "GRU", "PVG", "SIN"},
                //Beijing destinations:
                {"AMS", "BAH", "CAI", "CPT", "DMM", "DXB", "DUR", "FRA", "JNB", "LHR", "MRU", "MED", "MUC", "MCT",
                        "JFK", "CDG", "SCL", "GRU"},
                //Brisbane destinations:
                {"AMS", "BAH", "CAI", "DMM", "DEL", "DXB", "FRA", "LHR", "MED", "MUC", "MCT", "JFK", "CDG"},
                //Cairo destinations:
                {"BNE", "CPT", "DEL", "DXB", "DUR", "JNB", "LAX", "MRU", "MEX", "MCT", "JFK", "SCL", "PVG", "SIN"},
                //Cape Town destinations:
                {"JFK", "LAX", "DEL", "PEK", "PVG", "SIN", "AMS", "CDG", "FRA", "LHR", "MUC", "BAH", "DMM", "DXB", "MCT", "CAI"},
                //Dammam destinations:
                {"AMS", "PEK", "BNE", "CPT", "DEL", "DXB", "DUR", "FRA", "JNB", "LAX", "LHR", "MRU", "MEX", "MUC",
                        "MCT", "JFK", "CDG", "SCL", "GRU", "PVG", "SIN"},
                //Delhi destinations:
                {"AMS", "BAH", "BNE", "CAI", "CPT", "DMM", "DXB", "DUR", "FRA", "JNB", "LAX", "LHR", "MRU", "MED",
                        "MEX", "MUC", "MCT", "JFK", "CDG", "SCL", "GRU"},
                //Dubai destinations:
                {"AMS", "BAH", "PEK", "BNE", "CAI", "CPT", "DMM", "DEL", "DUR", "FRA", "JNB", "LAX", "LHR",
                        "MRU", "MED", "MEX", "MUC", "MCT", "JFK", "CDG", "SCL", "GRU", "PVG", "SIN"},
                //Durban destinations:
                {"JFK", "LAX", "DEL", "PEK", "PVG", "SIN", "AMS", "CDG", "FRA", "LHR", "MUC", "BAH", "DMM", "DXB", "MCT", "CAI"},
                //Frankfurt destinations:
                {"BAH", "PEK", "BNE", "CPT", "DMM", "DEL", "DXB", "DUR", "JNB", "MRU", "MCT", "PVG", "SIN"},
                //Johannesburg destinations:
                {"JFK", "LAX", "MEX", "DEL", "PEK", "PVG", "SIN", "AMS", "CDG", "FRA", "LHR", "MUC", "BAH",
                        "DMM", "DXB", "MCT", "MED", "CAI"},
                //Los Angeles destinations:
                {"BAH", "CAI", "DMM", "DEL", "JNB", "MRU", "MED", "MCT", "SIN"},
                //London destinations:
                {"BAH", "PEK", "BNE", "CPT", "DMM", "DEL", "DXB", "DUR", "JNB", "MRU", "MCT", "PVG", "SIN"},
                //Mauritius destinations:
                {"JFK", "LAX", "MEX", "DEL", "PEK", "PVG", "AMS", "CDG", "FRA", "LHR", "MUC", "BAH", "DMM",
                        "DXB", "MCT", "MED", "CAI"},
                //Medina destinations:
                {"PEK", "BNE", "DEL", "DXB", "DUR", "JNB", "MRU", "MEX", "MCT", "PVG", "SIN"},
                //Mexico city destinations:
                {"BAH", "CAI", "DMM", "DEL", "DXB", "JNB", "MRU", "MED", "MCT", "SIN"},
                //Munich destinations:
                {"BAH", "PEK", "BNE", "CPT", "DMM", "DEL", "DXB", "DUR", "JNB", "MRU", "MCT", "PVG", "SIN"},
                //Muscat destinations:
                {"AMS", "BAH", "PEK", "BNE", "CAI", "CPT", "DMM", "DEL", "DXB", "DUR", "FRA", "JNB", "LAX", "LHR",
                        "MRU", "MED", "MEX", "MUC", "JFK", "CDG", "SCL", "GRU", "PVG", "SIN"},
                //New york destinations:
                {"BAH", "BNE", "CAI", "CPT", "DMM", "DEL", "DXB", "DUR", "JNB", "MRU", "MED", "MCT", "PVG", "SIN"},
                //Paris destinations:
                {"BAH", "PEK", "BNE", "CPT", "DMM", "DEL", "DXB", "DUR", "JNB", "MRU", "MCT", "PVG", "SIN"},
                //Santiago destinations:
                {"BAH", "PEK", "CAI", "DMM", "DEL", "DXB", "MCT", "PVG", "SIN"},
                //Sao paulo destinations:
                {"BAH", "PEK", "DMM", "DEL", "DXB", "MED", "MCT", "PVG", "SIN"},
                //Shanghai destinations:
                {"AMS", "BAH", "CAI", "CPT", "DMM", "DXB", "DUR", "FRA", "JNB", "LHR", "MRU", "MED", "MUC", "MCT",
                        "JFK", "CDG", "SCL", "GRU"},
                //Singapore destinations:
                {"AMS", "BAH", "CAI", "CPT", "DMM", "DXB", "DUR", "FRA", "JNB", "LAX", "LHR", "MED", "MEX",
                        "MUC", "MCT", "JFK", "CDG", "SCL", "GRU"}
        };

        for (int i = 0; i < source.length ; i++) {

            String from = source[i];
            String[] d = destination[i];
            int minDate = 12;                   //Week Limit Minimum
            int maxDate = 14;                   //Week Limit Maximum

            for (int j = 0; j < d.length ; j++) {

                String to = d[j];
                if(!from.equals(to)) {
                    boolean connectingStatus;
                    String status;
                    if (to.equals("DXB") || from.equals("DXB")) {
                        status = "false";
                        connectingStatus = false;
                    } else {
                        status = "true";
                        connectingStatus = true;
                    }

                    String flightCode = "AMI00" + from + to + index;

                    //Getting estimated flight time:
                    double dist = getTime(from, to, connectingStatus);
                    dist = Math.round(dist * 100.0) / 100.0;
                    int h = (int) dist;
                    if (connectingStatus) {
                        h = h + 2;
                    }
                    int m = (int) (dist * 100) % 100;
                    m = (int) Math.round(m * 0.6);

                    //Getting new Date and time:
                    java.util.Date date = null;
                    Random rand = new Random();
                    int minh = 0;                               //Time Limit Minimum
                    int maxh = 24;                               //Time Limit Maximum
                    int hour = minh + rand.nextInt((maxh - minh) + 1);
                    String hString = Integer.toString(hour);
                    if (hString.length() == 1) {
                        hString = "0" + hString;
                    }
                    int day = minDate + rand.nextInt((maxDate - minDate) + 1);
                    if(day < minDate){
                        day = minDate;
                    }

                    if(day > maxDate){
                        day = maxDate;
                    }
                    String dd = Integer.toString(day);
                    if(dd.length() == 1){
                        dd = "0" + dd;
                    }

                    String src = "2020-06-" + dd + " " + hString + ":00";
                    try {
                        date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").parse(src);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String currentTime = sdf.format(date);
                    java.util.Date dateNew = addHoursToJavaUtilDate(date, h, m);
                    String newTime = sdf.format(dateNew);

                    String[] array = {Integer.toString(index), flightCode, from, to, currentTime, newTime, status, "200"};
                    startEntry(array);
                    index++;
                }

            }

        }

    }

    private static void letsSchedule() {

        String from = "CPT";
        String[] arry =
                {"JFK", "LAX", "DEL", "PEK", "PVG", "SIN", "AMS", "CDG", "FRA", "LHR", "MUC", "BAH", "DMM", "DXB", "MCT", "CAI"};
        int mind = 15;                               //Week Limit Minimum
        int maxd = 22;                               //Week Limit Maximum
        for (int i = 0; i < arry.length ; i++) {
            String to = arry[0];
            boolean connectingStatus;
            String status;
            if (to.equals("DXB")) {
                status = "false";
                connectingStatus = false;
            } else {
                status = "true";
                connectingStatus = true;
            }

            String flightCode = "AMI00" + from + to + index;

            //Getting estimated flight time:
            double d = getTime(from, to, connectingStatus);
            d = Math.round(d * 100.0) / 100.0;
            int h = (int) d;
            int m = (int) (d * 100) % 100;
            m = (int) Math.round(m * 0.6);

            //Getting new Date and time:
            java.util.Date date = null;
            Random rand = new Random();
            int minh = 0;                               //Time Limit Minimum
            int maxh = 24;                               //Time Limit Maximum
            int hour = minh + rand.nextInt((maxh - minh) + 1);
            String hString = Integer.toString(hour);
            if (hString.length() == 1) {
                hString = "0" + hString;
            }
            int day = mind + rand.nextInt((maxd - mind) + 1);

            String src = "2020-05-" + day + " " + hString + ":00";
            try {
                date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").parse(src);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currentTime = sdf.format(date);
            java.util.Date dateNew = addHoursToJavaUtilDate(date, h, m);
            String newTime = sdf.format(dateNew);

            String[] array = {Integer.toString(index), flightCode, from, to, currentTime, newTime, status, "200"};
            startEntry(array);
            index++;

        }
    }

    private static void startEntry(String[] arr) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1",
                    "root","123456789");
            Statement stmt = (Statement) con.createStatement();
            String query = "INSERT INTO temp VALUES( "
                    + Integer.parseInt(arr[0])+ ", '"
                    + arr[1] + "', '"
                    + arr[2] + "', '"
                    + arr[3] + "', '"
                    + arr[4] + "', '"
                    + arr[5] + "', '"
                    + arr[6] + "', "
                    + Integer.parseInt(arr[7]) + " );";
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private static void startEntryFlights(String[] arr) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1",
                    "root","123456789");
            Statement stmt = (Statement) con.createStatement();
            String query = "INSERT INTO flights VALUES( "
                    + Integer.parseInt(arr[0])+ ", '"
                    + arr[1] + "', '"
                    + arr[2] + "', '"
                    + arr[3] + "', '"
                    + arr[4] + "', '"
                    + arr[5] + "', '"
                    + arr[6] + "', "
                    + Integer.parseInt(arr[7]) + " );";
            stmt.executeUpdate(query);
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    private static void getPrice(String from , String to){

        ArrayList<Double> distance = new ArrayList<>();
        String fom = " ", end = " ";
        double max = 0.00;
        double min = 10000.00;
        double distanceN;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1", "root", "123456789");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select fromm, too from flights where flightno <= 389;");
            while(rs.next()) {
                String f = rs.getString(1);
                String d = rs.getString(2);
                if(!f.toUpperCase().equalsIgnoreCase(d.toUpperCase())) {
                    distanceN = distance(f.toUpperCase(), d.toUpperCase());
                    if (max <= distanceN) {
                        max = distanceN;
                    }

                    if (min >= distanceN) {
                        min = distanceN;
                        fom = f;
                        end = d;
                    }
                }
            }
            connection.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        System.out.println(max);
        System.out.println(min);

    }

    public static java.util.Date addHoursToJavaUtilDate(java.util.Date date, int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    private static double distance(String fromCode, String toCode){
        double fromX = Database.getX(fromCode);
        double fromY = Database.getY(fromCode);
        double toX = Database.getX(toCode);
        double toY = Database.getY(toCode);

        double d = distanceN(fromY, toY, fromX, toX);
        return d;
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

    private static void startAirportEntry() {
        String[][] arr;

//        arr[0] = new String[]{"1", "1", "1", "1", "1", "1", "1"};
//        arr[1] = new String[]{"2", "2", "2", "2", "2", "2", "2"};

        //Longitude is: X [E-W]
        //Latitude is: Y [N-S]

        //Order is: Code, Name, City, Country, Category, CoordinateX, CoordinateY
        arr = new String[][]{
                {"CPT", "Cape Town International Airport", "Cape Town", "South Africa",         "Africa",
                        "18.602010", "-33.971445"},
                {"DUR", "King Shaka International Airport", "Durban", "South Africa",           "Africa",
                        "31.116887" , "-29.609913"},
                {"JNB", "O R Tambo International Airport", "Johannesburg", "South Africa",      "Africa",
                        "28.241060" , "-26.136625"},
                {"MRU", "Sir Seewoosagur Ramgoolam International Airport", "Plaine Magnien", "Mauritius", "Africa",
                        "57.679712" , "-20.432154"},
                {"CAI", "Cairo International Airport", "Cairo", "Egypt",                        "Africa",
                        "31.399501" , "30.112688"},
                {"SIN", "Changi Airport", "Singapore", "Singapore",                   "Asia and Pacific",
                        "103.991273" , "1.364463"},
                {"DEL", "Indira Ghandhi International Airport", "Delhi", "India",     "Asia and Pacific",
                        "77.100687" , "28.556407"},
                {"BNE", "Brisbane Airport", "Brisbane", "Australia",                  "Asia and Pacific",
                        "153.122045" , "-27.394138"},
                {"PVG", "Pu Dong International Airport", "Shanghai", "China",         "Asia and Pacific",
                        "121.808858" , "31.144742"},
                {"PEK", "Beijing Capital Airport", "Beijing", "China",                "Asia and Pacific",
                        "116.603400" , "40.080112"},
                {"MUC", "Franz Josef Strauss Airport", "Munich", "Germany",                     "Europe",
                        "11.775242" , "48.353591"},
                {"LHR", "London Heathrow Airport", "London", "United Kingdom",                  "Europe",
                        "-0.454274" , "51.470056"},
                {"CDG", "Charles De Gaulle Airport", "Paris", "France",                         "Europe",
                        "2.548021" , "49.009705"},
                {"AMS", "Amsterdam Airport Schiphol", "Amsterdam", "Netherlands",               "Europe",
                        "4.791246" , "52.312377"},
                {"FRA", "Rhein-Main International Airport", "Frankfurt", "Germany",             "Europe",
                        "8.562131" , "50.038034"},
                {"LAX", "Los Angeles International Airport", "Los Angeles", "United States",    "America",
                        "-118.408787" , "33.941722"},
                {"GRU", "Sao Paulo International Airport", "São Paulo", "Brazil",               "America",
                        "-46.472957" , "-23.430425"},
                {"SCL", "Santiago Arturo Merino Benítez Airport", "Santiago de Chile", "Chile", "America",
                        "-70.793689" , "-33.397186"},
                {"JFK", "John F Kennedy International Airport", "New York", "United States",    "America",
                        "-73.778171" , "40.641376"},
                {"MEX", "Mexico City International Airport", "Mexico City", "Mexico",           "America",
                        "-99.071876" , "19.436161"},
                {"DXB", "Dubai International Airport", "Dubai", "United Arab Emirates",     "Middle East",
                        "55.365630" , "25.253417"},
                {"MCT", "Muscat International Airport", "Muscat", "Oman",                   "Middle East",
                        "58.287094" , "23.601014"},
                {"DMM", "King Fahd International Airport", "Dammam", "Saudi Arabia",        "Middle East",
                        "49.797345" , "26.468536"},
                {"BAH", "Bahrain International Airport", "Al Muharraq", "Bahrain",          "Middle East",
                        "50.626022" , "26.269897"},
                {"MED", "Prince Mohammad Bin Abdulaziz Airport", "Medina", "Saudi Arabia",  "Middle East",
                        "39.714947" , "24.554025"},
        };

//        display(arr);

        for (int i = 0; i < arr.length ; i++) {

            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = (Connection) DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/project8525?useSSL=false&characterEncoding=latin1",
                        "root","123456789");
                Statement stmt = (Statement) con.createStatement();
                String query = "INSERT INTO airport_list VALUES( '"
                        + arr[i][0] + "', '"
                        + arr[i][1] + "', '"
                        + arr[i][2] + "', '"
                        + arr[i][3] + "', '"
                        + arr[i][4] + "', "
                        + Double.parseDouble(arr[i][5])  + ", "
                        + Double.parseDouble(arr[i][6]) + " );";
                stmt.executeUpdate(query);
            }
            catch(Exception e){
                System.out.println(e);
            }

        }
    }

    private static void display(String[][] arr) {
        for (int i = 0; i < arr.length ; i++) {
            for (int j = 0; j < arr[i].length ; j++) {
                System.out.print(arr[i][j]);
                if(j != arr[i].length - 1){
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

}
