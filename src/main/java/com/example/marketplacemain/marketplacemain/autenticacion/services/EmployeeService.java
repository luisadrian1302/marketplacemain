package com.example.marketplacemain.marketplacemain.autenticacion.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Employee;
import com.example.marketplacemain.marketplacemain.autenticacion.repositories.EmployeRepository;

@Service
public class EmployeeService {

    @Autowired
    private  EmployeRepository repository;

    public List<Employee> getAll(){
     
        return (List<Employee>) repository.findAll();
    }
}
