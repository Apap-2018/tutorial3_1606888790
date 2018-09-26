package com.apap.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
					  @RequestParam(value = "licenseNumber", required = true) String licenseNumber,
					  @RequestParam(value = "name", required = true) String name,
					  @RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";		
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		model.addAttribute("title","View Detail");
		return "view-pilot";
	}

	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("pilotList", archive);
		return "viewall-pilot";
	}

    @RequestMapping("/pilot/view/license-number/{licenseNumber}")
	public String viewPath(@PathVariable String licenseNumber, Model model) {
        PilotModel archieve = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
        if (archieve!=null) {
            model.addAttribute("pilot", archieve);
            model.addAttribute("title","View Detail");
            return "view-pilot";
        }else {
            String message = "Pilot dengan License Number "+licenseNumber+" tidak ditemukan";
            model.addAttribute("errorMessage",message);
            return "error";
        }
	}
	
    @RequestMapping("/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}")
	public String updatePath(@PathVariable String licenseNumber, @PathVariable String flyHour, Model model) {
        PilotModel archive = pilotService.updatePilotFH(licenseNumber, Integer.parseInt(flyHour));
        if (archive!=null) {
        	model.addAttribute("isUpdate", true);
            model.addAttribute("pilot", archive);
            model.addAttribute("title","Update Pilot");
            return "view-pilot";
        }else {
            String message = "Pilot dengan License Number "+licenseNumber+" tidak ditemukan, update data dibatalkan";
            model.addAttribute("errorMessage",message);
            return "error";
        }
	}
    
    @RequestMapping("/pilot/delete/id/{id}")
   	public String deletePath(@PathVariable String id, Model model) {
           boolean archive = pilotService.deletePilot(id);
           if (archive) {
               model.addAttribute("id", id );
               return "delete-pilot";
           }
           String message = "Pilot dengan Id "+id+" tidak ditemukan, penghapusan data dibatalkan";
           model.addAttribute("errorMessage",message);
           return "error";
           
   	}
}
