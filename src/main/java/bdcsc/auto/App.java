package bdcsc.auto;

import bdcsc.auto.timer.MysqlObject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("开始执行定时任务");
        // 连接数据库的驱动以及url
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bdcsc_sys?useUnicode=true&characterEncoding=UTF-8&useSSL=false"; //bdcsc_sys:bdcsc_report_rescode_analyze

        String username = "mwr";
        String password = "mwr";

        //连接数据库
        Connection conn = null;
        Statement statement = null;
        Statement statement2 = null;

        String[] dates = {"20181015", "20181016", "20181017", "20181018", "20181019", "20181020", "20181021",
                "20181022", "20181023", "20181024", "20181025"};

        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        try {
            // 注册驱动
            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
            statement2 = conn.createStatement();
            int i = (int) Math.round(Math.random() * 10);
            String randomDate = dates[i];
            String sql = "select * from bdcsc_report_rescode_analyze where date like '" + randomDate + "%'";
            ResultSet set = statement.executeQuery(sql);

            // 遍历结果集，并将结果替换日期后插入数据库
            while (set.next()) {
                String usercode = set.getString("usercode") == null ? "" : set.getString("usercode");
                String module = set.getString("module") == null ? "" : set.getString("module");
                String method = set.getString("method") == null ? "" : set.getString("method");
                String resnum = set.getString("resnum") == null ? "" : set.getString("resnum");
                int num = set.getInt("num");
                String date = set.getString("date").replace(randomDate, today);
                String product = set.getString("product") == null ? "" : set.getString("product");
                String provcode = set.getString("provcode") == null ? "" : set.getString("provcode");
                String productcode = set.getString("productcode") == null ? "" : set.getString("productcode");

                MysqlObject mo = new MysqlObject();
                mo.setUsercode(usercode);
                mo.setModule(module);
                mo.setMethod(method);
                mo.setResnum(resnum);
                mo.setNum(num);
                mo.setDate(date);
                mo.setProduct(product);
                mo.setProvcode(provcode);
                mo.setProductcode(productcode);

                String insertSql = "insert into bdcsc_report_rescode_analyze (usercode, module, method, resnum, num," +
                        " date, product, provcode, productcode) value" + "('" + mo.getUsercode() + "','" + mo.getModule() + "','" +
                        mo.getMethod() + "','" + mo.getResnum() + "'," + mo.getNum()  + ",'" + mo.getDate()  + "','" +
                        mo.getProduct()  + "','" + mo.getProvcode() + "','" + mo.getProductcode() + "')";

                System.out.println(insertSql);

                // 插入数据库
                statement2.execute(insertSql);
            }

            String sql2 = "select * from bdcsc_report_restime_analyze where date like '" + randomDate + "%'";
            ResultSet set2 = statement.executeQuery(sql2);
            while (set2.next()) {
                String usercode = set2.getString("usercode") == null ? "" : set2.getString("usercode");
                String module = set2.getString("module") == null ? "" : set2.getString("module");
                String method = set2.getString("method") == null ? "" : set2.getString("method");
                int num = set2.getInt("num");
                int sum = set2.getInt("sum");
                String date = set2.getString("date").replace(randomDate, today);
                String product = set2.getString("product") == null ? "" : set2.getString("product");
                String totaltime = set2.getString("totaltime") == null ? "" : set2.getString("totaltime");
                String provcode = set2.getString("provcode") == null ? "" : set2.getString("provcode");
                String productcode = set2.getString("productcode") == null ? "" : set2.getString("productcode");

                String insertSql = "insert into bdcsc_report_restime_analyze (usercode, module, method, num, sum, " +
                        "date, product, totaltime, provcode, productcode) value ('" + usercode + "','" +
                        module + "','" + method + "'," + num + "," +
                        sum + ",'" + date + "','" + product + "','" +
                        totaltime + "','" + provcode + "','" + productcode + "')";
                System.out.println(insertSql);
                statement2.execute(insertSql);
            }


            String sql3 = "select * from dic_info.tb_collect_history where date = '" + randomDate + "'";
            ResultSet set3 = statement.executeQuery(sql3);
            while (set3.next()) {
                String insertSql = "insert into dic_info.tb_collect_history (date, provcode, usercode, method, resnum, " +
                        "num, username, methodname, productname, module, productcode, product) value ('" + today + "','" +
                        set3.getString("provcode") + "','" + set3.getString("usercode") + "','" + set3.getString("method") +
                        "','" + set3.getString("resnum") + "','" + set3.getString("num") + "','" + set3.getString("username") +
                        "','" + set3.getString("methodname") + "','" + set3.getString("productname") + "','" +
                        set3.getString("module") + "','" + set3.getString("productcode") + "','" + set3.getString("product") + "')";
                System.out.println(insertSql);
                statement2.execute(insertSql);
            }

            String sql4 = "select * from dic_info.tb_collect_now where date = '" + randomDate + "'";
            ResultSet set4 = statement.executeQuery(sql4);
            while (set4.next()) {
                String insertSql = "insert into dic_info.tb_collect_now (date, provcode, usercode, method, resnum, " +
                        "num, username, methodname, productname, module, productcode, product) value ('" + today + "','" +
                        set3.getString("provcode") + "','" + set3.getString("usercode") + "','" + set3.getString("method") +
                        "','" + set3.getString("resnum") + "','" + set3.getString("num") + "','" + set3.getString("username") +
                        "','" + set3.getString("methodname") + "','" + set3.getString("productname") + "','" +
                        set3.getString("module") + "','" + set3.getString("productcode") + "','" + set3.getString("product") + "')";
                System.out.println(insertSql);
                statement2.execute(insertSql);
            }

            System.out.println("定时任务执行成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
