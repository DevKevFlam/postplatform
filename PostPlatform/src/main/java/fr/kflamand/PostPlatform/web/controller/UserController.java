package fr.kflamand.PostPlatform.web.controller;

import fr.kflamand.PostPlatform.Dao.UserDao;
import fr.kflamand.PostPlatform.Exception.UserNotFoundException;
import fr.kflamand.PostPlatform.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    // Private fields

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    // Affiche la liste de tous les Users
    @GetMapping(value = "/Users")
    public List<User> listDesUsers() {

        List<User> users = userDao.findAll();
        if (users.isEmpty()) {

            log.info("Users not found");
            //TODO Throw exception
            throw new UserNotFoundException("List des Users Vide");
        }
        return users;
    }

    //Récuperer un user par son id
    @GetMapping(value = "/Users/{id}")
    @ResponseBody
    public User getOneUserById(@PathVariable long id) {

        Optional<User> userOp = userDao.findById(id);
        if (!userOp.isPresent()) {
            throw new UserNotFoundException("Le user correspondant à l'id " + id + " n'existe pas");
        }
        User user = userOp.get();
        if (user == null) {
            log.info("User not found");
            //TODO Throw exception
            throw new UserNotFoundException("Le user correspondant à l'id " + id + " n'existe pas");
        }
        return user;
    }

    @PostMapping("/Users")
    @ResponseBody
    public void addUser(@RequestBody User user) {

        log.info(user.toString());
        userDao.save(user);

    }


    @PatchMapping("/Users")
    @ResponseBody
    public void updateUser(@RequestBody User user) {

        log.info(user.toString());
        userDao.save(user);

    }

    @DeleteMapping("/Users/{id}")
    @ResponseBody
    public void deleteUser(@PathVariable long id) {

        User user = userDao.findUserByIdEquals(id);
        userDao.delete(user);

    }

}

