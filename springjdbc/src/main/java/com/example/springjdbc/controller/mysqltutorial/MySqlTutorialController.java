package com.example.springjdbc.controller.mysqltutorial;

import com.example.springjdbc.model.customer.Customer;
import com.example.springjdbc.service.customer.CustomerService;
import com.example.springjdbc.service.mysqltutorial.MySqlTutorialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MySqlTutorialController {
    private final MySqlTutorialService mySqlTutorialService;


//    @GetMapping()
//    public ResponseEntity<List<Customer>> getDistinct1() {
//        // Call the service to get all customers and directly store the return value
//        List<Customer> customers = mySqlTutorialService.getDistinct1();
//
//        // Return the list of customers with a status of OK
//        return new ResponseEntity<>(customers, HttpStatus.OK);
//    }
}
