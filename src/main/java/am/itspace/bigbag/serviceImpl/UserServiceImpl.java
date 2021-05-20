package am.itspace.bigbag.serviceImpl;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.User;
import am.itspace.bigbag.repository.UserRepository;
import am.itspace.bigbag.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void saveUser(User user, MultipartFile image) {
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> allUsers() {
        List<User> all = userRepository.findAll();
        return all;
    }

    @Override
    public User getById(int id) throws ResourceNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with" + id + " does not exist"));
        User one = userRepository.getOne(id);
        return one;
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

}
