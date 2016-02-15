package alex.test.controller;

import alex.test.domain.UserPhone;
import alex.test.service.UserPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("user_phone")
public class UserPhoneController {
    @Autowired
    private UserPhoneService userPhoneService;

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<String> add(@RequestBody @Valid UserPhone userPhone) {
        userPhoneService.save(userPhone);
        return new ResponseEntity("{val: some}", HttpStatus.OK);
    }

}
