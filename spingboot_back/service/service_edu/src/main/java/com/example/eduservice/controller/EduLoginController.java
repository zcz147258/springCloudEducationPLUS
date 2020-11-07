package com.example.eduservice.controller;


import com.example.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    //login
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://i1.hdslb.com/bfs/face/d604c7cebee82a0448062e69cbc21c70766346b4.jpg@52w_52h.webp");
    }
}
