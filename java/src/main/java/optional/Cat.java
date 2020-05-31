package optional;

import lombok.Data;

/**
 * @Author: Damon
 * @Date: 2020/5/31 17:12
 */
@Data
public class Cat {
    private String name;

    public Cat(String name) {
        this.name = name;
    }
}
