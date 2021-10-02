package com.example.vaccnow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.vaccnow.entity.Branch;
import com.example.vaccnow.entity.ScheduleVaccination;
import com.example.vaccnow.mapping.BranchMapping;
import com.example.vaccnow.model.BranchModel;
import com.example.vaccnow.service.BranchService;
import com.example.vaccnow.service.ScheduleVaccinationService;
import com.example.vaccnow.util.PaymentMethodEnum;
import com.example.vaccnow.util.VaccNowUtils;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = { Application.class })
class ScheduledVaccinationTests {

        private @Autowired MockMvc mockMvc;
        private @Autowired ScheduleVaccinationService scheduleVaccinationService;
        private @Autowired BranchService branchService;
        private @Autowired BranchMapping branchMapper;

        private Branch bModel;

        @Test
        @Order(1)
        void test_scheduleVaccination_By_payment() throws Exception {
                mockMvc.perform(get("/scheduleVaccination/vaccination/CASH/temp1@domain.net"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.paymentMethod", Matchers.equalTo("CASH")))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(1)));
        }

        @Test
        @Order(2)
        void test_scheduleVaccination() throws Exception {
                Long from = VaccNowUtils.getTimeInMillis(new Date());
                Long to = VaccNowUtils.getTimeInMillis(
                                VaccNowUtils.add15MinuteToDate(VaccNowUtils.add15MinuteToDate(new Date())));

                scheduleVaccinationService.scheduleVaccinationByPaymentMethod(PaymentMethodEnum.FAWRY,
                                "temp11@domain.net");
                scheduleVaccinationService.scheduleVaccinationByPaymentMethod(PaymentMethodEnum.CREDIT,
                                "temp12@domain.net");

                mockMvc.perform(get("/scheduleVaccination/confirmed/" + from + "/" + to)).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(2)));
        }

        @Test
        @Order(3)
        void test_applied_vaccination_by_branch() throws Exception {
                bModel = List.<BranchModel>of(BranchModel.builder().email("E1").location("L1").name("B1").phone("P1")
                                .workStartDate(new Date()).phone("P1").workStartDate(new Date())
                                .workEndDate(VaccNowUtils.add15MinuteToDate(new Date())).build()).stream()
                                .map(branchMapper::mapToEntity).map(branchService::create).findFirst().get();

                scheduleVaccinationService.create(ScheduleVaccination.builder().branch(bModel).email("ttemp@domain.net")
                                .vacDesc("vacDesc").vacTime(VaccNowUtils.add15MinuteToDate(new Date())).build());

                mockMvc.perform(get("/scheduleVaccination/appliedByBranchId/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                                .andExpect(jsonPath("$[0].branch.id", Matchers.equalTo(1)));
        }

        @AfterEach
        private void tearDown() throws Exception {
                scheduleVaccinationService.deleteAll();
                branchService.deleteAll();
        }
}