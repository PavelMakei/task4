package by.makei.shop.model.dao;

import java.lang.annotation.*;

@Documented //Попадёт в javadoc
@Retention(RetentionPolicy.RUNTIME)//время жизни (SOURCE - только в исходном коде 'для javadoc'),(CLASS - только на время выполнения компиляции)
@Target(ElementType.TYPE)//(FIELD, METHOD, PACKAGE, PARAMETER...)  в данном случае над классами и интерфейсами
@Inherited //будет унаследована потомками класса
public @interface SuspiciousConnection {

   boolean suppressException() default false;


}
