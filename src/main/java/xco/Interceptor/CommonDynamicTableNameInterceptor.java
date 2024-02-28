package xco.Interceptor;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xco.handler.AndroidTableNameHandler;

@Configuration
public class CommonDynamicTableNameInterceptor {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.SQLITE));
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor1 = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor1.setTableNameHandler(new AndroidTableNameHandler());
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor1);
        return interceptor;
    }
}
