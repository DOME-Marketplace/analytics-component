package com.dome.matomo_analytics;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class InsertDemoData {
    private static final String DB_URL = "jdbc:postgresql://dome-monitoring-server.eurodyn.com:5433/mydata";
    private static final String USER = "ramp";
    private static final String PASS = "jijikos";

//    private static final String DB_URL = "jdbc:postgresql://localhost:5433/matomo_data_json";
//    private static final String USER = "postgres";
//    private static final String PASS = "admin";
    private static final String JSON_DAILY_FILE_PATH = "C:\\Users\\pkont\\Downloads\\Demo_market_place_daily.json";
    private static final String JSON_MONTHLY_FILE_PATH = "C:\\Users\\pkont\\Desktop\\dome-server\\demo_data_NEW\\monthly.json";

    private static final String JSON_COUNTRY_FILE_PATH = "C:\\Users\\pkont\\Desktop\\dome-server\\demo_data\\marketplace demo\\Countries_subs.json";
    private static final String JSON_MONTHLY_MP_FILE_PATH = "C:\\Users\\pkont\\Desktop\\dome-server\\demo_data_NEW\\Monthly_mp.json";

    private static final String JSON_SALES_MONTHLY_FILE_PATH = "C:\\Users\\pkont\\Desktop\\dome-server\\demo_data_NEW\\Sales_monthly.json";

    private static final String JSON_SERVICES_FILE_PATH = "C:\\Users\\pkont\\Desktop\\dome-server\\demo_data\\marketplace demo\\services.json";

    private static final String JSON_SOURCE_OF_VISITS_FILE_PATH = "C:\\Users\\pkont\\Desktop\\dome-server\\demo_data_NEW\\new\\source_of_visits.json";

    private static final String JSON_SUBSCRIPTION_TIME_FILE_PATH = "C:\\Users\\pkont\\Desktop\\dome-server\\demo_data_NEW\\new\\Subscription_time.json";
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            //generatePageData(conn);
            insertDailyData(conn);
            insertMonthlyData(conn);
            //insertCountryData(conn);
            //insertMonthlyMp(conn);
            //insertSalesMonthly(conn);
            //insertServices(conn);
            //insertSourceOfVisits(conn);
            //insertSubscriptionTime(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertDailyData(Connection conn) {
        try (Reader reader = new FileReader(JSON_DAILY_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO daily_stats_demo (id, date, period, data, api_method) VALUES (?, ?, ?, ?::jsonb, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();
                    String date = jsonObj.get("date").getAsString();
                    jsonObj.remove("date");

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, date);
                    pstmt.setString(3, "day");
                    pstmt.setObject(4, jsonObj.toString());
                    pstmt.setString(5, "Actions.getPageURLs");
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertMonthlyData(Connection conn) {
        try (Reader reader = new FileReader(JSON_MONTHLY_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO monthly_stats_demo (id, date, period, data, api_method) VALUES (?, ?, ?, ?::jsonb, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();

                    String date = jsonObj.get("Date").getAsString();
                    jsonObj.remove("date");

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, date);
                    pstmt.setString(3, "month");
                    pstmt.setObject(4, jsonObj.toString());
                    pstmt.setString(5, "Actions.getPageURLs");
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertCountryData(Connection conn) {
        try (Reader reader = new FileReader(JSON_COUNTRY_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO country_subs (ID, Country, Subs) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();

                    String country = jsonObj.get("Country").getAsString();
                    int subs = jsonObj.get("Subs").getAsInt();

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, country);
                    pstmt.setInt(3, subs);
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertMonthlyMp(Connection conn) {
        try (Reader reader = new FileReader(JSON_MONTHLY_MP_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO monthly_mp (ID, Date, Total_Services, Conversion_Rate, Churn_Rate, new_Subscribers, New_Services)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, jsonObj.get("Date").getAsString());
                    pstmt.setInt(3, jsonObj.get("Total_Services").getAsInt());
                    pstmt.setInt(4, jsonObj.get("Conversion Rate").getAsInt());
                    pstmt.setInt(5, jsonObj.get("Churn Rate").getAsInt());
                    pstmt.setInt(6, jsonObj.get("new Subscribers").getAsInt());
                    pstmt.setInt(7, jsonObj.get("new Services").getAsInt());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertSalesMonthly(Connection conn) {
        try (Reader reader = new FileReader(JSON_SALES_MONTHLY_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO sales_monthly (ID, Date, Product, Customers, Sales, Brand) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, jsonObj.get("Date").getAsString());
                    pstmt.setString(3, jsonObj.get("Product").getAsString());
                    pstmt.setInt(4, jsonObj.get("Customers").getAsInt());
                    pstmt.setBigDecimal(5, jsonObj.get("Sales").getAsBigDecimal());
                    pstmt.setString(6, jsonObj.get("Brand").getAsString());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void insertServices(Connection conn) {
        try (Reader reader = new FileReader(JSON_SERVICES_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO services (ID, Product, Cost, Brand, Type_Service, prod) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, jsonObj.get("Product").getAsString());
                    pstmt.setBigDecimal(3, jsonObj.get("Cost").getAsBigDecimal());
                    pstmt.setString(4, jsonObj.get("Brand").getAsString());
                    pstmt.setString(5, jsonObj.get("Type_Service").getAsString());
                    pstmt.setString(6, jsonObj.get("prod").getAsString());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertSourceOfVisits(Connection conn) {
        try (Reader reader = new FileReader(JSON_SOURCE_OF_VISITS_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO source_of_visits (ID, Date, Type, Value) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, jsonObj.get("Date").getAsString());
                    pstmt.setString(3, jsonObj.get("Type").getAsString());
                    pstmt.setInt(4, jsonObj.get("Value").getAsInt());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertSubscriptionTime(Connection conn) {
        try (Reader reader = new FileReader(JSON_SUBSCRIPTION_TIME_FILE_PATH)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            String sql = "INSERT INTO subscription_time (ID, Date, Type, Value) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();

                    pstmt.setObject(1, UUID.randomUUID());
                    pstmt.setString(2, jsonObj.get("Date").getAsString());
                    pstmt.setString(3, jsonObj.get("Type").getAsString());
                    pstmt.setInt(4, jsonObj.get("Value").getAsInt());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generatePageData(Connection conn) throws SQLException {
        String[] pageLabels = {"Home",
                "Contact",
                "About",
                "Products",
                "Services",
                "Providers",
                "Superset",
                "Workspace",
                "Dashboard"};

        String baseUrl = "https://demo_data/";

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO page (page_id, label, url) VALUES (?, ?, ?)");

        for (String label : pageLabels) {
            UUID pageId = UUID.randomUUID();
            pstmt.setObject(1, pageId);
            pstmt.setString(2, label);
            pstmt.setString(3, baseUrl + label.toLowerCase());
            pstmt.executeUpdate();
        }
    }


}
