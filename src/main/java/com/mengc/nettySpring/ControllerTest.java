package com.mengc.nettySpring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：mengchao
 * @date ：Created in 2019/10/18 18:11
 * @description：sdfasf
 */
@Controller
public class ControllerTest {
    @RequestMapping("ControllerTest")
    public @ResponseBody
    String test(){
        return "assdfsfasfasfdsfsdfasf";
    }
}
