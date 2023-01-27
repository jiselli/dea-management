package br.com.dea.management.user.controller;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.dto.UserDto;
import br.com.dea.management.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value= "/user", method = RequestMethod.GET)
    public Page<UserDto> getUsers(@RequestParam Integer page,
                                  @RequestParam Integer pageSize) {

        Page<User> usersPaged = this.userService.findAllUserPaginated(page, pageSize);
        Page<UserDto> users = usersPaged.map(user -> UserDto.fromUser(user));
        return users;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Long id) {
        return this.userService.findUserById(id);
    }
}
