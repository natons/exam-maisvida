package exam.maisvida.med.br.service;

import exam.maisvida.med.br.model.User;
import exam.maisvida.med.br.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        return user;
    }

    public boolean save(User user){
        try {
            userRepository.save(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
