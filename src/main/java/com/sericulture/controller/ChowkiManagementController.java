package com.sericulture.controller;


import com.sericulture.model.ChowkiManagement;
import com.sericulture.model.api.AddChowkiResponse;
import com.sericulture.model.api.ChowkiManagementRequest;
import com.sericulture.service.ChowkiManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chowkimanagement")
@Slf4j
public class ChowkiManagementController {

    @Autowired
    private ChowkiManagementService chowkiManagementService;

    @PostMapping("/add-info")
    public AddChowkiResponse insertData(@RequestBody ChowkiManagementRequest ChowkiManagementRequest) {
        return chowkiManagementService.insertData(ChowkiManagementRequest);
    }

    @PostMapping("/update-info")
    public AddChowkiResponse updateData(@RequestBody ChowkiManagement chowkiManagement) {
        return chowkiManagementService.updateData(chowkiManagement);
    }

    @GetMapping("/get-info")
    public List<ChowkiManagement> getAllChowkiManagement() {
        return chowkiManagementService.findAll();
    }

    @GetMapping("/get-info-by-id/{chowki_id}")
    public Optional<ChowkiManagement> getByChowkiId(@PathVariable Integer chowki_id) {
        return chowkiManagementService.getById(chowki_id);
    }

    @GetMapping("/delete-info/{id}")
    public AddChowkiResponse deleteDataById(@PathVariable Integer id) {
        return chowkiManagementService.deleteById(id);
    }
}

