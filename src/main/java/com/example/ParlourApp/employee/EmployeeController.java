package com.example.ParlourApp.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController
{
    @Autowired
    EmployeeService employeeService;
    @PreAuthorize("hasAuthority('ROLE_PARLOUR')")
    @PostMapping("/addEmployee")
    public ResponseEntity<EmployeeRegModel> addEmployee(@RequestParam String employeeName,
                                                        @RequestParam Long parlourId,
                                                        @RequestParam MultipartFile image)throws IOException
    {
        byte[] imageBytes = image.getBytes();
        EmployeeRegModel employeeRegModel = employeeService.addEmployee(employeeName, parlourId, imageBytes);
        return ResponseEntity.ok(employeeRegModel);
    }

    @GetMapping("/by-parlourName")
    public ResponseEntity<List<String>> getEmployeeNames(@RequestParam String parlourName) {
        List<String> employeeNames = employeeService.getEmployeeNamesByParlourName(parlourName);
        return ResponseEntity.ok(employeeNames);
    }
}



