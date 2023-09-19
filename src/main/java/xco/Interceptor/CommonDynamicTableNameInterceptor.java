package xco.Interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xco.handler.AndroidTableNameHandler;
import xco.handler.WindowsTableNameHandler;

@Configuration
public class CommonDynamicTableNameInterceptor {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor1 = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor1.setTableNameHandler(new AndroidTableNameHandler());
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor2 = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor2.setTableNameHandler(new WindowsTableNameHandler());
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor1);
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor2);
        return interceptor;
    }
}
