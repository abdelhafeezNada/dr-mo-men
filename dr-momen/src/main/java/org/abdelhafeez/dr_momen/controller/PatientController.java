package org.abdelhafeez.dr_momen.controller;

import java.util.regex.Pattern;

import org.abdelhafeez.dr_momen.entity.Patient;
import org.abdelhafeez.dr_momen.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        ModelAndView modelAndView = new ModelAndView("patient_form");
        modelAndView.addObject("patient", new Patient());
        return modelAndView;
    }

    @GetMapping("/patients/{id}/edit")
    public ModelAndView showEditPatientForm(@PathVariable Long id) {
        Patient patient = patientService.findById(id);
        ModelAndView modelAndView = new ModelAndView("patient_form");
        modelAndView.addObject("patient", patient);
        return modelAndView;
    }

    @PostMapping("/patients")
    public String createPatient(@ModelAttribute Patient patient, BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (!isValidPhone(patient.getPhone())) {
            result.rejectValue("phone", "error.patient",
                    "Phone must start with 01, be 11 characters long, and contain only digits.");
        }

        if (result.hasErrors()) {
            return "new_patient";
        }

        if (patient.getId() != null)
            patientService.update(patient);
        else
            patientService.save(patient);

        int lastPage = (int) Math.ceil((double) patientService.count() / 10) - 1;
        return "redirect:/patients?page=" + lastPage;
    }

    @GetMapping("/patients/search")
    public ModelAndView searchPatients(@RequestParam String name, @RequestParam(defaultValue = "0") int page) {
        Page<Patient> patientPage = patientService.findByNameContaining(name, page);
        ModelAndView modelAndView = new ModelAndView("patients");
        modelAndView.addObject("patients", patientPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", patientPage.getTotalPages());
        modelAndView.addObject("searchName", name); // Pass the search name to the template
        return modelAndView;
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^01\\d{9}$";
        return Pattern.matches(phonePattern, phone);
    }
}
