package com.example.CrowdFunding.CrowdFundingBackend.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CrowdFunding.CrowdFundingBackend.model.Payment;
import com.example.CrowdFunding.CrowdFundingBackend.service.implementation.PaymentServiceImplementation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
   
    @Autowired
    private PaymentServiceImplementation paymentServiceImplementation;

    //swagger documentation
	@Operation(summary = "Get Payment details", description = "This is the api endpoint for retriving donor's payment details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // Getting all the payment details.
    @GetMapping
    public ResponseEntity<Iterable<Payment>> getAllPayments() {
        try {
            Iterable<Payment> payment = paymentServiceImplementation.getAllPayments();
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //swagger documentation
	@Operation(summary = "Get Payment details by id", description = "This is the api endpoint for retriving donor's payment details by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // getting payment detaisl by the id
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        try {
            // get the payment by id
            Optional<Payment> payment = paymentServiceImplementation.getPaymentById(id);
            return payment.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
        
    }

    //swagger documentation
	@Operation(summary = "Donor payment", description = "This is the api endpoint for donor when they are donating to the campaign")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Created"),
            @ApiResponse(responseCode = "500", description = "Unexpected Error Occured")
    })
    // User makes the payment.
    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody Payment payment) {
        try {
            // Save the payment
            Payment savedPayment = paymentServiceImplementation.createPayment(payment);

            if(savedPayment != null) {
                ResponseEntity<String> ok = ResponseEntity.ok("Payment Successful.");
                return ok;
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } 
    }

}
