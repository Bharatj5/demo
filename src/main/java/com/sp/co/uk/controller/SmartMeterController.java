package com.sp.co.uk.controller;

import com.sp.co.uk.domain.SmartMeterRead;
import com.sp.co.uk.dto.ReadDTO;
import com.sp.co.uk.dto.SmartMeterDTO;
import com.sp.co.uk.exception.NotFoundException;
import com.sp.co.uk.service.AccountService;
import com.sp.co.uk.service.SmartMeterReadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class SmartMeterController {

    @Autowired
    private SmartMeterReadService smartMeterReadService;

    @Autowired
    private AccountService accountService;

    private static final ModelMapper modelMapper = new ModelMapper();

    public SmartMeterController(SmartMeterReadService smartMeterReadService) {
        this.smartMeterReadService = smartMeterReadService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/api/smart/reads/{accountNumber}")
    public ResponseEntity<CollectionModel<EntityModel<SmartMeterDTO>>> getReads(
            @PathVariable long accountNumber) {

        // This should be separate service, as of now added it for validation
        accountService.findById(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account number not found"));

        List<SmartMeterRead> reads =
                smartMeterReadService.getSmartMeterReads(accountNumber);
        List<SmartMeterDTO> readDto = reads.stream()
                .map(read -> modelMapper.map(read, SmartMeterDTO.class))
                .collect(Collectors.toList());

        List<EntityModel<SmartMeterDTO>> reads1 = StreamSupport.stream(readDto.spliterator(), false)
                .map(read -> new EntityModel<>(read,
                        // linkTo(methodOn(SmartMeterController.class).getReads(read.getAccountNumber())).withSelfRel(),
                        linkTo(methodOn(SmartMeterController.class).findOne(read.getId())).withRel("read")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new CollectionModel<>(reads1,
                        linkTo(methodOn(SmartMeterController.class).getReads(accountNumber)).withSelfRel()));

    }


    @GetMapping("/api/smart/read/{id}")
    public ResponseEntity<EntityModel<ReadDTO>> findOne(@PathVariable long id) {
        return smartMeterReadService.findById(id)
                .map(read -> modelMapper.map(read, ReadDTO.class))
                .map(read -> new EntityModel<>(read,
                        linkTo(methodOn(SmartMeterController.class).findOne(read.getId())).withSelfRel()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
