package tree;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByType;
import tree.domain.Customer;
import tree.domain.IDCard;
import tree.tool.BaseController;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ControllerTest extends BaseTest {
    @SpringBeanByType private RequestMappingHandlerAdapter handlerAdapter;
    @SpringBeanByType private BaseController controller;
    @SpringBeanByType private RestTemplate template;

    private MockHttpServletRequest request=new MockHttpServletRequest();
    private MockHttpServletResponse response=new MockHttpServletResponse();

    @Test
    @DataSet("init_tables.xls")
    public void unitTest(){
        Customer customer=new Customer("triski");
        customer.setIdCard(new IDCard(1406448399));
        request.setCharacterEncoding("utf-8");
        request.setRequestURI("/login");
        request.setAttribute("customer",customer);
        String result=controller.login(request);
        assertEquals(request.getAttribute("message"),"success");
        assertEquals(result,"session");
    }
    @Test
    public void integrationTest() throws Exception{
        MultiValueMap<String,String>map=new LinkedMultiValueMap<String,String>();
        map.add("greet","china");
        String result=template.postForObject("http://127.0.0.1:8080/index",map,String.class);
        assertTrue(result.contains("nihao"));
    }
}
