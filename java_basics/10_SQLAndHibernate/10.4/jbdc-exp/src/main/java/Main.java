import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/skillbox?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String user = "root";
        String pass = "testtest";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT name, count(s.subscription_date)/(TIMESTAMPDIFF(MONTH,min(s.subscription_date),max(s.subscription_date))) AS avg_sub_per_month FROM Subscriptions s LEFT JOIN Courses c ON c.id = s.course_id GROUP BY c.name;");
            while (resultSet.next()){
                String avg_sub_per_month = resultSet.getString("avg_sub_per_month");
                String courseName = resultSet.getString("name");
                System.out.println(courseName + " - " + avg_sub_per_month);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
