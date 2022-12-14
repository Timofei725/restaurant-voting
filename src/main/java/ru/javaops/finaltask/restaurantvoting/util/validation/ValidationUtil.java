package ru.javaops.finaltask.restaurantvoting.util.validation;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.javaops.finaltask.restaurantvoting.HasId;
import ru.javaops.finaltask.restaurantvoting.error.IllegalRequestDataException;
import ru.javaops.finaltask.restaurantvoting.error.NoExistentRestaurantException;
import ru.javaops.finaltask.restaurantvoting.error.VotingException;
import ru.javaops.finaltask.restaurantvoting.model.Restaurant;
import ru.javaops.finaltask.restaurantvoting.model.Vote;

import java.util.Optional;

import static ru.javaops.finaltask.restaurantvoting.web.GlobalExceptionHandler.EXCEPTION_LATE_VOTE;


@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }


    public static void checkRestaurantId(Optional<Restaurant> restaurant, int id) {
        if (restaurant.isEmpty()) {
            throw new NoExistentRestaurantException(String.format("Restaurant with id = %s doesn't exist", id));
        }
    }

    public static void checkVote(Optional<Vote> vote) {
        if (vote.isEmpty()) {
            throw new VotingException(String.format(EXCEPTION_LATE_VOTE));
        }
    }
}
