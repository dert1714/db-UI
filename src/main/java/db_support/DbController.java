package db_support;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class DbController {

    static RequestCreator db = new RequestCreator();

    @RequestMapping("/")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/read")
    public String read(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) throws SQLException, ClassNotFoundException {
        model.addAttribute("name", "Yana");
        ArrayList<String> ar = new ArrayList<>(Arrays.asList("one", "two", "ten", "aga"));
        Connection con = RequestCreator.getDbConnection();
        ArrayList a = db.selectRequest(con,"weather");
        model.addAttribute("strings", a);
        return "read1";
    }

    @RequestMapping("/create")
    public String create(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) {
        return "create";
    }

    @RequestMapping("/update")
    public String update(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) {
        return "update";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) {
        return "delete";
    }

    @RequestMapping("/createResult")
    public String readResult(@RequestParam(value="name", required=false, defaultValue="") String name,
                             @RequestParam(value="region", required=false, defaultValue="") String region,
                             @RequestParam(value="Hbar", required=false, defaultValue="") String Hbar,
                             Model model) throws SQLException, ClassNotFoundException {
        if("".equals(name) || "".equals(region) || "".equals(Hbar))
            return "createNotValid";
        Connection con = RequestCreator.getDbConnection();
        if(db.isUnique(con,"weather","name",name))
            return "createIdFail";
        else{
            String request = db.getRequest("insert","regions","region", region);
            String select = db.getSelectRequest("regions","region", region);
            int id_region = db.updateTable(con,request,select);
            request = db.getRequest("insert","hbars","hbar", Hbar);
            select = db.getSelectRequest("hbars","hbar", Hbar);
            int id_hbar = db.updateTable(con,request,select);
            Statement st = con.createStatement();
            int res = st.executeUpdate("INSERT INTO weather (name,region,hbar) VALUES("
                    + "\'" + name + "\'" + "," + "\'" + id_region + "\'" + "," + "\'" + id_hbar + "\'" + ")");
        }
        return "createSuccess";
    }

    @RequestMapping("/updateResult")
    public String updateResult(@RequestParam(value="name", required=false, defaultValue="") String name,
                               @RequestParam(value="region", required=false, defaultValue="") String region,
                               @RequestParam(value="Hbar", required=false, defaultValue="") String Hbar,
                               Model model) throws SQLException, ClassNotFoundException {
        if("".equals(name) || "".equals(region) || "".equals(Hbar))
            return "updateNotValid";
        Connection con = RequestCreator.getDbConnection();
        if(!db.isUnique(con,"weather","name",name))
            return "idNotExists";
        else{
            String request = db.getRequest("insert","regions","region", region);
            String select = db.getSelectRequest("regions","region", region);
            int id_region = db.updateTable(con,request,select);
            request = db.getRequest("insert","hbars","hbar", Hbar);
            select = db.getSelectRequest("hbars","hbar", Hbar);
            int id_hbar = db.updateTable(con,request,select);
            Statement st = con.createStatement();
            st.executeUpdate("UPDATE weather SET region=" + id_region + ", hbar=" + id_hbar
            + " WHERE name=" + "\'" + name + "\'");
        }
        return "updateSuccess";
    }

    @RequestMapping("/deleteResult")
    public String deleteResult(@RequestParam(value="name", required=false, defaultValue="") String name,
                               Model model) throws SQLException, ClassNotFoundException {
        if("".equals(name))
            return "deleteNotValid";
        Connection con = RequestCreator.getDbConnection();
        if(!db.isUnique(con,"weather","name",name))
            return "idNotExistsDel";
        else {
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM weather WHERE name=" + "\'" + name + "\'");
        }
        return "deleteSuccess";
    }
}
