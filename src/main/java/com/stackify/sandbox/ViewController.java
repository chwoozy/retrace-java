package com.stackify.sandbox;

import com.stackify.sandbox.exceptions.CustomThrownException;
import com.stackify.sandbox.model.CustomEP;
import com.stackify.sandbox.model.CustomSQL;
import com.stackify.sandbox.services.APIService;
import com.stackify.sandbox.services.SQLService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class ViewController {
    @RequestMapping("/")
    public String index(Model model) {
        return "index";
    }
    
    @RequestMapping("/orm")
    public String orm() {
        try {
            new SQLService().updateQuery(CustomSQL.CREATETABLE.getQuery());
            new SQLService().updateQuery(CustomSQL.DROPTABLE.getQuery());
            System.out.println("Called SQL query on page: " + "/orm");
        } catch (DataAccessException e) {
            System.out.println("Failed SQL query on page: " + "/orm");
        }
        return "orm";
    }

    @PostMapping("/orm")
    public String executeORM(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Map<String, Object>> result = new SQLService().callQuery(CustomSQL.ORMGETROWS.getQuery());
            for (Map<String, Object> entry : result) {
                List<Map<String, Object>> entryResult =
                        new SQLService().callQuery(String.format(CustomSQL.ORMSELECT.getQuery(), entry.get("id")));
                System.out.println(entryResult);
            }
            System.out.println("Called ORM N+1 call on page: " + "/orm");
            redirectAttributes.addFlashAttribute("success", "ORM N+1 query successful!");
        } catch (DataAccessException e) {
            System.out.println("Failed ORM N+1 call on page: " + "/orm");
            redirectAttributes.addFlashAttribute("error", "ORM N+1 query failed");
        }
        return "redirect:/orm";
    }

    @RequestMapping("/slowdb")
    public String slowDB() {
        List<Map<String, Object>> getRequest = new SQLService().callQuery(CustomSQL.FULLJOIN.getQuery());
        if (getRequest.isEmpty()) {
            System.out.println("Failed SQL query on page: " + "/slowdb");
        } else {
            System.out.println("Called SQL query on page: " + "/slowdb");
        }
        return "slowdb";
    }

    @PostMapping("/slowdb")
    public String executeSlowDB(Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> result = new SQLService().executeSP("5sec_proc");
        if (result.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "SlowDB SQL query failed");
        } else {
            redirectAttributes.addFlashAttribute("success", "SQL stored procedure successfully executed!");
        }

        return "redirect:/slowdb";
    }
    
    @RequestMapping("/slowrequest")
    public String slowRequest(Model model, RedirectAttributes redirectAttributes) {
        List<Map<String, Object>> getRequest = new SQLService().callQuery(CustomSQL.LEFTJOIN.getQuery());
        if (getRequest.isEmpty()) {
            System.out.println("Failed SQL query on page: " + "/slowrequest");
        } else {
            System.out.println("Called SQL query on page: " + "/slowrequest");
        }
        List<String> endPoints = CustomEP.getEndPointList();
        model.addAttribute("endpoints", endPoints);
        return "slowrequest";
    }

    @PostMapping("/slowrequest")
    public String executeSlowRequest(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String result = APIService.call(request.getParameter("apicall"));
        if (result.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "SlowRequest API call failed");
            System.out.println("Failed API call on page: " + "/slowrequest");
        } else {
            redirectAttributes.addFlashAttribute("success", "SlowRequest API call successful!");
            System.out.println(result);
            System.out.println("Successful API call on page: " + "/slowrequest");
        }
        return "redirect:/slowrequest";
    }

    @RequestMapping("/swallowedexception")
    public String swallowedException() {
        List<Map<String, Object>> getRequest = new SQLService().callQuery(CustomSQL.RIGHTJOIN.getQuery());
        if (getRequest.isEmpty()) {
            System.out.println("Failed SQL query on page: " + "/swallowedexception");
        } else {
            System.out.println("Called SQL query on page: " + "/swallowedexception");
        }
        return "swallowedexception";
    }

    @PostMapping("/swallowedexception")
    public String executeSwallowedException(Model model, RedirectAttributes redirectAttributes) {
        try {
            throw new CustomThrownException("This exception should be swallowed!");
        } catch (CustomThrownException e) {
            System.out.println("Successfully swallowed exception on page: " + "/swallowedexception");
            redirectAttributes.addFlashAttribute("success", "Successful swallowed exception!");
        }
        return "redirect:/swallowedexception";
    }

    @RequestMapping("/untracked")
    public String untracked() {
        return "untracked";
    }
}
