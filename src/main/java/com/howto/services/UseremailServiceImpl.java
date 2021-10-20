package com.howto.services;

import com.howto.exceptions.ResourceNotFoundException;
import com.howto.models.User;
import com.howto.models.Useremail;
import com.howto.repository.UseremailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "useremailService")
public class UseremailServiceImpl implements UseremailService{
    @Autowired
    private UseremailRepository useremailRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private HelperFunctions helperFunctions;

    @Override
    public List<Useremail> findAll() {
        List<Useremail> list = new ArrayList<>();
        useremailRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Useremail findUseremailById(long id) {
        return useremailRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Useremail with id " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (useremailRepository.findById(id).isPresent()) {
            if (helperFunctions.isAuthorizedToMakeChange(useremailRepository.findById(id)
                    .get()
                    .getUser()
                    .getUsername()))
            {
                useremailRepository.deleteById(id);
            }
        } else {
            throw new ResourceNotFoundException("Useremail with id " + id + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Useremail update(long useremailid, String emailaddress) {
        if (useremailRepository.findById(useremailid).isPresent()) {
            if (helperFunctions.isAuthorizedToMakeChange(useremailRepository.findById(useremailid)
                    .get()
                    .getUser()
                    .getUsername()))
            {
                Useremail useremail = findUseremailById(useremailid);
                useremail.setUseremail(emailaddress.toLowerCase());
                return useremailRepository.save(useremail);
            } else {
                throw new ResourceNotFoundException("This user is not authorized to make change");
            }
        } else {
            throw new ResourceNotFoundException("Useremail with id " + useremailid + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Useremail save(long userid, String emailaddress) {
        User currentUser = userService.findUserById(userid);
        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername())) {
            Useremail newUserEmail = new Useremail(currentUser, emailaddress);
            return useremailRepository.save(newUserEmail);
        } else {
            throw new ResourceNotFoundException("This user is not authorized to make change");
        }
    }
}
