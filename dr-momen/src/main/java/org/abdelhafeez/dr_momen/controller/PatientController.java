package org.abdelhafeez.dr_momen.controller;

import java.util.regex.Pattern;

import org.abdelhafeez.dr_momen.entity.Patient;
import org.abdelhafeez.dr_momen.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/patients")
    public ModelAndView listPatients(@RequestParam(defaultValue = "0") int page) {
        Page<Patient> patientPage = patientService.findPage(page);
        ModelAndView modelAndView = new ModelAndView("patients");
        modelAndView.addObject("patients", patientPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", patientPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/patients/new")
    public ModelAndView showCreatePatientForm() {
        ModelAndView modelAndView = new ModelAndView("new_patient");
        modelAndView.addObject("patient", new Patient());
        return modelAndView;
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^01\\d{9}$";
        return Pattern.matches(phonePattern, phone);
    }
}
