package com.tsingtec.mini.controller;


import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Api(tags = "公众号视图",description = "负责返回视图")
@Controller
@RequestMapping("/mobile")
public class MobileController {

    @GetMapping("/{str}")
    public String home(@PathVariable("str")String str){
        return "mp/"+str;
    }

    @GetMapping("/{str}/{str1}")
    public String str(@PathVariable("str") String str,@PathVariable("str1") String str1){
        return "mp/"+str+"/"+str1;
    }
}
