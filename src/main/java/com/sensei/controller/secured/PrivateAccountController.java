package com.sensei.controller.secured;

import com.sensei.entity.RecordStatus;
import com.sensei.entity.User;
import com.sensei.entity.dto.RecordsContainerDto;
import com.sensei.service.RecordService;
import com.sensei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class PrivateAccountController {
    private final UserService userService;
    private final RecordService recordService;

    @Autowired
    public PrivateAccountController(RecordService recordService, UserService userService) {
        this.recordService = recordService;
        this.userService = userService;
    }

    @GetMapping
    public String getMainPage(Model model, @RequestParam(name = "filter", required = false) String filterMode) {
        RecordsContainerDto container = recordService.findAllRecords(filterMode);
        model.addAttribute("userName",container.getUserName());
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        return "private/account-page";
    }



    @PostMapping( "/add-record")
    public String addRecord(@RequestParam  String title){
        recordService.saveRecord(title);
        return "redirect:/account";
    }

    @PostMapping( "/make-record-done")
    public String makeRecordDone(@RequestParam  int id,
                                 @RequestParam(name = "filter",required = false) String filterMode){
        recordService.updateRecordStatus(id,RecordStatus.DONE);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode: "");
    }

    @PostMapping( "/delete-record")
    public String deleteRecord(@RequestParam  int id,
                               @RequestParam(name = "filter",required = false) String filterMode){
        recordService.deleteRecord(id);
        return "redirect:/account" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode: "");
    }


}
