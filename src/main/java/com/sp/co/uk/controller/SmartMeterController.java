package com.sp.co.uk.controller;

import com.sp.co.uk.domain.SmartMeterRead;
import com.sp.co.uk.dto.ReadDTO;
import com.sp.co.uk.dto.SmartMeterReadDTO;
import com.sp.co.uk.exception.NotFoundException;
import com.sp.co.uk.service.AccountService;
import com.sp.co.uk.service.SmartMeterReadService;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class SmartMeterController {

    private SmartMeterReadService smartMeterReadService;

    private AccountService accountService;

    private static final ModelMapper modelMapper = new ModelMapper();

    public SmartMeterController(SmartMeterReadService smartMeterReadService,
                                AccountService accountService) {
        this.smartMeterReadService = smartMeterReadService;
        this.accountService = accountService;
    }

    @GetMapping("/api/smart/reads/{accountNumber}")
    public ResponseEntity<CollectionModel<EntityModel<SmartMeterReadDTO>>> getReads(
            @PathVariable long accountNumber) {

        // This should be separate service, as of now added it for validation
        accountService.findById(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account number not found"));

        List<SmartMeterRead> reads = smartMeterReadService.getSmartMeterReads(accountNumber);

        List<EntityModel<SmartMeterReadDTO>> readsResponse =
                reads.stream()
                        .map(read -> modelMapper.map(read, SmartMeterReadDTO.class))
                        .map(read -> new EntityModel<>(read,
                                //linkTo(methodOn(SmartMeterController.class).getReads(read.getAccountNumber())).withSelfRel(),
                                linkTo(methodOn(SmartMeterController.class).findOne(read.getId())).withRel("read")))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(
                new CollectionModel<>(readsResponse,
                        linkTo(methodOn(SmartMeterController.class).getReads(accountNumber)).withSelfRel(),
                        linkTo(methodOn(AccountController.class).findOne(accountNumber)).withRel("account")));

    }


    @GetMapping("/api/smart/read/{id}")
    public ResponseEntity<EntityModel<ReadDTO>> findOne(@PathVariable long id) {
        return smartMeterReadService.findById(id)
                .map(read -> modelMapper.map(read, ReadDTO.class))
                .map(read -> new EntityModel<>(read,
                        linkTo(methodOn(SmartMeterController.class).
                                findOne(read.getId())).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> {
                    return new NotFoundException("reads for number " + id + " not found");
                });
    }
}
