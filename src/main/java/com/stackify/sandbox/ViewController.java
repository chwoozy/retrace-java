package com.stackify.sandbox;

import com.stackify.api.common.log.direct.LogManager;
import com.stackify.api.common.log.direct.Logger;
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
            Logger.queueMessage("info", "Called SQL query on page: " + "/orm");
	        LogManager.shutdown();
        } catch (DataAccessException e) {
            System.out.println("Failed SQL query on page: " + "/orm");
            Logger.queueException("error", "Failed SQL query on page: " + "/orm", e);
	        LogManager.shutdown();
        }
        return "orm";
    }

    @PostMapping("/orm")
    public String executeORM(Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> result = new SQLService().executeSP("good_getsupplierid_proc");
        if (result.isEmpty()) {
            System.out.println("Failed ORM N+1 call on page: " + "/orm");
            Logger.queueException(new Throwable("Failed ORM N+1 call on page: " + "/orm"));
            LogManager.shutdown();
            redirectAttributes.addFlashAttribute("error", "ORM N+1 query failed");
        } else {
            System.out.println("Called ORM N+1 call on page: " + "/orm");
            Logger.queueMessage("info", "Called ORM N+1 call on page: " + "/orm");
            LogManager.shutdown();
            redirectAttributes.addFlashAttribute("success", "ORM N+1 query successful!");
        }
        return "redirect:/orm";
    }

    @RequestMapping("/slowdb")
    public String slowDB() {
        List<Map<String, Object>> getRequest = new SQLService().callQuery(CustomSQL.FULLJOIN.getQuery());
        if (getRequest.isEmpty()) {
            System.out.println("Failed SQL query on page: " + "/slowdb");
            Logger.queueException(new Throwable("Failed SQL query on page: " + "/slowdb"));
            LogManager.shutdown();
        } else {
            System.out.println("Called SQL query on page: " + "/slowdb");
            Logger.queueMessage("info", "Called SQL query on page: " + "/slowdb");
            LogManager.shutdown();
        }
        return "slowdb";
    }

    @PostMapping("/slowdb")
    public String executeSlowDB(Model model, RedirectAttributes redirectAttributes) {
        Map<String, Object> result = new SQLService().executeSP("Ssec_proc");
        if (result.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "SlowDB SQL query failed");
            Logger.queueException(new Throwable("SlowDB SQL query failed"));
            LogManager.shutdown();
        } else {
            redirectAttributes.addFlashAttribute("success", "SQL stored procedure successfully executed!");
            Logger.queueMessage("info", "SQL stored procedure successfully executed!");
            LogManager.shutdown();
        }
        return "redirect:/slowdb";
    }
    
    @RequestMapping("/slowrequest")
    public String slowRequest(Model model, RedirectAttributes redirectAttributes) {
        List<Map<String, Object>> getRequest = new SQLService().callQuery(CustomSQL.LEFTJOIN.getQuery());
        if (getRequest.isEmpty()) {
            System.out.println("Failed SQL query on page: " + "/slowrequest");
            Logger.queueException(new Throwable("Failed SQL query on page: " + "/slowrequest"));
            LogManager.shutdown();
        } else {
            System.out.println("Called SQL query on page: " + "/slowrequest");
            Logger.queueMessage("info", "Called SQL query on page: " + "/slowrequest");
            LogManager.shutdown();
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
            Logger.queueException(new Throwable("Failed API call on page: " + "/slowrequest"));
            LogManager.shutdown();
        } else {
            redirectAttributes.addFlashAttribute("success", "SlowRequest API call successful!");
            System.out.println(result);
            System.out.println("Successful API call on page: " + "/slowrequest");
            Logger.queueMessage("info", "Successful API call on page: " + "/slowrequest");
            LogManager.shutdown();
        }
        return "redirect:/slowrequest";
    }

    @RequestMapping("/swallowedexception")
    public String swallowedException() {
        List<Map<String, Object>> getRequest = new SQLService().callQuery(CustomSQL.RIGHTJOIN.getQuery());
        if (getRequest.isEmpty()) {
            System.out.println("Failed SQL query on page: " + "/swallowedexception");
            Logger.queueException(new Throwable("Failed SQL query on page: " + "/swallowedexception"));
            LogManager.shutdown();
        } else {
            System.out.println("Called SQL query on page: " + "/swallowedexception");
            Logger.queueMessage("info", "Called SQL query on page: " + "/swallowedexception");
            LogManager.shutdown();
        }
        return "swallowedexception";
    }

    @PostMapping("/swallowedexception")
    public String executeSwallowedException(Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Map<String, Object>> getRequest = new SQLService().callQuery(CustomSQL.FULLJOIN.getQuery());
        } catch (Exception e) {
            System.out.println("Failed swallowed exception on page: " + "/swallowedexception");
            Logger.queueException("error", "Failed swallowed exception on page: " + "/swallowedexception", e);
            LogManager.shutdown();
            redirectAttributes.addFlashAttribute("error", "Failed swallowed exception!");
        }
        System.out.println("Successfully swallowed exception on page: " + "/swallowedexception");
        Logger.queueMessage("info", "Successfully swallowed exception on page: " + "/swallowedexception");
        LogManager.shutdown();
        redirectAttributes.addFlashAttribute("success", "Successful swallowed exception!");
        return "redirect:/swallowedexception";
    }

    @RequestMapping("/untracked")
    public String untracked() {
        return "untracked";
    }
}
