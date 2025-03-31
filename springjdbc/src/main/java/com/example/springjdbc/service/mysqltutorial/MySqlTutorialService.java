package com.example.springjdbc.service.mysqltutorial;

import com.example.springjdbc.model.customer.Customer;
import com.example.springjdbc.repository.customer.CustomerRepository;
import com.example.springjdbc.repository.mysqltutorial.MySqlTutorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MySqlTutorialService {

    private final MySqlTutorialRepository mySqlTutorialRepository;

    public MySqlTutorialService(MySqlTutorialRepository mySqlTutorialRepository) {
        this.mySqlTutorialRepository = mySqlTutorialRepository;
    }

//    public List<Customer> getDistinct1() {
//        return mySqlTutorialRepository.getDistinct1();
//    }
}
