package com.qujie.mintwo.system.user.controller;



import com.qujie.mintwo.system.user.service.ITbUserService;
import com.qujie.mintwo.ustils.AbstractController;
import com.qujie.mintwo.ustils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * @author jobob
 * @since 2019-04-02
 */
@RestController
@RequestMapping("/system/tbUser")
public class TbUserController  extends AbstractController {

    @Autowired
    private ITbUserService userService;


    //角色列表
    @PostMapping("/userList")
    public void roleList(@RequestBody Map<String, Object> params , HttpServletResponse response) throws IOException {
        PageUtil page = userService.userList(params);
        this.ajaxRequestTable(response,page);
    }


}
