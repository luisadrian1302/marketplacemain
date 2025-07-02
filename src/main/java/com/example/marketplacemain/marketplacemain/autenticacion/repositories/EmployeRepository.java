package com.example.marketplacemain.marketplacemain.autenticacion.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.marketplacemain.marketplacemain.autenticacion.entities.Employee;

public interface  EmployeRepository extends CrudRepository<Employee, Long> {

}
