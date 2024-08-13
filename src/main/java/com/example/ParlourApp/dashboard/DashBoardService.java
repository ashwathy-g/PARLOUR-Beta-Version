package com.example.ParlourApp.Dashboard;

import com.example.ParlourApp.Rating.RatingRepository;
import com.example.ParlourApp.Revenue.RevenueRepository;
import com.example.ParlourApp.booking.BookingRepository;
import com.example.ParlourApp.cart.CartRepository;
import com.example.ParlourApp.dto.DashboardData;
import com.example.ParlourApp.employee.EmployeeRepository;
import com.example.ParlourApp.parlour.ParlourRepository;
import com.example.ParlourApp.parlour.ParlourService;
import com.example.ParlourApp.userbilling.UserBillingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class DashBoardService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ParlourService parlourService;

    @Autowired
    private ParlourRepository parlourRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserBillingRepository userBillingRepository;

    // Removed TransactionDetails from constructor and as a dependency

    public List<Long> getAllBookingIds() {
        return cartRepository.findAllBookingIds();
    }

    public List<LocalTime> getAllBookingTimes() {
        return cartRepository.findAllBookingTimes();
    }

    public List<BigDecimal> getAllBookingPrices() {
        return cartRepository.findAllBookingPrices();
    }

    public List<String> getAllBookingStatuses() {
        return userBillingRepository.findAllBookingStatuses();
    }

    public DashboardData getDashboardData() {
        DashboardData dashboardData = new DashboardData();
        // Populate dashboardData with necessary information
        return dashboardData;
    }
}
