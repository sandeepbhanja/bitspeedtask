package com.bitespeed.bitespeed.Implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.bitespeed.bitespeed.BitespeedRepository;
import com.bitespeed.bitespeed.BitespeedService;
import com.bitespeed.bitespeed.UserDetails;
import com.bitespeed.bitespeed.UserDetails.LinkPrecedence;

@Service
public class BitespeedServiceImplementation implements BitespeedService {

    BitespeedRepository bitespeedRepository;

    BitespeedServiceImplementation(BitespeedRepository bitespeedRepository) {
        this.bitespeedRepository = bitespeedRepository;
    }

    @Override
    public Map<String, Object> getUserDetails(String phoneNumber, String emailId) {
        Long userId = checkIfUserExists(phoneNumber, emailId);
        List<UserDetails> user_details = bitespeedRepository.findUsers(emailId, phoneNumber);
        List<String> email = new ArrayList<>();
        List<String> phoneNumbers = new ArrayList<>();
        List<Long> secondaryContactIds = new ArrayList<>();
        Long primaryContatctId = null;
        if (user_details.get(0).getLinkPrecedence() == LinkPrecedence.SECONDARY) {
            UserDetails primaryUser = bitespeedRepository.findPrimaryUser(user_details.get(0).getLinkedId());
            primaryContatctId = primaryUser.getId();
            email.add(primaryUser.getEmailId());
            phoneNumbers.add(primaryUser.getPhoneNumber());
            if (user_details.size() > 1) {
                bitespeedRepository.updateUserAsSecondary(userId, primaryContatctId);
                if (!email.contains(emailId)) {
                    email.add(emailId);
                }
                if (!phoneNumbers.contains(phoneNumber)) {
                    phoneNumbers.add(phoneNumber);
                }
                secondaryContactIds.add(userId);
            }
        } else {
            primaryContatctId = user_details.get(0).getId();
            email.add(user_details.get(0).getEmailId());
            phoneNumbers.add(user_details.get(0).getPhoneNumber());
            if (user_details.size() > 1) {
                bitespeedRepository.updateUserAsSecondary(userId, primaryContatctId);
                if (!email.contains(emailId)) {
                    email.add(emailId);
                }
                if (!phoneNumbers.contains(phoneNumber)) {
                    phoneNumbers.add(phoneNumber);
                }
                secondaryContactIds.add(userId);
            }
        }
        return createResponseObject(email, phoneNumbers, primaryContatctId, secondaryContactIds);
    }

    private Map<String, Object> createResponseObject(
            List<String> email,
            List<String> phoneNumbers,
            Long primaryContatctId, List<Long> secondaryContactIds) {
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("primaryContactId", primaryContatctId);
        response.put("emails", email);
        response.put("phoneNumbers", phoneNumbers);
        response.put("secondaryContactIds", secondaryContactIds);
        return Map.of("contact", response);
    }

    private Long checkIfUserExists(String phoneNumber, String emailId) {
        UserDetails userToAdd = new UserDetails();
        UserDetails exactUser = bitespeedRepository.findExactUser(emailId, phoneNumber);
        if (exactUser == null) {
            userToAdd.setEmailId(emailId);
            userToAdd.setPhoneNumber(phoneNumber);
            userToAdd.setCreatedAt(java.time.LocalDateTime.now());
            userToAdd.setDeletedAt(null);
            userToAdd.setLinkPrecedence(LinkPrecedence.PRIMARY);
            userToAdd.setLinkedId(null);
            userToAdd.setUpdatedAt(java.time.LocalDateTime.now());
            bitespeedRepository.save(userToAdd);
            return userToAdd.getId();
        }
        return exactUser.getId();
    }

}
