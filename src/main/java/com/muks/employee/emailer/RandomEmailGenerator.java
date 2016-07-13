package com.muks.employee.emailer;

import org.springframework.stereotype.Service;

/**
 * Created by 15692 on 23/06/16.
 */

@Service
public class RandomEmailGenerator implements EmailGenerator {

    public String generate() {
        return "feedback@yoursite.com";
    }

}