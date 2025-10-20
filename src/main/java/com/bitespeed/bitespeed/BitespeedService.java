package com.bitespeed.bitespeed;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface BitespeedService {
    Map<String, Object> getUserDetails(String phoneNumber, String emailId);
}
