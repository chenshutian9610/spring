package tree;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.testng.annotations.Test;
import tree.domain.Customer;
import tree.domain.IDCard;

import static org.testng.Assert.*;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;
import java.util.Map;

/* 动态语言，即运行时可动态更改参数的语言 */
public class SpEL {

    @Test
    /* java 本身不支持动态语言，需要调用其他脚本语言 */
    public void JVMEL() throws Exception{
        ScriptEngineManager manager=new ScriptEngineManager();
        ScriptEngine engine=manager.getEngineByName("JavaScript");
        engine.eval("function sum(a,b){return a+b;}");
        Invocable invocable=(Invocable)engine;
        Object result=invocable.invokeFunction("sum",3,5);
        assertEquals(8.0,result);
    }

    @Test
    /* SpEL 吸取了 ognl 等动态语言框架的长处，并且和 spring 无缝集成 */
    public void SpEL() throws Exception{

        /* 初始化一个 customer */
        Customer customer=new Customer("triski");
        IDCard idCard=new IDCard(1406448399);
        customer.setIdCard(idCard);

        /* 初始化 SpEL 组件 */
        ExpressionParser parser=new SpelExpressionParser();
        EvaluationContext context=new StandardEvaluationContext(customer);

        /* 取值上下文可以设置变量，变量使用时需要加上 # */
        context.setVariable("x",100);
        parser.parseExpression("setId(#x)").getValue(context);
        String name=parser.parseExpression("getName()").getValue(customer,String.class);
        long num=parser.parseExpression("idCard.num").getValue(customer,long.class);

        /* 测试数据 */
        assertEquals(customer.getId(),100);
        assertEquals(name,"triski");
        assertEquals(num,1406448399);
    }

    @Test
    /* SpEL 表达式字符串基础 */
    public void collection() throws Exception{

        ExpressionParser parser=new SpelExpressionParser();
        EvaluationContext context=new StandardEvaluationContext();

        /* 集合类型的转换，暂不支持二维数组 */
        int[]array=parser.parseExpression("new int[]{1,2,3,7,8,9}").getValue(int[].class);
        List list=parser.parseExpression("{1,2,3,7,8,9}").getValue(List.class);
        Map map=parser.parseExpression("{name:'triski',pwd:1014}").getValue(Map.class);

        /* 集合过滤 */
        context.setVariable("list",list);
        List result1=parser.parseExpression("#list.?[#this>5]").getValue(context,List.class);   //  7 8 9，可以理解为 for(#this:#list)
        int i=parser.parseExpression("#list.^[#this>5]").getValue(context,int.class);           //  7
        int j=parser.parseExpression("#list.$[#this>5]").getValue(context,int.class);           //  9
        context.setVariable("map",map);
        Map result2=parser.parseExpression("#map.?[value==1014]").getValue(context,Map.class);  //  pwd=1014，在 map 中有 key 和 value
    }
}