package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.model.Credential;
import cheboksarov.blps_lab4.model.SiteUser;
import cheboksarov.blps_lab4.repository.SiteUserRepository;
import cheboksarov.blps_lab4.service.CredentialService;
import cheboksarov.blps_lab4.service.SiteUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SiteUserServiceImplement implements SiteUserService {
    private SiteUserRepository siteUserRepository;
    private CredentialService credentialService;

    @Override
    public SiteUser createNewUser(SiteUser siteUser) {
        return siteUserRepository.save(siteUser);
    }

    @Override
    public List<SiteUser> getAllUsers() {
        return siteUserRepository.findAll();
    }

    @Override
    public SiteUser findUserById(Long userId) throws UsernameNotFoundException{
        Optional<SiteUser> userOp =  siteUserRepository.findById(userId);
        if(userOp.isPresent()){
            return userOp.get();
        }
        throw new UsernameNotFoundException("No User With such Id: " + userId);
    }

    @Override
    public ResponseEntity<?> makePayment(Double amount, String username) throws Exception {
        Credential credential = credentialService.findByUserName(username);
        Optional<SiteUser> siteUser = siteUserRepository.findByCredential(credential);
        SiteUser user;
        if ( siteUser.isPresent()){
            user = siteUser.get();
        }
        else {
            throw new Exception("No such user");
        }
        Double balance = user.getBalance();
        user.setBalance(balance+ amount);
        siteUserRepository.save(user);
        return new ResponseEntity<>("Balance updated: " + user.getBalance(), HttpStatus.OK);
    }

    @Override
    public SiteUser findByCredentialId(Credential credential) throws Exception {
        Optional<SiteUser> siteUserOp =  siteUserRepository.findByCredential(credential);
        if (siteUserOp.isPresent()){
            return siteUserOp.get();
        } else{
            throw new Exception("No such user with this credentials: " + credential);
        }
    }

    @Override
    public ResponseEntity<?> getMyBalance() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        SiteUser siteUser;
        try {
            Credential credential = credentialService.findByUserName(name);
            siteUser = findByCredentialId(credential);
        }catch (Exception e){
            return new ResponseEntity<>("Cant check your balance(:" + e.getMessage(),
            HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Your Balance: " + siteUser.getBalance(), HttpStatus.OK);
    }
}
