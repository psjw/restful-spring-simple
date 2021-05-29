package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
//HATEOA
@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {

        List<User> users = service.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);//id:빈의 이름 (UserInfo)
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters); //필터 적용

        return mapping;
    }

    // GET /admin/users/1 or /admin/users/10 -> String -> /admin/v1/users/1 or /admin/v1/users/10
    //@GetMapping("/users/{id}")
//    @GetMapping("/v1/users/{id}")
//    @GetMapping(value = "/users/{id}/",params = "version=1")
//    @GetMapping(value ="/users/{id}",headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","ssn");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo",filter);//id:빈의 이름 (UserInfo)
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters); //필터 적용
        return mapping;
    }

//    @GetMapping("/v2/users/{id}")
 //   @GetMapping(value = "/users/{id}/",params = "version=2")
   //@GetMapping(value="/users/{id}",headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv2+json") //MimeType
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        // User  -> User2 (copy)
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2); //BeanUtils -> 빈들간의 관련된 작업 지원
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id","name","joinDate","grade");
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2",filter);//id:빈의 이름 (UserInfo)
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters); //필터 적용
        return mapping;
    }
}
