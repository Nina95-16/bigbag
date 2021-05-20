package am.itspace.bigbag.service;

import am.itspace.bigbag.exception.ResourceNotFoundException;
import am.itspace.bigbag.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    void saveUser(User user, MultipartFile image);

    List<User> allUsers();

    User getById(int id) throws ResourceNotFoundException;

    void delete(int id);

    Optional<User> findByEmail(String email);

    List<User> findByName(String name);
}
