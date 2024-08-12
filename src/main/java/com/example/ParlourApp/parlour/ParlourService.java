package com.example.ParlourApp.parlour;

import com.example.ParlourApp.admin.AdminRegModel;
import com.example.ParlourApp.dto.EmployeeDto;
import com.example.ParlourApp.dto.ItemDto;
import com.example.ParlourApp.dto.ParlourDetails;
import com.example.ParlourApp.dto.ParlourDetailsDTO;
import com.example.ParlourApp.employee.EmployeeRegModel;
import com.example.ParlourApp.employee.EmployeeRepository;
import com.example.ParlourApp.exception.ResourceNotFoundException;
import com.example.ParlourApp.items.ItemRegModel;
import com.example.ParlourApp.items.ItemRepository;
import com.example.ParlourApp.jwt.CustomerUserDetailsService;
import com.example.ParlourApp.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@CrossOrigin
@Service
@Slf4j
public class ParlourService
{
    @Autowired
    private ParlourRepository parlourRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    public ParlourRegModel registerParlour(ParlourRegModel parlourRegModel) {
        parlourRegModel.setPassword(passwordEncoder.encode(parlourRegModel.getPassword()));
        parlourRegModel.getRoles().add("ROLE_PARLOUR");
        parlourRegModel.setStatus(0);
        return parlourRepository.save(parlourRegModel);
    }

    public  String authenticate(String email,String password) {
        UserDetails userDetails = customerUserDetailsService.loadUserByUsername(email);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return jwtUtil.generateToken(userDetails);
        } else {
            return null;
        }


    }
    public ParlourRegModel getParlourByEmailAndPassword(String email, String password) {
        return parlourRepository.findByEmailAndPassword(email, password);
    }

    public ParlourRegModel getParlourById(Long id) {
        return parlourRepository.findById(id).orElseThrow(() -> new RuntimeException("Parlour not found with id: " + id));
    }

    public boolean updateParlour(Long id, ParlourRegModel parlourDetails) {
        Optional<ParlourRegModel> optionalParlour = parlourRepository.findById(id);

        if (optionalParlour.isPresent()) {
            ParlourRegModel existingParlour = optionalParlour.get();

            existingParlour.setParlourName(parlourDetails.getParlourName());
            existingParlour.setPhoneNumber(parlourDetails.getPhoneNumber());
            existingParlour.setPassword(parlourDetails.getPassword());
            existingParlour.setEmail(parlourDetails.getEmail());
            if (parlourDetails.getImage() != null) {
                existingParlour.setImage(parlourDetails.getImage());
            }
            existingParlour.setLicenseNumber(parlourDetails.getLicenseNumber());

            if (parlourDetails.getLicenseImage() != null) {
                existingParlour.setLicenseImage(parlourDetails.getLicenseImage());
            }
            existingParlour.setRatings(parlourDetails.getRatings());
            existingParlour.setLocation(parlourDetails.getLocation());
            existingParlour.setDescription(parlourDetails.getDescription());
            existingParlour.setStatus(0);

            parlourRepository.save(existingParlour);
            return true;
        } else {
            return false;
        }
    }

    public ParlourRegModel getParlourByName(String parlourName) {
        return parlourRepository.findByParlourName(parlourName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parlour name"));
    }

    public List<EmployeeRegModel> getEmployeesByParlourId(Long parlourId) {
        return employeeRepository.findByParlourId_Id(parlourId);
    }

    public ParlourDetails getParlourDetails(String parlourName) {
        ParlourRegModel parlour = getParlourByName(parlourName);
        List<EmployeeRegModel> employees = getEmployeesByParlourId(parlour.getId());
        return new ParlourDetails(parlour.getParlourName(), parlour.getPhoneNumber(), parlour.getEmail(), employees);
    }
    public List<ParlourRegModel> getAllParlours()
    {
        return parlourRepository.findAll();
    }
    public Optional<ParlourRegModel> getParlourDetails(Long parlourId) {
        return parlourRepository.findById(parlourId);
    }

    public ResponseEntity<List<ParlourDetailsDTO>> getParlourAllDetails(Long id) {
        List<ParlourDetailsDTO>parlourDetailsDTOList=new ArrayList<>();
        Optional<ParlourRegModel>parlourRegModelOptional=parlourRepository.findById(id);
        if (parlourRegModelOptional.isPresent()){
            ParlourRegModel parlourRegModel = parlourRegModelOptional.get();
            ParlourDetailsDTO parlourDetailsDTO = new ParlourDetailsDTO();
            parlourDetailsDTO.setId(parlourRegModel.getId());
            parlourDetailsDTO.setParlourName(parlourRegModel.getParlourName());
            parlourDetailsDTO.setPhoneNumber(parlourRegModel.getPhoneNumber());
            parlourDetailsDTO.setEmail(parlourRegModel.getEmail());
            parlourDetailsDTO.setImage(parlourRegModel.getImage());
            parlourDetailsDTO.setRatings(parlourRegModel.getRatings());
            parlourDetailsDTO.setStatus(parlourRegModel.getStatus());
            parlourDetailsDTO.setDescription(parlourRegModel.getDescription());
            parlourDetailsDTO.setLocation(parlourRegModel.getLocation());

            List<EmployeeRegModel>employeeRegModelList=employeeRepository.findByParlourId_Id(id);
            List<EmployeeDto>employeeDtoList = new ArrayList<>();
            if (!employeeRegModelList.isEmpty()){
                for (EmployeeRegModel employeeRegModel : employeeRegModelList){
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setId(employeeRegModel.getId());
                    employeeDto.setEmployeeName(employeeRegModel.getEmployeeName());
                    employeeDto.setImage(employeeRegModel.getImage());
                    employeeDtoList.add(employeeDto);
                    parlourDetailsDTO.setEmployees(employeeDtoList);
                }

                List<ItemRegModel>itemRegModelList=itemRepository.findByParlourId_Id(id);
                List<ItemDto>itemDtoList=new ArrayList<>();
                if (!itemRegModelList.isEmpty())
                {
                    for (ItemRegModel itemRegModel:itemRegModelList)
                    {
                        ItemDto itemDto=new ItemDto();
                        itemDto.setId(itemRegModel.getId());
                        itemDto.setItemName(itemRegModel.getItemName());
                        itemDto.setItemImage(itemRegModel.getItemImage());
                        itemDto.setCategoryId(itemRegModel.getCategoryId());
                        itemDto.setSubCategoryId(itemRegModel.getSubCategoryId());
                        itemDto.setSubSubCategoryId(itemRegModel.getSubSubCategoryId());
                        itemDto.setPrice(itemRegModel.getPrice());
                        itemDto.setAvailability(Boolean.parseBoolean(itemRegModel.getAvailability()));
                        itemDto.setDescription(itemRegModel.getDescription());
                        itemDto.setServiceTime(itemRegModel.getServiceTime());
                        itemDtoList.add(itemDto);

                    }
                    parlourDetailsDTO.setItems(itemDtoList);
                }
            }
            parlourDetailsDTOList.add(parlourDetailsDTO);

            //parlourDetailsDTO.setEmployees(employeeDtoList);
            //parlourDetailsDTOList.add(parlourDetailsDTO);
        }
        return new ResponseEntity<>(parlourDetailsDTOList,HttpStatus.OK);
    }
}




