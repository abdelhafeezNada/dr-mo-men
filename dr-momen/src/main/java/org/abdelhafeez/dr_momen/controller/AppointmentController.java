package org.abdelhafeez.dr_momen.controller;

import org.abdelhafeez.dr_momen.entity.Appointment;
import org.abdelhafeez.dr_momen.entity.Patient;
import org.abdelhafeez.dr_momen.service.AppointmentService;
import org.abdelhafeez.dr_momen.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final PatientService patientService;

    @GetMapping("appointments/patient/{patientId}/form")
    public String showAppointmentForm(@PathVariable Long patientId, @RequestParam(required = false) Long id,
            Model model) {
        if (id != null) {
            Appointment appointment = appointmentService.findById(id);
            model.addAttribute("appointment", appointment);
        } else {
            Appointment appointment = new Appointment();
            appointment.setPatient(Patient.builder().id(patientId).build());
            model.addAttribute("appointment", appointment);
        }
        return "appointment_form";
    }

    @PostMapping("appointments/save")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        Patient patient = patientService.findById(appointment.getPatient().getId());
        appointment.setPatient(patient);
        appointmentService.save(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/appointments")
    private ModelAndView listAppointments(@RequestParam(defaultValue = "0") int page) {
        Page<Appointment> appointmentPage = appointmentService.findAll(page);
        ModelAndView modelAndView = new ModelAndView("appointments");
        modelAndView.addObject("appointments", appointmentPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", appointmentPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/appointments/by-patient")
    private ModelAndView listPatientAppointments(@RequestParam(defaultValue = "0") int page, @RequestParam Long id) {
        Page<Appointment> appointmentPage = appointmentService.findByPatientId(id, page);
        ModelAndView modelAndView = new ModelAndView("appointments");
        modelAndView.addObject("appointments", appointmentPage.getContent());
        modelAndView.addObject("currentPage", page);
        modelAndView.addObject("totalPages", appointmentPage.getTotalPages());
        modelAndView.addObject("patientId", id); // Pass the search name to the template
        return modelAndView;
    }

}
