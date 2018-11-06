package bdcsc.auto.timer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobThread implements Runnable{
    @Override
    public void run() {
        // 连接数据库的驱动以及url
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/bdcsc_sys"; //bdcsc_sys:bdcsc_report_rescode_analyze

        String username = "stb";
        String password = "123456";

        //连接数据库
        Connection conn = null;
        Statement statement = null;

        String[] dates = {"20181015", "20181016", "20181017", "20181018", "20181019", "20181020", "20181021",
                "20181022", "20181023", "20181024", "20181025"};

        try {
            // 注册驱动
            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
            int i = (int) Math.round(Math.random() * 10);
            String sql = "select * from bdcsc_report_rescode_analyze where date like '" + dates[i] + "%'";
            ResultSet set = statement.executeQuery(sql);

            List<MysqlObject> mos = new ArrayList<>();
            // 遍历结果集，并将结果替换日期后插入数据库
            while (set.next()) {
                int id = set.getInt("ID");
                String usercode = set.getString("usercode");
                String module = set.getString("module");
                String method = set.getString("method");
                String resnum = set.getString("resnum");
                int num = set.getInt("num");
                String date = set.getString("date");
                String product = set.getString("product");
                String provcode = set.getString("provcode");
                String productcode = set.getString("productcode");

                MysqlObject mo = new MysqlObject();
                mo.setId(id);
                mo.setUsercode(usercode);
                mo.setModule(module);
                mo.setMethod(method);
                mo.setResnum(resnum);
                mo.setNum(num);
                mo.setDate(date);
                mo.setProduct(product);
                mo.setProvcode(provcode);
                mo.setProductcode(productcode);

                mos.add(mo);
            }

            StringBuilder insertSql = new StringBuilder("insert into bdcsc_report_rescode_analyze (ID, usercode, module, method, resnum, num," +
                    " date, product, provcode, productcode) values");
            // 将数据插入数据库
            for (MysqlObject m : mos) {
                insertSql.append("(" + m.getId() + "," + m.getUsercode() + "," + m.getModule() + "," +
                        m.getMethod() + "," + m.getResnum() + "," + m.getNum()  + "," + m.getDate()  + "," +
                        m.getProduct()  + "," + m.getProvcode() + "," + m.getProductcode() + "),");
            }
            insertSql.substring(0, insertSql.length() - 1);

            boolean execute = statement.execute(insertSql.toString());
            if (execute) {
                System.out.println("数据更新成功！");
            } else {
                System.out.println("数据更新失败！");
            }
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
