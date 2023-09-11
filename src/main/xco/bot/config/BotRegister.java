package xco.bot.config;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
class BotRegister {
    @Autowired
    BotConfig config;

    @Bean
    private Bot registerBot() {
        try {
            //FixProtocolVersion.fetch(BotConfiguration.MiraiProtocol.ANDROID_PAD, "8.9.63");
            //FixProtocolVersion.sync(BotConfiguration.MiraiProtocol.ANDROID_PAD);
            // test 尝试过滤大部分日志,只保留关键信息
            // 目前bot接收消息时不会输出日志了,但是bot进行初始化时的日志可能还是过多,之后可以考虑参考simbot相关代码进行过滤
            //BotAuthorization.byQRCode()
            Bot bot = BotFactory.INSTANCE.newBot(config.getAccount(), BotAuthorization.byQRCode(), new BotConfiguration() {{
                setProtocol(MiraiProtocol.ANDROID_WATCH); // 切换协议
                fileBasedDeviceInfo();//使用device.json存储设备信息
                noBotLog();//禁用bot日志
                // noNetworkLog();//禁用网络日志
                setShowingVerboseEventLog(false);//不显示详细的日志
                // setLoginSolver(new StandardCharImageLoginSolver());
            }});
            bot.login();
            return bot;
        } catch (Throwable e) {
            e.printStackTrace();
            return new EmptyBot();
        }
    }

}
