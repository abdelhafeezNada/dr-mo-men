package org.abdelhafeez.dr_momen.controller;

import org.abdelhafeez.dr_momen.entity.Appointment;
import org.abdelhafeez.dr_momen.service.AppointmentService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

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
