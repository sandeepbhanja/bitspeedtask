package com.bitespeed.bitespeed;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class BitespeedController {

    BitespeedService bitespeedService;

    BitespeedController(BitespeedService bitespeedService) {
        this.bitespeedService = bitespeedService;
    }

    @GetMapping("/identify")
    public ResponseEntity<Map<String, Object>> identify(@RequestBody Map<String, String> request) {
        Map<String, Object> response = bitespeedService.getUserDetails(request.get("phoneNumber"),
                request.get("emailId"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
