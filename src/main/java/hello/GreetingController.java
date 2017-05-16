package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import javax.servlet.Servlet;

@Controller
public class GreetingController {
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/read")
    public String read(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) {
//        model.addAttribute("name", name);
        return "read";
    }

    @RequestMapping("/create")
    public String create(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) {
//        model.addAttribute("name", name);
        return "create";
    }

    @RequestMapping("/update")
    public String update(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) {
//        model.addAttribute("name", name);
        return "update";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam(value="tableName", required=false, defaultValue="stations") String name, Model model) {
//        model.addAttribute("name", name);
        return "delete";
    }

    @RequestMapping("/createResult")
    public String readResult(@RequestParam(value="name", required=false, defaultValue="") String name,
                             @RequestParam(value="region", required=false, defaultValue="") String region,
                             @RequestParam(value="Hbar", required=false, defaultValue="") String Hbar,
                             Model model) {
        System.out.println(name + " " + region + " " + Hbar);
        if("".equals(name))
            return "createNotValid";
        else
            if("1".equals(name))
                return "createExists";
            else
//        model.addAttribute("name", name);
                return "createSuccess";
    }

    @RequestMapping("/updateResult")
    public String updateResult(@RequestParam(value="id", required=false, defaultValue="") String id,
                            @RequestParam(value="name", required=false, defaultValue="") String name,
                            @RequestParam(value="region", required=false, defaultValue="") String region,
                            @RequestParam(value="Hbar", required=false, defaultValue="") String Hbar,
                             Model model) {
        System.out.println(id + " " + name + " " + region + " " + Hbar);
        if("".equals(id))
            return "updateNotValid";
        else
        if("1".equals(id))
            return "idNotExists";
        else
//        model.addAttribute("name", name);
            return "updateSuccess";
    }

    @RequestMapping("/deleteResult")
    public String deleteResult(@RequestParam(value="id", required=false, defaultValue="") String id,
                               Model model) {
        System.out.println(id);
        if("".equals(id))
            return "deleteNotValid";
        else
        if("1".equals(id))
            return "idNotExistsDel";
        else
//        model.addAttribute("name", name);
            return "deleteSuccess";
    }
}
