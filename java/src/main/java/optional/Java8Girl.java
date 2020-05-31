package optional;

import lombok.Data;

import java.util.Optional;

/**
 * @Author: Damon
 * @Date: 2020/5/31 17:21
 */
@Data
public class Java8Girl {
    private Optional<Cat> cat = Optional.empty();

    public Java8Girl() {

    }

    public Java8Girl(Optional<Cat> cat) {
        this.cat = cat;
    }
}
