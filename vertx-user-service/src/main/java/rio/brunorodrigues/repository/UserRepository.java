package rio.brunorodrigues.repository;

import rio.brunorodrigues.domain.User;

import javax.enterprise.context.RequestScoped;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RequestScoped
public class UserRepository {

    private static List<User> userList = Arrays.asList();

    public Stream<User> findAll() {
        Stream<User> userStream = Stream.of(userList.toArray(new User[userList.size()]));
        return userStream;
    }
}
