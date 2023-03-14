package com.epam.esm;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@EnableAutoConfiguration
@ActiveProfiles("test")
@ComponentScan("com.epam.esm")
public class TestConfig {




}
