package tree.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tree.domain.Customer;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BaseController {
    @Autowired private BaseService service;

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        Customer customer=(Customer)request.getAttribute("customer");
        long num=service.findIDCardByCustomerName(customer.getName()).getNum();
        if(num==customer.getIdCard().getNum()){
            request.setAttribute("message","success");
            return "session";
        }
        request.setAttribute("message","fail");
        return "index";
    }

    @RequestMapping("/index")
    @ResponseBody
    public String index(HttpServletRequest request){
        if("america".equals(request.getParameter("greet")))
            return "how are you!";
        if("china".equals(request.getParameter("greet")))
            return "nihao！";
        return "nothing";
    }
}
