package com.example.ParlourApp.employee;

import com.example.ParlourApp.parlour.ParlourRegModel;
import com.example.ParlourApp.parlour.ParlourRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeService
{
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ParlourRepository parlourRepository;

    public EmployeeRegModel addEmployee(String employeeName, Long parlourId, byte[] image) {
        ParlourRegModel parlourRegModel = parlourRepository.findById(parlourId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parlour ID"));
        EmployeeRegModel employeeRegModel = new EmployeeRegModel(employeeName, parlourRegModel);
        employeeRegModel.setImage(image);
        log.info("Adding employee: {}", employeeRegModel);
        return employeeRepository.save(employeeRegModel);
    }

    public List<String> getEmployeeNamesByParlourName(String parlourName) {
        List<String> employeeNames = employeeRepository.findEmployeeNamesByParlourName(parlourName);
        log.info("Retrieved employee names for parlour '{}': {}", parlourName, employeeNames);
        return employeeNames;
    }
}

