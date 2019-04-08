package com.qujie.mintwo.system.generalMethod;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/generalMethod")
public class GeneralMethod {

    @RequestMapping("/batchId")
    public static String[] batchId(String [] ids){
        return ids;
    }


}
