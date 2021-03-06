package com.example.vaccnow.controller;

import com.example.vaccnow.entity.VaccineBranchPK;
import com.example.vaccnow.model.VaccineBranchModel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface VaccineBranchController extends BaseController<VaccineBranchModel, VaccineBranchPK> {

    @PostMapping({ "{branchId}/{vaccineId}" })
    ResponseEntity<VaccineBranchModel> saveVaccineBranch(@PathVariable("branchId") Integer branchId,
            @PathVariable("vaccineId") Integer vaccineId);

    @DeleteMapping("{branchId}/{vaccineId}")
    ResponseEntity<Void> delete(@PathVariable("branchId") Integer branchId,
            @PathVariable("vaccineId") Integer vaccineId);

    @GetMapping("find/{branchId}/{vaccineId}")
    ResponseEntity<VaccineBranchModel> findById(@PathVariable("branchId") Integer branchId,
            @PathVariable("vaccineId") Integer vaccineId);

}
