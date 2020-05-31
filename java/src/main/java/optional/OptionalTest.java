package optional;

import lambada.entity.Employee;
import org.junit.Test;

import java.util.Optional;

/**
 * @Author: Damon
 * @Date: 2020/5/24 18:01
 */
public class OptionalTest {
    @Test
    public void test()
    {
        Optional<Employee> optional = Optional.empty();
        //java.util.NoSuchElementException: No value present
        System.out.println(optional.get());
    }

    /**
     *  public static <T> Optional<T> of(T value)
     */
    @Test
    public void test1()
    {
        Optional<Employee> optional = Optional.of(new Employee());
        Employee employee = optional.get();
        System.out.println(employee);

        Optional<Employee> op = Optional.of(null);// 此处直接空指针异常
        Employee emp = op.get();
        System.out.println(emp);

    }

    /**
     * 创建一个空的Optional
     * Optional.empty()
     *
     */
    @Test
    public void test2()
    {
        Optional<Employee> optional = Optional.empty();
        // java.util.NoSuchElementException: No value present
        Employee employee = optional.get();
        System.out.println(employee);

    }

    /**
     * 创建一个空的Optional
     * Optional.empty()
     * public static <T> Optional<T> ofNullable(T value) {
     *         return value == null ? empty() : of(value);
     *     }
     */
    @Test
    public void test3()
    {
//        Optional<Employee> optional = Optional.ofNullable(null);
        Optional<Employee> optional = Optional.ofNullable(new Employee());
        //如果Optional容器中有值
        if(optional.isPresent()){
            Employee employee = optional.get();
            //Employee(id=0, name=null, age=0, salary=0, status=null)
            System.out.println(employee);
        }else {
            System.out.println("Optional容器中没值");
        }
    }

    /**
     *
     * optional.orElse();
     * optional.orElseGet();
     * optional.orElseThrow();
     *
     */
    @Test
    public void test4()
    {
//        Optional<Employee> optional = Optional.ofNullable(null);
        Optional<Employee> optional = Optional.ofNullable(new Employee(222,"KK",18,20));

//        Employee(id=1, name=jj, age=23, salary=500, status=null)
        Employee orElse = optional.orElse(new Employee(1,"jj",23,500));

        System.out.println(orElse);

//        Employee orElseGet = optional.orElseGet(()->{
//            System.out.println("使用了orElseGet方法");
//            //可以根据需求生成不同的对象
//            return new Employee();
//        });
//
//        System.out.println(orElseGet);

    }
    @Test
    public void test4_1()
    {
//        Optional<Employee> optional = Optional.ofNullable(null);
        Optional<Employee> optional = Optional.ofNullable(new Employee());

        Employee employee = optional.orElseGet(()->{
            //可以根据需求生成不同的对象
            return new Employee();
        });
//        Employee employee = optional.orElseThrow();

        System.out.println(employee);

    }


    /**
     * @FunctionalInterface
     * public interface Function<T, R> {
     *     R apply(T t);
     * }
     *public<U> Optional<U> map(Function<? super T, ? extends U> mapper)
     *
     *public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper)
     */
    @Test
    public void test5()
    {
        Optional<Employee> optional = Optional.ofNullable(new Employee(1,"jj",23,500));
//        Optional<Integer> age = optional.map(e -> e.getAge());
//        System.out.println(age.get())
        //返回optional
        Optional<Integer> age = optional.flatMap(employee -> Optional.of(employee.getSalary()));
        System.out.println(age.get());
    }

    @Test
    public void filter(){
        Optional<Employee> optional = Optional.ofNullable(new Employee(1,"jj",23,500));
        Optional<Employee> optionalEmployee = optional.filter(e -> e.getAge() > 10);
        System.out.println(optionalEmployee.get());

        optional.ifPresent(e-> System.out.println("如果Employee不为null,获取年纪的属性: " + e.getAge()));
    }

    @Test
    public void test6(){
        Girl girl = new Girl();
        //java.lang.NullPointerException
        String catName = getCatName(girl);
        System.out.println(catName);
    }

    public String getCatName(Girl girl){
//        return girl.getCat().getName();
        if(girl != null){
            Cat cat = girl.getCat();
            if(cat != null){
                return cat.getName();
            }
        }
        return "tom";
    }

    @Test
    public void test7(){
//        Optional<Java8Girl> girl = Optional.ofNullable(null);
//        Optional<Java8Girl> girl = Optional.ofNullable(new Java8Girl());


        Optional<Cat> cat = Optional.ofNullable(new Cat("mimi"));
        Optional<Java8Girl> girl = Optional.ofNullable(new Java8Girl(cat));
        String catName = getCatNameV2(girl);
        System.out.println(catName);
    }

    public String getCatNameV2(Optional<Java8Girl> girl){
        return girl.orElse(new Java8Girl())
                .getCat()
                .orElse(new Cat("Tom"))
                .getName();
    }




}
